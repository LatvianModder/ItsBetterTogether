package latmod.ibt.blocks;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.*;
import latmod.ibt.entity.*;

public class Block
{
	public final String blockName;
	public Texture blockTexture;
	public AABB renderBounds = null;
	
	public Block(String s)
	{
		blockName = s;
		addedBlocks.put(blockName, this);
		renderBounds = new AABB.Corner(0D, 0D, 0D, 1D, 0D, 1D);
		renderBounds.owner = this;
	}
	
	public void reloadTextures()
	{
		blockTexture = Renderer.getTexture("blocks/" + blockName + ".png");
	}
	
	public void onRender(World w, double x, double y)
	{
		Renderer.setTexture(blockTexture);
		Renderer.rect(x, y, 1D, 1D);
	}
	
	public void onActived(World w, double x, double y, EntityPlayer ep)
	{
	}
	
	public boolean isSolidFor(Entity e)
	{ return true; }
	
	//FIXME: IDs per world
	public final int getID(World w)
	{ return 0; }
	
	public static final FastMap<String, Block> addedBlocks = new FastMap<String, Block>();
	
	public static final Block wall_stone = new Block("wall_stone");
}