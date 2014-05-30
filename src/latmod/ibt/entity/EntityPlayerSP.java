package latmod.ibt.entity;
import org.lwjgl.input.*;

import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.world.*;

public class EntityPlayerSP extends EntityPlayer
{
	public EntityPlayerSP(World w)
	{
		super(w);
	}
	
	public void onRender()
	{
		super.onRender();
		
		Main.inst.camX = -posX * World.inst.width + Main.inst.width / 2D;
		Main.inst.camY = -posY * World.inst.height + Main.inst.height / 2D;
	}
	
	public void onGuiRender()
	{
		Font.inst.drawShadedText(4, 4, "Testificate");
	}
	
	public void onUpdate(Timer t)
	{
		double speed = 0.15D;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) move(0D, -1D, speed);
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) move(0D, 1D, speed);
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) move(-1D, 0D, speed);
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) move(1D, 0D, speed);
	}
}