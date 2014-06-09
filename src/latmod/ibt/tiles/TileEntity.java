package latmod.ibt.tiles;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public abstract class TileEntity
{
	public final World worldObj;
	public int posX, posY;
	public Block type = null;
	public boolean isDirty = false;
	
	public TileEntity(World w)
	{
		worldObj = w;
	}
	
	public abstract void loadTile(ExtraData data);
	public abstract void readTile(DataIOStream dios) throws Exception;
	public abstract void writeTile(DataIOStream dios) throws Exception;
	
	public void onDestroyed()
	{
	}
	
	public void onUpdate(Timer t)
	{
	}
	
	public void onRender()
	{
	}
	
	public Integer getCol(String s)
	{ return WorldLoader.getCol(s); }
	
	public Integer getSide(String s0)
	{
		String s = s0.trim().toLowerCase();
		
		if(s.equals("up")) return 0;
		if(s.equals("right")) return 1;
		if(s.equals("down")) return 2;
		if(s.equals("left")) return 3;
		
		return null;
	}
}