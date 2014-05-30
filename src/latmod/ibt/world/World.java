package latmod.ibt.world;
import java.util.Map;

import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.entity.*;
import latmod.ibt.tiles.*;

public class World
{
	public static World inst = null;
	
	public final IDReg registry;
	public String worldName;
	public int width = 1, height = 1;
	
	public String backgroundTex = "bg/metal.png";
	private Texture texBG;
	public Block[][] blocks;
	public FastList<TileEntity> tileEntities;
	public EntityPlayerSP playerSP;
	public EntityPlayerMP playerMP;
	
	public int renderedBlocks = 0;
	
	public World()
	{
		registry = new IDReg(this);
		tileEntities = new FastList<TileEntity>();
	}
	
	public void postInit()
	{
		blocks = new Block[width][height];
		texBG = Renderer.getTexture(backgroundTex);
		playerSP = new EntityPlayerSP(this);
		playerMP = new EntityPlayerMP(this);
	}
	
	public void onUpdate(Timer t)
	{
		playerSP.onUpdate(t);
		playerMP.onUpdate(t);
	}
	
	public void onRender()
	{
		Renderer.enableTexture();
		
		Renderer.setTexture(texBG);
		Renderer.rect(0D, 0D, width, height, 0D, 0D, width, height);
		
		renderedBlocks = 0;
		
		for(int y = 0; y < height; y++)
		for(int x = 0; x < width; x++)
		{
			if(blocks[x][y] != null && blocks[x][y].isVisible(this, x, y))
			{ blocks[x][y].onRender(this, x, y); renderedBlocks++; }
		}
		
		playerSP.onRender();
		playerMP.onRender();
	}
	
	public void read(DataIOStream dios) throws Exception
	{
		registry.read(dios);
	}
	
	public void write(DataIOStream dios) throws Exception
	{
		registry.write(dios);
	}
	
	public Block getBlock(double bx, double by)
	{
		int x = (int)bx;
		int y = (int)by;
		if(x < 0 || x >= width) return null;
		if(y < 0 || y >= height) return null;
		return blocks[x][y];
	}
	
	public boolean setBlock(double bx, double by, Block b, Map<String, Object> data)
	{
		int x = (int)bx;
		int y = (int)by;
		if(x < 0 || x >= width) return false;
		if(y < 0 || y >= height) return false;
		blocks[x][y] = b;
		return true;
	}
	
	public boolean setBlock(double bx, double by, Block b)
	{ return setBlock(bx, by, b, null); }
}