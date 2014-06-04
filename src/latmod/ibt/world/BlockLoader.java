package latmod.ibt.world;
import java.util.*;
import com.google.gson.annotations.Expose;

public class BlockLoader
{
	@Expose public String id;
	@Expose public Map<String, Object> data;
	
	public ExtraData getData()
	{
		if(data == null) return null;
		return new ExtraData(data);
	}
}