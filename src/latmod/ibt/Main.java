package latmod.ibt;
import org.lwjgl.input.*;
import latmod.core.rendering.*;
import latmod.core.util.*;

public class Main extends LMFrame
{
	public static Main inst = null;
	public Main() { super(800, 600, 60); }
	public String getTitle() { return "It's Better Together"; }
	public static void main(String[] args) { inst = new Main(); }
	
	public World worldObj = null;
	
	public final double zoom = 64D;
	public double camX, camY;
	
	public void onLoaded()
	{
		LatCore.setProjectName("ItsBetterTogether");
		super.onLoaded();
		
		worldObj = new World((int)(width / zoom) + 5, (int)(height / zoom) + 5);
		worldObj.postInit();
		
		camX = 5.931D * worldObj.width;
		camY = 3.211D * worldObj.height;
	}
	
	public boolean isResizable()
	{ return false; }
	
	public void onRender()
	{
		Renderer.enter2D();
		Renderer.disableTexture();
		
		if(Mouse.isButtonDown(0))
		{
			camX += mouse.DX * 0.05D;
			camY += mouse.DY * 0.05D;
		}
		else if(Mouse.isButtonDown(1))
		{
			camX = camY = 0D;
		}
		
		Renderer.push();
		Renderer.translate(camX - width / 2D, camY - height / 2D);
		Renderer.scale(zoom, zoom, 1D);
		
		worldObj.onRender();
		
		Renderer.pop();
	}
}