package latmod.ibt.blocks;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.entity.*;
import latmod.ibt.world.*;

public class Block
{
	public final String blockID;
	public Texture blockTexture;
	
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
	
	public boolean isVisible(World w, double x, double y)
	{ return true; }
	
	public void onActived(World w, double x, double y, EntityPlayer ep)
	{
	}
	
	public boolean isSolidFor(Entity e)
	{ return true; }
	
	public final int getID(World w)
	{ return w.registry.blocks.getOrCreateID(blockID); }
	
	public static final FastMap<String, Block> addedBlocks = new FastMap<String, Block>();
	
	public static final Block wall_stone = new Block("wall_stone");
}