package latmod.ibt.tiles;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class TileEntity
{
	public World worldObj = null;
	public int posX, posY;
	public Block type = null;
	
	public TileEntity()
	{
		worldObj = World.inst;
	}
	
	public void onUpdate(Timer t)
	{
	}
	
	public void onRender()
	{
	}
}