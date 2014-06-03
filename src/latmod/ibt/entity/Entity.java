package latmod.ibt.entity;
import latmod.core.nbt.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class Entity
{
	public final World worldObj;
	public double posX, posY;
	public double sizeX, sizeY;
	public double rotation = 0D;
	public boolean isDirty = true;
	public boolean isDead = false;
	
	public Entity(World w)
	{
		worldObj = w;
		sizeX = sizeY = 0.9D;
	}
	
	public void onUpdate(Timer t)
	{
		Block b = worldObj.getBlock(posX, posY);
		if(b != null) b.onEntityStandingOn(worldObj, (int)posX, (int)posY, this);
	}
	
	public boolean isVisible()
	{ return true; }
	
	public void setDead()
	{ isDead = isDirty = true; }
	
	public void onRender()
	{
	}
	
	public void setPos(double x, double y)
	{
		posX = x;
		posY = y;
		isDirty = true;
	}
	
	public void move(double mx, double my, double speed)
	{
		// X
		
		if(posX + mx * speed < 0D) mx = -posX / speed;
		if(posX + mx * speed > worldObj.width) mx = (worldObj.width - posX) / speed;
		
		Block bx = worldObj.getBlock(posX + mx * speed, posY);
		if(bx == null || !bx.isSolidFor(worldObj, (int)(posX + mx * speed), (int)posY, this))
		{
			posX += mx * speed;
			isDirty = true;
		}
		
		// Y
		
		if(posY + my * speed < 0D) my = -posY / speed;
		if(posY + my * speed >= worldObj.height) my = (worldObj.height - posY) / speed;
		
		//Coords cy = Coords.getAABBInBox(worldObj.coordsList, this, 0D, my * speed);
		
		Block by = worldObj.getBlock(posX, posY + my * speed);
		if(by == null || !by.isSolidFor(worldObj, (int)posX, (int)(posY + my * speed), this))
		{
			posY += my * speed;
			isDirty = true;
		}
	}
	
	public void readFromNBT(NBTMap map)
	{
		double[] pos = map.getDoubleArray("PosRot");
		posX = pos[0];
		posY = pos[1];
		rotation = pos[2];
	}
	
	public void writeToNBT(NBTMap map)
	{
		map.setDoubleArray("PosRot", posX, posY, rotation);
	}
}