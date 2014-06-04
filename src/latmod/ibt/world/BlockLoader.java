package latmod.ibt.world;
import java.util.*;
import latmod.core.nbt.*;
import com.google.gson.annotations.Expose;

public class BlockLoader
{
	@Expose public String id;
	@Expose public Map<String, Object> data;
	
	public NBTMap getData()
	{
		if(data == null) return null;
		
		NBTMap dataMap = new NBTMap(null);
		
		Iterator<String> iKeys = data.keySet().iterator();
		Iterator<Object> iObjects = data.values().iterator();
		
		while(iKeys.hasNext())
		{
			String k = iKeys.next();
			Object v = iObjects.next();
			
			NBTBase b = NBTBase.newBase(k, v);
			if(b != null) dataMap.setBase(b);
		}
		
		return dataMap;
	}
}