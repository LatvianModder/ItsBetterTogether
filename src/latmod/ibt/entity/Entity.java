package latmod.ibt.entity;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class Entity
{
	public double posX, posY;
	public final World worldObj;
	public double rotation = 0D;
	public boolean isDirty = true;
	
	public Entity(World w)
	{
		worldObj = w;
	}
	
	public void onUpdate(Timer t)
	{
	}
	
	public boolean isVisible()
	{ return true; }
	
	public void onRender()
	{
	}
	
	public void move(double mx, double my, double speed)
	{
		Block bx = worldObj.getBlock(posX + mx * speed, posY);
		if(bx == null || !bx.isSolidFor(this))
		{ posX += mx * speed; isDirty = true; }
		
		Block by = worldObj.getBlock(posX, posY + my * speed);
		if(by == null || !by.isSolidFor(this))
		{ posY += my * speed; isDirty = true; }
	}
}