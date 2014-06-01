package latmod.ibt.entity;
import latmod.core.nbt.NBTMap;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class Entity
{
	public final World worldObj;
	public double posX, posY;
	public double rotation = 0D;
	public boolean isDirty = true;
	public boolean isDead = false;
	
	public Entity(World w)
	{
		worldObj = w;
	}
	
	public void onUpdate(Timer t)
	{
		Block b = worldObj.getBlock(posX, posY);
		if(b != null) b.onEntityCollided(worldObj, (int)posX, (int)posY, this, false);
	}
	
	public boolean isVisible()
	{ return true; }
	
	public void setDead()
	{ isDead = isDirty = true; }
	
	public void onRender()
	{
	}
	
	public void move(double mx, double my, double speed)
	{
		Block bx = worldObj.getBlock(posX + mx * speed, posY);
		if(bx == null || !bx.isSolidFor(this))
		{
			posX += mx * speed;
			if(bx != null) bx.onEntityCollided(worldObj, (int)posX, (int)posY, this, true);
			isDirty = true;
		}
		
		Block by = worldObj.getBlock(posX, posY + my * speed);
		if(by == null || !by.isSolidFor(this))
		{
			posY += my * speed;
			if(by != null) by.onEntityCollided(worldObj, (int)posX, (int)posY, this, true);
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