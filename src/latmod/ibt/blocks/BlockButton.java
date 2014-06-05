package latmod.ibt.blocks;
import latmod.core.rendering.*;
import latmod.ibt.entity.*;
import latmod.ibt.tiles.*;
import latmod.ibt.world.*;

public class BlockButton extends Block implements ITileBlock
{
	public Texture textureOn;
	
	public BlockButton(String s)
	{
		super(s);
	}
	
	public void reloadTextures()
	{
		super.reloadTextures();
		textureOn = Renderer.getTexture("blocks/buttonOn.png");
	}
	
	public boolean isVisible(World w, int x, int y)
	{ return false; }
	
	public TileEntity createTile(World w)
	{ return new TileButton(w); }

	public void registerTiles(IDReg reg)
	{ reg.addTile(blockID, TileButton.class); }
	
	public boolean isSolidFor(World w, int x, int y, Entity e)
	{ return false; }
}