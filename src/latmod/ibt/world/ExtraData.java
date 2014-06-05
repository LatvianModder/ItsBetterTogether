package latmod.ibt.world;
import java.util.*;
import latmod.core.rendering.*;
import latmod.ibt.blocks.*;

public class ExtraData
{
	public Map<String, Object> map;
	
	public ExtraData(Map<String, Object> m)
	{ map = m; }
	
	public ExtraData()
	{ this(new HashMap<String, Object>()); }
	
	public boolean has(String s)
	{ return map.containsKey(s); }
	
	public Object get(String s)
	{ return map.get(s); }
	
	public String getS(String s, String def)
	{
		String o = (String) get(s);
		return (o == null) ? def : o;
	}
	
	public Number getN(String s, Number def)
	{
		Number o = (Number) get(s);
		return (o == null) ? def : o;
	}
	
	public Number getN(String s, Number def, Number min, Number max)
	{
		Number n = getN(s, def);
		if(n != null && min != null && max != null)
		{
			if(n.doubleValue() < min.doubleValue()) n = min;
			if(n.doubleValue() > max.doubleValue()) n = max;
		}
		
		return n;
	}
	
	public Boolean getB(String s, Boolean def)
	{
		Boolean o = (Boolean) get(s);
		return (o == null) ? def : o;
	}
	
	public Block getBlock(String s, Block def)
	{
		String s1 = getS(s, null);
		if(s1 == null || !Block.blockNameMap.keys.contains(s1))
		return def; return Block.blockNameMap.get(s1);
	}
	
	public Color getC(String s, Color def)
	{
		String s1 = getS(s, null);
		if(s1 == null) return def;
		Integer i = WorldLoader.getCol(s1);
		return (i == null) ? def : Color.get(i);
	}
}