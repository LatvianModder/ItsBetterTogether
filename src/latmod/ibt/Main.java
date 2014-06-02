package latmod.ibt;
import java.util.logging.Logger;

import org.lwjgl.input.*;

import latmod.core.input.IMouseListener;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class Main extends LMFrame implements IMouseListener.Scrolled
{
	public static Main inst = null;
	public Main() { super(800, 600, 60); }
	public String getTitle() { return "It's Better Together"; }
	public static void main(String[] args) { inst = new Main(); }
	public static Logger logger = Logger.getLogger("Game");
	
	public double zoom = 64D;
	public double camX, camY;
	
	public void onLoaded()
	{
		LatCore.setProjectName("ItsBetterTogether");
		super.onLoaded();
		logger.setParent(LatCore.logger);
		
		World.inst = new World();
		WorldLoader.loadWorldFromStream(World.inst, WorldLoader.class.getResourceAsStream("/levels/level1.json"), WorldLoader.class.getResourceAsStream("/levels/level1.png"));
		
		for(Block b : Block.addedBlocks)
		b.reloadTextures();
		
		logger.info("Color: " + Integer.toHexString(Color.MAGENTA.hex).toUpperCase());
	}
	
	public boolean isResizable()
	{ return true; }
	
	public void onRender()
	{
		Renderer.enter2D();
		Renderer.disableTexture();
		
		if(Mouse.isButtonDown(0))
		{
			camX += mouse.DX * 0.009D;
			camY += mouse.DY * 0.009D;
		}
		else if(Mouse.isButtonDown(1))
		{
			camX = camY = 0D;
		}
		
		if(World.inst != null)
		{
			Renderer.push();
			Renderer.translate(-(World.inst.playerSP.posX * zoom - width / 2D), -(World.inst.playerSP.posY * zoom - height / 2D));
			Renderer.scale(zoom, zoom, 1D);
			
			World.inst.onRender();
			
			Renderer.pop();
			
			World.inst.playerSP.onGuiRender();
		}
	}
	
	public void onFrameUpdate(Timer t)
	{
		if(World.inst != null)
		World.inst.onUpdate(t);
	}
	
	public void onMouseScrolled(LMMouse m)
	{
		if(m.scroll < 0) zoom *= 0.5D;
		else zoom *= 2D;
		
		if(zoom > 256D) zoom = 256D;
		if(zoom < 8D) zoom = 8D;
	}
}