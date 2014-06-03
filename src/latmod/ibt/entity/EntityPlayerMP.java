package latmod.ibt.entity;
import org.lwjgl.input.Keyboard;

import latmod.core.util.Timer;
import latmod.ibt.world.*;

public class EntityPlayerMP extends EntityPlayer
{
	public EntityPlayerMP(World w)
	{
		super(w);
		username = "annijamic";
	}
	
	public void onUpdate(Timer t)
	{
		double speed = 0.15D;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) speed *= 1.7D;
		if(Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) speed *= 0.3D;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) move(0D, -1D, speed);
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) move(0D, 1D, speed);
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) move(-1D, 0D, speed);
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) move(1D, 0D, speed);
		
		boolean f = flashlight;
		flashlight = Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0);
		if(flashlight != f) worldObj.lightMapDirty = true;
	}
}