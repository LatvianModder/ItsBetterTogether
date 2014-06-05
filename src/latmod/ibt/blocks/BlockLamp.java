package latmod.ibt.blocks;
import latmod.ibt.tiles.*;
import latmod.ibt.world.*;

public class BlockLamp extends Block implements ITileBlock
{
	public BlockLamp(int i, String s)
	{
		super(i, s);
		setLightValue(1F);
	}
	
	public int getLightValue(World w, int x, int y)
	{
		TileLamp t = (TileLamp)w.getTile(x, y);
		return t.lightValue;
	}
	
	public boolean isVisible(World w, int x, int y)
	{ return false; }
	
	public TileEntity createTile(World w)
	{ return new TileLamp(w); }
	
	public void registerTiles()
	{ TileRegistry.addTile(this, TileLamp.class); }
}