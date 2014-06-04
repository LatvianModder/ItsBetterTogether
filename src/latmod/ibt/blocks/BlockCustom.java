package latmod.ibt.blocks;
import latmod.ibt.entity.Entity;
import latmod.ibt.tiles.*;
import latmod.ibt.world.*;

public class BlockCustom extends Block implements ITileBlock
{
	public BlockCustom(String s)
	{
		super(s);
	}
	
	public int getLightValue(World w, int x, int y)
	{
		TileCustom t = (TileCustom) w.getTile(x, y);
		return t.lightValue;
	}
	
	public boolean isVisible(World w, int x, int y)
	{ return false; }
	
	public boolean isSolidFor(World w, int x, int y, Entity e)
	{
		TileCustom t = (TileCustom) w.getTile(x, y);
		return !t.isGhost;
	}
	
	public TileEntity createTile(World w)
	{ return new TileCustom(w); }
	
	public void registerTiles(IDReg reg)
	{ reg.addTile(blockID, TileCustom.class); }
}