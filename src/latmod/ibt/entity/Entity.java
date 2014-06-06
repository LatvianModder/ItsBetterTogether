package latmod.ibt.entity;
import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class Entity
{
	public final World worldObj;
	public double posX, posY, motX, motY;
	public double radius;
	public double rotation = 0D;
	public boolean isDirty = true;
	public boolean isDead = false;
	public CollisionBox collisionBox;
	
	public Entity(World w)
	{
		worldObj = w;
		radius = 0.4D;
		collisionBox = new CollisionBox(0D, 0D, radius, radius);
	}
	
	public void onUpdate(Timer t)
	{
		Block b = worldObj.getBlock(posX, posY);
		if(b != null) b.onEntityStandingOn(worldObj, (int)posX, (int)posY, this);
	}
	
	public boolean isVisible()
	{ return true; }
	
	public CollisionBox getCollisionBox()
	{ return collisionBox; }
	
	public void setDead()
	{ isDead = isDirty = true; }
	
	public void onRender()
	{
	}
	
	public boolean isCamera()
	{ return Main.inst.cameraEntity == this; }
	
	public void setAsCamera()
	{ Main.inst.cameraEntity = this; }
	
	public void setPos(double x, double y)
	{
		posX = x;
		posY = y;
		isDirty = true;
	}
	
	public void move(double x, double y, double s)
	{
		double d = MathHelper.sqrt2sq(x, y);
		motX += x / d * s;
		motY += y / d * s;
	}
	
	public void updateCollisionBox()
	{ collisionBox.set(posX - radius, posY - radius, posX + radius, posY + radius); }
	
	public void moveEntity()
	{
		updateCollisionBox();
		
		if(motX != 0D)
		{
			if(posX + motX < 0D) motX = -posX;
			if(posX + motX >= worldObj.width) motX = worldObj.width - posX;
			
			FastList<CollisionBox> list = worldObj.getAllBlocks(collisionBox, motX, 0D);
			
			if(list.size() > 0) for(int i = 0; i < list.size(); i++)
			{
				CollisionBox cb = list.get(i);
				if(worldObj.blocks.get(cb.getIndex(worldObj)).isSolidFor(worldObj, (int)cb.minX, (int)cb.minY, this))
				motX = 0D; if(motX == 0D) continue;
			}
			
			if(motX != 0D)
			{
				posX += motX;
				updateCollisionBox();
				motX = 0D;
				isDirty = true;
			}
		}
		
		if(motY != 0D)
		{
			if(posY + motY < 0D) motY = -posY;
			if(posY + motY >= worldObj.height) motY = worldObj.height - posY;
			
			FastList<CollisionBox> list = worldObj.getAllBlocks(collisionBox, 0D, motY);
			
			if(list.size() > 0) for(int i = 0; i < list.size(); i++)
			{
				CollisionBox cb = list.get(i);
				if(worldObj.blocks.get(cb.getIndex(worldObj)).isSolidFor(worldObj, (int)cb.minX, (int)cb.minY, this))
					motY = 0D; if(motY == 0D) continue;
			}
			
			if(motY != 0D)
			{
				posY += motY;
				updateCollisionBox();
				motY = 0D;
				isDirty = true;
			}
		}
	}
	
	public void readEntity(DataIOStream dios) throws Exception
	{
		posX = dios.readDouble();
		posY = dios.readDouble();
		rotation = dios.readDouble();
	}
	
	public void writeEntity(DataIOStream dios) throws Exception
	{
		dios.writeDouble(posX);
		dios.writeDouble(posY);
		dios.writeDouble(rotation);
	}
}