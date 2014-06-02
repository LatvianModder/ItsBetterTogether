package latmod.ibt.tiles;
import java.util.Iterator;
import java.util.Map;

import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class TileEntity
{
	public final World worldObj;
	public int posX, posY;
	public Block type = null;
	public boolean isDirty = true;
	
	public TileEntity(World w)
	{
		worldObj = w;
	}
	
	public void onCreated()
	{
	}
	
	public void onDestroyed()
	{
	}
	
	public void onUpdate(Timer t)
	{
	}
	
	public boolean isVisible()
	{ return true; }
	
	public void onRender()
	{
	}
	
	public void onCustomData(Map<String, Object> data)
	{
		Iterator<String> keys = data.keySet().iterator();
		Iterator<Object> values = data.values().iterator();
		
		Main.logger.info("Received custom data for " + LatCore.classpath(getClass()));
		
		while(keys.hasNext())
		{
			String k = keys.next();
			Object v = values.next();
			Main.logger.info(k + ": " + v + " [ " + v.getClass().getSimpleName() + " ]");
		}
	}
}