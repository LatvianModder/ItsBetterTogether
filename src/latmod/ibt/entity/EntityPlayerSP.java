package latmod.ibt.entity;
import org.lwjgl.input.*;

import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.*;
import latmod.ibt.gui.GuiComplete;
import latmod.ibt.world.*;

public class EntityPlayerSP extends EntityPlayer // Entity
{
	public boolean debug = false;
	
	public EntityPlayerSP(World w)
	{
		super(w);
		username = GameOptions.props.username;
		color = TextColor.L_BLUE.color;
	}
	
	public void onGuiRender()
	{
		Renderer.enableTexture();
		FastList<String> txt = new FastList<String>();
		
		txt.add(worldObj.worldName);
		
		if(debug)
		{
			txt.add("FPS: " + Main.inst.FPS + ", TPS: " + Main.inst.TPS);
			txt.add("X, Y, R: " + LatCore.stripDouble(posX, posY, rotation));
		}
		
		for(int i = 0; i < txt.size(); i++)
		Font.inst.drawText(4, 4 + i * 20, txt.get(i));
	}
	
	public void onRender()
	{
		super.onRender();
	}
	
	public void onUpdate(Timer t)
	{
		double speed = 0.075D;
		
		if(Main.inst.getGui().allowPlayerInput())
		{
			double arot = Math.atan2(Main.inst.mouseX - posX, Main.inst.mouseY - posY) * MathHelper.DEG;
			double d = 360D / 16D;
			rotation = ((long)(arot / d)) * d;
			
			if(Mouse.isButtonDown(0))
			move(MathHelper.sin(rotation * MathHelper.RAD), MathHelper.cos(rotation * MathHelper.RAD), speed);
		}
		
		moveEntity();
		
		if(worldObj.isPlayerAtEnd(this) && worldObj.isPlayerAtEnd(worldObj.playerMP))
		Main.inst.openGui(new GuiComplete());
	}
	
	public void mousePressed(LMMouse m)
	{
		if(m.button == 1)
		{
			if(!isCamera()) setAsCamera();
			else worldObj.playerMP.setAsCamera();
		}
	}
	
	public void keyPressed(int key)
	{
		if(key == GameOptions.KEY_DEBUG.key)
			debug = !debug;
		if(key == GameOptions.KEY_CAMERA.key)
		{
			if(!isCamera()) setAsCamera();
			else worldObj.playerMP.setAsCamera();
		}
	}
}