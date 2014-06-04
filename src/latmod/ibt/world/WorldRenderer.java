package latmod.ibt.world;
import latmod.core.rendering.*;
import latmod.core.util.MathHelper;
import latmod.ibt.GameOptions;
import latmod.ibt.blocks.Block;
import latmod.ibt.tiles.TileEntity;

public class WorldRenderer
{
	public World worldObj;
	
	private Texture texBG;
	
	public int[] lightMap;
	public int lightMapListID = -1;
	public boolean lightMapDirty = true;
	
	public WorldRenderer(World w)
	{
		worldObj = w;
	}
	
	public void postInit()
	{
		lightMap = new int[worldObj.width * worldObj.height];
		texBG = Renderer.getTexture(worldObj.backgroundTex);
	}
	
	public void onRender()
	{
		Renderer.enableTexture();
		
		Renderer.setTexture(texBG);
		Renderer.rect(0D, 0D, worldObj.width, worldObj.height, 0D, 0D, worldObj.width, worldObj.height);
		
		for(int i = 0; i < worldObj.blocks.size(); i++)
		{
			Integer c = worldObj.blocks.keys.get(i);
			Block b = worldObj.blocks.values.get(i);
			
			if(b.isVisible(worldObj, worldObj.getX(c), worldObj.getY(c)))
			b.onRender(worldObj, worldObj.getX(c), worldObj.getY(c));
		}
		
		for(TileEntity te : worldObj.tiles)
		te.onRender();
		
		worldObj.playerSP.onRender();
		worldObj.playerMP.onRender();
		
		renderLighting();
	}
	
	public void renderLighting()
	{
		if(worldObj.extraArgs.keys.contains("brightLight")) return;
		
		Renderer.disableTexture();
		
		if(lightMapListID == -1)
		lightMapListID = Renderer.createListID();
		
		if(lightMapDirty)
		{
			lightMapDirty = false;
			Renderer.updateList(lightMapListID);
			
			if(worldObj.extraArgs.keys.contains("darkLight"))
			{
				Color.BLACK.set(200);
				Renderer.rect(0D, 0D, worldObj.width, worldObj.height);
			}
			else
			{
				boolean[] transMap = new boolean[lightMap.length];
				
				for(int i = 0; i < lightMap.length; i++)
				{
					lightMap[i] = 0;
					transMap[i] = true;
					
					Block b = worldObj.blocks.get(i);
					if(b != null)
					{
						int x = worldObj.getX(i);
						int y = worldObj.getY(i);
						
						lightMap[i] = b.getLightValue(worldObj, x, y);
						transMap[i] = true;//b.isTransparent(worldObj, x, y);
					}
				}
				
				// -- Custom Light Sources -- //
				
				//lightMap[worldObj.getIndex(worldObj.playerSP.posX, worldObj.playerSP.posY)] = 15;
				
				for(int j = 0; j < lightMap.length; j++)
				for(int i = 0; i < lightMap.length; i++)
				{
					int x = worldObj.getX(i);
					int y = worldObj.getY(i);
					
					int val = lightMap[i];
					int lUp = getLightValue(x, y - 1);
					int lDown = getLightValue(x, y + 1);
					int lLeft = getLightValue(x - 1, y);
					int lRight = getLightValue(x + 1, y);
					
					if(lUp > val + 1) val = lUp - 1;
					if(lDown > val + 1) val = lDown - 1;
					if(lLeft > val + 1) val = lLeft - 1;
					if(lRight > val + 1) val = lRight - 1;
					
					if(!transMap[i]) val = 0;
					if(val > 0) lightMap[i] = val;
				}
				
				for(int i = 0; i < lightMap.length; i++)
				{
					int x = worldObj.getX(i);
					int y = worldObj.getY(i);
					
					if(GameOptions.props.smoothLighting)
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
						Color.BLACK.set((int)MathHelper.map(lightMap[i], 0D, 15D, 200D, 0D));
						Renderer.rect(x, y, 1D, 1D);
					}
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
	
	public int getLightValue(double x, double y)
	{
		if(x < 0 || x >= worldObj.width || y < 0 || y >= worldObj.height) return 0;
		
		int i = worldObj.getIndex(x, y);
		return (i < 0 || i >= worldObj.width * worldObj.height) ? 0 : lightMap[i];
	}
}