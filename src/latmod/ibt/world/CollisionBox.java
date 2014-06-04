package latmod.ibt.world;
import latmod.core.util.*;

public class CollisionBox
{
	public double minX, minY, maxX, maxY;
	
	public CollisionBox(double x1, double y1, double x2, double y2)
	{
		minX = x1;
		minY = y1;
		maxX = x2;
		maxY = y2;
	}
	
	public boolean isColliding(CollisionBox b, double x, double y)
	{
		if(maxX + x < b.minX || minX + x > b.maxX) return false;
		if(maxY + y < b.minY || minY + y > b.maxY) return false;
		return true;
	}
	
	public boolean isAt(double x, double y)
	{
		if(x < minX || x > maxX) return false;
		if(y < minY || y > maxY) return false;
		return true;
	}
	
	public static CollisionBox getAABBAtPoint(FastList<? extends CollisionBox> list, double x, double y)
	{
		FastList<CollisionBox> alB = getAllAABBsAtPoint(list, x, y);
		
		for(int i = 0; i < alB.size(); i++)
		{
			CollisionBox b = alB.get(i);
			if(b.isAt(x, y)) return b;
		}
		
		return null;
	}
	
	public static CollisionBox getAABBInBox(FastList<? extends CollisionBox> list, CollisionBox pos, double x, double y)
	{
		if(pos == null) return null;
		FastList<CollisionBox> alB = getAllAABBsInBox(list, pos, x, y);
		
		for(int i = 0; i < alB.size(); i++)
		{
			CollisionBox b = alB.get(i);
			if(b != pos && pos.isColliding(b, x, y)) return b;
		}
		
		return null;
	}
	
	public static FastList<CollisionBox> getAllAABBsInBox(FastList<? extends CollisionBox> list, CollisionBox pos, double x, double y)
	{
		FastList<CollisionBox> al = new FastList<CollisionBox>();
		if(pos == null) return al;
		
		for(int i = 0; i < list.size(); i++)
		{
			CollisionBox b = list.get(i);
			if(b != pos && pos.isColliding(b, x, y)) al.add(b);
		}
		
		return al;
	}
	
	public static FastList<CollisionBox> getAllAABBsAtPoint(FastList<? extends CollisionBox> list, double x, double y)
	{
		FastList<CollisionBox> al = new FastList<CollisionBox>();
		
		for(int i = 0; i < list.size(); i++)
		{
			CollisionBox b = list.get(i);
			if(b.isAt(x, y)) al.add(b);
		}
		
		return al;
	}
}