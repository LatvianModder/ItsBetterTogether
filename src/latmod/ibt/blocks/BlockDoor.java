package latmod.ibt.blocks;
import latmod.ibt.entity.*;
import latmod.ibt.tiles.*;
import latmod.ibt.world.*;

public class BlockDoor extends Block implements ITileBlock
{
	public BlockDoor(String s)
	{
		super(s);
	}
	
	public TileEntity createTile(World w)
	{ return new TileDoor(w); }
	
	public boolean isSolidFor(World w, int x, int y, Entity e)
	{ return ((TileDoor)w.getTile(x, y)).isOpen(); }
	
	public void registerTiles(IDReg reg)
	{ reg.addTile(blockID, TileDoor.class); }
}