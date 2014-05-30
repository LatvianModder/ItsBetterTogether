package latmod.ibt;
import org.lwjgl.input.*;

import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.blocks.Block;

public class Main extends LMFrame
{
	public static Main inst = null;
	public Main() { super(800, 600, 60); }
	public String getTitle() { return "It's Better Together"; }
	public static void main(String[] args) { inst = new Main(); }
	
	public World worldObj = null;
	
	public final double zoom = 32D;
	public double camX, camY;
	
	public void onLoaded()
	{
		LatCore.setProjectName("ItsBetterTogether");
		super.onLoaded();
		
		worldObj = new World((int)(width / zoom) + 5, (int)(height / zoom) + 5);
		worldObj.postInit();
		
		for(Block b : Block.addedBlocks)
		b.reloadTextures();
		
		camX = 5.931D;
		camY = 3.211D;
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
		Renderer.translate(camX * worldObj.width, camY * worldObj.height);
		Renderer.scale(zoom, zoom, 1D);
		
		worldObj.onRender();
		
		Renderer.pop();
	}
}