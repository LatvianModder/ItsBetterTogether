package latmod.ibt.world;
import java.util.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.core.util.Timer;
import latmod.ibt.blocks.*;
import latmod.ibt.entity.*;
import latmod.ibt.tiles.*;

public class World
{
	public static World inst = null;
	
	public static int[] lightMapValues = new int[16];
	
	static
	{
		for(int i = 0; i < lightMapValues.length; i++)
			lightMapValues[i] = (int)MathHelper.map(i, 0D, 15D, 200D, 0D);
	}
	
	public final IDReg registry;
	public String worldName;
	public int width = 1, height = 1;
	public String backgroundTex = "bg/metal.png";
	private Texture texBG;
	public int renderedBlocks = 0;
	
	public FastMap<Integer, Block> blocks;
	public FastMap<Integer, TileEntity> tiles;
	public EntityPlayerSP playerSP;
	public EntityPlayerMP playerMP;
	public Random rand = new Random();
	public int[] lightMap;
	public int lightMapListID = -1;
	public boolean lightMapDirty = true;

	public World()
	{
		registry = new IDReg(this);
		blocks = new FastMap<Integer, Block>();
		tiles = new FastMap<Integer, TileEntity>();
	}
	
	public void postInit()
	{
		lightMap = new int[width * height];
		texBG = Renderer.getTexture(backgroundTex);
		playerSP = new EntityPlayerSP(this);
		playerMP = new EntityPlayerMP(this);
		
		registry.load();
	}
	
	public void onUpdate(Timer t)
	{
		for(TileEntity te : tiles)
		te.onUpdate(t);
		
		playerSP.onUpdate(t);
		playerMP.onUpdate(t);
	}
	
	public void onRender()
	{
		Renderer.enableTexture();
		
		Renderer.setTexture(texBG);
		Renderer.rect(0D, 0D, width, height, 0D, 0D, width, height);
		
		renderedBlocks = 0;
		
		for(int i = 0; i < blocks.size(); i++)
		{
			Integer c = blocks.keys.get(i);
			Block b = blocks.values.get(i);
			
			if(b.isVisible(this, getX(c), getY(c)))
			{
				b.onRender(this, getX(c), getY(c));
				renderedBlocks++;
			}
		}
		
		for(TileEntity te : tiles)
		te.onRender();
		
		playerSP.onRender();
		playerMP.onRender();
		
		Renderer.disableTexture();
		
		if(lightMapListID == -1)
		lightMapListID = Renderer.createListID();
		
		boolean smoothLighting = true;
		
		if(lightMapDirty)
		{
			lightMapDirty = false;
			Renderer.updateList(lightMapListID);
			
			for(int i = 0; i < lightMap.length; i++)
			{
				lightMap[i] = 0;
				
				Block b = blocks.get(i);
				if(b != null) lightMap[i] = b.getLightValue(this, getX(i), getY(i));
			}
			
			if(playerSP.flashlight)
			lightMap[getIndex(playerSP.posX, playerSP.posY)] = 10;
			
			if(playerMP.flashlight)
			lightMap[getIndex(playerMP.posX, playerMP.posY)] = 10;
			
			for(int j = 0; j < lightMap.length; j++)
			for(int i = 0; i < lightMap.length; i++)
			{
				int x = getX(i);
				int y = getY(i);
				
				int val = lightMap[i];
				int lUp = getLightValue(x, y - 1);
				int lDown = getLightValue(x, y + 1);
				int lLeft = getLightValue(x - 1, y);
				int lRight = getLightValue(x + 1, y);
				
				if(lUp > val + 1) val = lUp - 1;
				if(lDown > val + 1) val = lDown - 1;
				if(lLeft > val + 1) val = lLeft - 1;
				if(lRight > val + 1) val = lRight - 1;
				
				if(val > 0) lightMap[i] = val;
			}
			
			for(int i = 0; i < lightMap.length; i++)
			{
				int x = getX(i);
				int y = getY(i);
				
				if(smoothLighting)
				{
					Renderer.beginQuads();
					Color.BLACK.set(calcSmoothLight(x, y));
					Renderer.vertex(x, y);
					Color.BLACK.set(calcSmoothLight(x + 1D, y));
					Renderer.vertex(x + 1D, y);
					Color.BLACK.set(calcSmoothLight(x + 1D, y + 1D));
					Renderer.vertex(x + 1D, y + 1D);
					Color.BLACK.set(calcSmoothLight(x, y + 1D));
					Renderer.vertex(x, y + 1D);
					Renderer.end();
				}
				else
				{
					Color.BLACK.set(lightMapValues[lightMap[i]]);
					Renderer.rect(x, y, 1D, 1D);
				}
			}
			
			Renderer.endList();
		}
		
		Renderer.renderList(lightMapListID);
	}
	
	public int calcSmoothLight(double x, double y)
	{
		int lx0y0 = getLightValue(x, y);
		int lx1y0 = getLightValue(x + 1, y);
		int lx1y1 = getLightValue(x + 1, y + 1);
		int lx0y1 = getLightValue(x, y + 1);
		
		double val = (lx0y0 + lx1y0 + lx1y1 + lx0y1) / 4D;
		return (int)MathHelper.map(val, 0D, 15D, 200D, 0D);
	}
	
	public int getX(int index)
	{ return index % width; }
	
	public int getY(int index)
	{ return index / width; }
	
	public int getIndex(double x, double y)
	{ return (int)x + (int)y * width; }
	
	public void read(DataIOStream dios) throws Exception
	{
		registry.read(dios);
	}
	
	public void write(DataIOStream dios) throws Exception
	{
		registry.write(dios);
	}
	
	public Block getBlock(double x, double y)
	{ return blocks.get(getIndex(x, y)); }
	
	public void setBlock(double x, double y, Block b, Map<String, Object> data)
	{
		int ix = (int)x;
		int iy = (int)y;
		int index = getIndex(x, y);
		
		Block b0 = blocks.get(index);
		
		if(b0 != null)
		{
			b0.onDestroyed(this, ix, iy);
			
			if(b0.hasTile)
			{
				TileEntity te = tiles.get(index);
				if(te != null) te.onDestroyed();
				tiles.remove(index);
			}
			
			blocks.remove(index);
		}
		
		if(b != null)
		{
			blocks.put(index, b);
			
			if(b.hasTile)
			{
				TileEntity te = ((ITileBlock)b).createTile(this);
				
				if(te != null)
				{
					te.posX = ix;
					te.posY = iy;
					te.type = b;
					tiles.put(index, te);
					
					te.onCreated();
					if(data != null) te.onCustomData(data);
					setTile(x, y, te);
				}
			}
			
			b.onCreated(this, ix, iy);
		}
	}
	
	public void setBlock(double x, double y, Block b)
	{ setBlock(x, y, b, null); }
	
	public TileEntity getTile(double x, double y)
	{ return tiles.get(getIndex(x, y)); }
	
	public void setTile(double x, double y, TileEntity te)
	{ tiles.put(getIndex(x, y), te); }
	
	public int getLightValue(double x, double y)
	{
		if(x < 0 || x >= width || y < 0 || y >= height) return 0;
		
		int i = getIndex(x, y);
		return (i < 0 || i >= width * height) ? 0 : lightMap[i];
	}
}