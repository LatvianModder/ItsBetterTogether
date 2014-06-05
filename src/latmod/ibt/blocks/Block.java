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
	private int lightValue = 0;
	
	public Block(String s)
	{
		blockID = s;
		addedBlocks.put(blockID, this);
	}
	
	public void setLightValue(float f)
	{ lightValue = (int)(f * 15F); }
	
	public int getLightValue(World w, int x, int y)
	{ return lightValue; }
	
	public void reloadTextures()
	{
		blockTexture = Renderer.getTexture("blocks/" + blockID + ".png");
	}
	
	public void onRender(World w, double x, double y)
	{
		Color.WHITE.set();
		Renderer.setTexture(getTexture(w, x, y));
		Renderer.rect(x, y, 1D, 1D);
	}
	
	public Texture getTexture(World w, double x, double y)
	{ return blockTexture; }
	
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
	
	public boolean isTransparent(World w, int x, int y)
	{ return !isSolidFor(w, x, y, null) ; }
	
	// --  -- //
	
	public static final FastMap<String, Block> addedBlocks = new FastMap<String, Block>();
	
	public static final Block unknown = new Block("unknown");
	public static final Block wall_stone = new Block("wall_stone");
	public static final Block wall_stone_bricks = new Block("wall_stone_bricks");
	public static final Block wall_stone_cracked = new Block("wall_stone_cracked");
	public static final Block lamp = new BlockLamp("lamp");
	public static final Block door = new BlockDoor("door");
	public static final Block button = new BlockButton("button");

	public Integer getID(World w)
	{ return w.registry.blocks.getID(blockID); }
}