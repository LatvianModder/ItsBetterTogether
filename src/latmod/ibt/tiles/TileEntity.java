package latmod.ibt.tiles;
import latmod.core.nbt.*;
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
	
	public void readFromNBT(NBTMap map)
	{
	}
	
	public void writeToNBT(NBTMap map)
	{
	}
	
	public void onCustomData(NBTMap data)
	{
		Main.logger.info("Received custom data for " + LatCore.classpath(getClass()));
		
		for(NBTBase b : data.map.values)
		Main.logger.info(b.name + " [ " + b.getClass().getSimpleName() + " ]: " + b.getData());
	}
}