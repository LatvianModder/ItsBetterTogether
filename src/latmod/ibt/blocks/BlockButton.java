package latmod.ibt.blocks;
import latmod.ibt.tiles.*;
import latmod.ibt.world.*;

public class BlockButton extends Block implements ITileBlock
{
	public BlockButton(String s)
	{
		super(s);
	}
	
	public TileEntity createTile(World w)
	{ return new TileButton(w); }

	public void registerTiles(IDReg reg)
	{ reg.addTile(blockID, TileButton.class); }
}