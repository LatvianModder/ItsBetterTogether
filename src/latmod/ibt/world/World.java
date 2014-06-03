package latmod.ibt.world;
import java.util.*;
import latmod.core.util.*;
import latmod.core.util.Timer;
import latmod.ibt.blocks.*;
import latmod.ibt.entity.*;
import latmod.ibt.tiles.*;

public class World
{
	public static World inst = null;
	
	public final IDReg registry;
	public final WorldRenderer renderer;
	
	public String worldName;
	public int width = 1, height = 1;
	public String backgroundTex = "bg/metal.png";
	
	public FastMap<Integer, Block> blocks;
	public FastMap<Integer, TileEntity> tiles;
	public EntityPlayerSP playerSP;
	public EntityPlayerMP playerMP;
	public Random rand = new Random();

	public World()
	{
		registry = new IDReg(this);
		renderer = new WorldRenderer(this);
		
		blocks = new FastMap<Integer, Block>();
		tiles = new FastMap<Integer, TileEntity>();
	}
	
	public void postInit()
	{
		renderer.postInit();
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
}