package latmod.ibt.entity;
import latmod.core.util.*;
import latmod.ibt.world.*;

public class EntityRegistry
{
	public final World worldObj;
	public FastMap<Integer, String> idMap = new FastMap<Integer, String>();
	public FastMap<Integer, Class<? extends Entity>> classMap = new FastMap<Integer, Class<? extends Entity>>();
	
	public EntityRegistry(World w)
	{
		worldObj = w;
		
		add("box", EntityBox.class);
	}
	
	public void add(String s, Class<? extends Entity> e)
	{
		int id = worldObj.registry.entities.getOrCreateID(s);
		idMap.put(id, s);
		classMap.put(id, e);
	}
}