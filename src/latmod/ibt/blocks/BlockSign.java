package latmod.ibt.blocks;
import latmod.ibt.tiles.*;
import latmod.ibt.world.*;

public class BlockSign extends Block implements ITileBlock
{
	public BlockSign(String s)
	{
		super(s);
	}
	
	public TileEntity createTile(World w)
	{ return new TileSign(w); }
}