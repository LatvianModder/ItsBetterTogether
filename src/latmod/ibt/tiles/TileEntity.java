package latmod.ibt.tiles;
import java.util.Map;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class TileEntity
{
	public final World worldObj;
	public int posX, posY;
	public Block type = null;
	
	public TileEntity(World w)
	{
		worldObj = w;
	}
	
	public void onUpdate(Timer t)
	{
	}
	
	public void onRender()
	{
	}
	
	public void onCustomData(Map<String, Object> map)
	{
	}
}