package latmod.ibt.entity;
import latmod.core.util.*;
import latmod.ibt.*;

public class Entity extends Vertex
{
	public final World worldObj;
	
	public Entity(World w)
	{
		worldObj = w;
	}
}