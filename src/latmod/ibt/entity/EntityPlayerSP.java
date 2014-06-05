package latmod.ibt.entity;
import org.lwjgl.input.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.*;
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
	
	public void onUpdate(Timer t)
	{
		double speed = 0.1D;
		
		if(Main.inst.getGui().allowPlayerInput())
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) speed *= 1.7D;
			else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) speed *= 0.3D;
			
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) move(0D, -1D, speed);
			if(Keyboard.isKeyDown(Keyboard.KEY_S)) move(0D, 1D, speed);
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) move(-1D, 0D, speed);
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) move(1D, 0D, speed);
		}
		
		moveEntity();
	}
	
	public void keyPressed(int key)
	{
		if(key == GameOptions.KEY_DEBUG.key)
			debug = !debug;
	}
}