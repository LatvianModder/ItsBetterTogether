package latmod.ibt.blocks;
import latmod.core.rendering.*;
import latmod.core.res.*;
import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.entity.*;
import latmod.ibt.world.*;

public class Block
{
	public final int blockID;
	public final String blockName;
	public Resource blockTexture;
	public final boolean hasTile = (this instanceof ITileBlock);
	private int lightValue = 0;
	
	public Block(int i, String s)
	{
		blockID = i;
		blockName = s;
		
		blockMap.put(blockID, this);
		blockNameMap.put(blockName, this);
		
		blockTexture = Resource.getTexture("blocks/" + blockName + ".png");
	}
	
	public void setLightValue(float f)
	{ lightValue = (int)(f * 15F); }
	
	public int getLightValue(World w, int x, int y)
	{ return lightValue; }
	
	public void onRender(World w, double x, double y)
	{
		Color.reset();
		Main.inst.textureManager.setTexture(getTexture(w, x, y));
		Renderer.rect(x, y, 1D, 1D);
	}
	
	public Resource getTexture(World w, double x, double y)
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
	
	public static final FastMap<Integer, Block> blockMap = new FastMap<Integer, Block>();
	public static final FastMap<String, Block> blockNameMap = new FastMap<String, Block>();
	
	public static final Block unknown = new Block(0, "unknown");
	public static final Block wall_stone = new Block(1, "wall_stone");
	public static final Block wall_stone_bricks = new Block(2, "wall_stone_bricks");
	public static final Block wall_stone_cracked = new Block(3, "wall_stone_cracked");
	public static final Block lamp = new BlockLamp(4, "lamp");
	public static final Block door = new BlockDoor(5, "door");
	public static final Block button = new BlockButton(6, "button");
}