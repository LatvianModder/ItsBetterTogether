package latmod.ibt.blocks;
import latmod.core.rendering.*;
import latmod.ibt.entity.*;
import latmod.ibt.tiles.*;
import latmod.ibt.world.*;

public class BlockPlayerEnd extends Block implements ITileBlock
{
	public Texture textureOff = null;
	public final boolean isSP() { return (this == Block.playerSPEnd); }
	
	public BlockPlayerEnd(String s)
	{
		super(s);
	}
	
	public boolean isVisible(World w, int x, int y)
	{ return false; }
	
	public boolean isSolidFor(World w, int x, int y, Entity e)
	{ return false; }

	public TileEntity createTile(World w)
	{ return new TilePlayerEnd(w); }

	public void registerTiles(IDReg reg)
	{ reg.addTile(blockID, TilePlayerEnd.class); }
	
	public void reloadTextures()
	{
		super.reloadTextures();
		textureOff = Renderer.getTexture("blocks/" + blockID + "_off.png");
	}
}