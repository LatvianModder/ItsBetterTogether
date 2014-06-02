package latmod.ibt.blocks;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.entity.*;
import latmod.ibt.world.*;

public class Block
{
	public final String blockID;
	public Texture blockTexture;
	public final boolean hasTile = (this instanceof ITileBlock);
	
	public Block(String s)
	{
		blockID = s;
		addedBlocks.put(blockID, this);
	}
	
	public void reloadTextures()
	{
		blockTexture = Renderer.getTexture("blocks/" + blockID + ".png");
	}
	
	public void onRender(World w, double x, double y)
	{
		Renderer.setTexture(blockTexture);
		Renderer.rect(x, y, 1D, 1D);
	}
	
	public boolean isVisible(World w, int x, int y)
	{ return true; }
	
	public void onActived(World w, int x, int y, EntityPlayer ep)
	{
	}
	
	public void onEntityStandingOn(World w, int x, int y, Entity e)
	{
	}
	
	public void onCreated(World w, int x, int y)
	{
	}
	
	public void onDestroyed(World w, int x, int y)
	{
	}
	
	public boolean isSolidFor(World w, int x, int y, Entity e)
	{ return true; }
	
	// --  -- //
	
	public static final FastMap<String, Block> addedBlocks = new FastMap<String, Block>();
	
	public static final Block unknown = new Block("unknown");
	public static final Block wall_stone = new Block("wall_stone");
	public static final BlockDoors doors = new BlockDoors("doors");
}