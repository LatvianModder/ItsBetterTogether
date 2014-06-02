package latmod.ibt.blocks;
import latmod.ibt.entity.*;
import latmod.ibt.tiles.*;
import latmod.ibt.world.*;

public class BlockDoors extends Block implements ITileBlock
{
	public BlockDoors(String s)
	{
		super(s);
	}
	
	public TileEntity createTile(World w)
	{ return new TileDoors(w); }
	
	public boolean isSolidFor(World w, int x, int y, Entity e)
	{ return ((TileDoors)w.getTile(x, y)).isOpen(); }
}