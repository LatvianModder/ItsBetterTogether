package latmod.ibt.entity;
import org.lwjgl.input.*;

import latmod.core.input.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.*;
import latmod.ibt.gui.*;
import latmod.ibt.net.PacketPlayerUpdate;
import latmod.ibt.world.*;

public class EntityPlayerSP extends EntityPlayer // Entity
{
	public boolean debug = false;
	
	public EntityPlayerSP(World w)
	{
		super(w);
		username = Main.inst.getPlayerUsername();
		color = Color.get(WorldLoader.getCol(Main.inst.getPlayerColor()));
	}
	
	public void onGuiRender()
	{
		if(!worldObj.canUpdate()) return;
		
		Renderer.enableTexture();
		FastList<String> txt = new FastList<String>();
		
		txt.add(worldObj.worldName);
		
		if(debug)
		{
			txt.add("FPS: " + Main.inst.FPS + ", TPS: " + Main.inst.TPS);
			txt.add("X, Y, R: " + LatCore.stripDouble(posX, posY, rotation));
			txt.add("Mouse: " + LatCore.stripInt(Main.inst.mouseX, Main.inst.mouseY));
			txt.add("Packets sent: " + worldObj.packetHandler.packetsTX);
			txt.add("Packets received: " + worldObj.packetHandler.packetsRX);
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
			
			tarX = MathHelper.sin(rotation * MathHelper.RAD);
			tarY = MathHelper.cos(rotation * MathHelper.RAD);
			
			if(Mouse.isButtonDown(0) && MathHelper.dist(posX, posY, Main.inst.mouseX, Main.inst.mouseY) > radius + 0.1D)
			move(tarX, tarY, speed);
		}
		
		moveEntity();
		
		if(isDirty)
		{
			isDirty = false;
			worldObj.packetHandler.sendPacket(new PacketPlayerUpdate());
		}
		
		if(worldObj.isPlayerAtEnd(this) && worldObj.isPlayerAtEnd(worldObj.playerMP))
		Main.inst.openGui(new GuiComplete());
	}
	
	public void mousePressed(EventMouse.Pressed e)
	{
		if(!worldObj.canUpdate()) return;
		
		if(e.button == 1)
		{
			if(!isCamera()) setAsCamera();
			else worldObj.playerMP.setAsCamera();
		}
	}
	
	public void keyPressed(EventKey.Pressed e)
	{
		if(!worldObj.canUpdate()) return;
		
		if(e.key == GameOptions.KEY_DEBUG.key)
			debug = !debug;
		else if(e.key == GameOptions.KEY_CAMERA.key)
		{
			if(!isCamera()) setAsCamera();
			else worldObj.playerMP.setAsCamera();
		}
		else if(e.key == Keyboard.KEY_T)
			setPos(Main.inst.mouseX, Main.inst.mouseY);
	}
}