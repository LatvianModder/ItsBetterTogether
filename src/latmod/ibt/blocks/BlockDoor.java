package latmod.ibt.blocks;
import latmod.ibt.entity.*;
import latmod.ibt.tiles.*;
import latmod.ibt.world.*;

public class BlockDoor extends Block implements ITileBlock
{
	public BlockDoor(int i, String s)
	{
		super(i, s);
	}
	
	public boolean isVisible(World w, int x, int y)
	{ return false; }
	
	public TileEntity createTile(World w)
	{ return new TileDoor(w); }
	
	public boolean isSolidFor(World w, int x, int y, Entity e)
	{ return !((TileDoor)w.getTile(x, y)).isOpen; }
	
	public void registerTiles()
	{ TileRegistry.addTile(this, TileDoor.class); }
}