package latmod.ibt;
import java.io.InputStream;
import java.util.logging.Logger;

import org.lwjgl.input.*;

import latmod.core.input.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.gui.*;
import latmod.ibt.world.*;

public class Main extends LMFrame implements IMouseListener.Scrolled, IKeyListener.Pressed
{
	public static FastMap<String, String> mainArgs;
	public static Main inst = null;
	public Main() { super(800, 600, 60); }
	public String getTitle() { return "It's Better Together"; }
	public static void main(String[] args) { mainArgs = LatCore.createArgs(args); inst = new Main(); }
	public static Logger logger = Logger.getLogger("Game");
	
	public double zoom = 64D;
	public double camX, camY;
	public boolean takingScreenshot = false;
	
	private GuiBasic openedGui = null;
	
	public void onLoaded()
	{
		LatCore.setProjectName("ItsBetterTogether");
		super.onLoaded();
		logger.setParent(LatCore.logger);
		
		GameOptions.loadOptions();
		
		openGui(new GuiStart());
		
		if(mainArgs.keys.contains("-openLevel"))
		{
			String json = mainArgs.get("-jsonLevel");
			String png = mainArgs.get("-pngLevel");
			
			if(json != null && png != null)
			{
				openLevel(WorldLoader.class.getResourceAsStream(json), WorldLoader.class.getResourceAsStream(png));
			}
		}
		
		for(Block b : Block.addedBlocks)
		b.reloadTextures();
		
		logger.info("Lan IP: " + LatCore.getHostAddress());
		logger.info("External IP: " + LatCore.getExternalAddress());
	}
	
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
			
			World.inst.renderer.onRender();
			
			Renderer.pop();
			
			World.inst.playerSP.onGuiRender();
		}
		
		openedGui.onRender();
		
		if(takingScreenshot)
		{
			Renderer.takeScreenshot();
			takingScreenshot = false;
		}
	}
	
	public void onFrameUpdate(Timer t)
	{
		if(World.inst != null)
		World.inst.onUpdate(t);
	}
	
	public Cancel onKeyPressed(int key, char keyChar)
	{
		if(key == Keyboard.KEY_ESCAPE)
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			destroy(); else openedGui.onEscPressed();
			return Cancel.TRUE;
		}
		else if(key == GameOptions.KEY_SCREENSHOT.key)
		{
			takingScreenshot = true;
			return Cancel.TRUE;
		}
		else if(key == GameOptions.KEY_TEST.key)
		{
		}
		
		return Cancel.FALSE;
	}
	
	public void openGui(GuiBasic g)
	{
		if(g == null) g = (World.inst != null ? new GuiIngame() : new GuiStart());
		if(openedGui != null)
		{
			openedGui.onReplacedBy(g);
			openedGui.onUnloaded();
			openedGui.onDestroyed();
			openedGui = null;
		}
		
		openedGui = g;
		onResized();
		
		Mouse.setGrabbed(g.allowPlayerInput());
	}
	
	public void onResized()
	{
		super.onResized();
		if(openedGui != null)
		{
			openedGui.width = width;
			openedGui.height = height;
			openedGui.onUnloaded();
			openedGui.onLoaded();
		}
	}
	
	public GuiBasic getGui()
	{ return openedGui; }
	
	public void onMouseScrolled(LMMouse m)
	{
		if(m.scroll < 0) zoom *= 0.5D;
		else zoom *= 2D;
		
		if(zoom > 256D) zoom = 256D;
		if(zoom < 8D) zoom = 8D;
	}
	
	public void openLevel(InputStream json, InputStream png)
	{
		World.inst = new World();
		WorldLoader.loadWorldFromStream(World.inst, json, png);
	}
	
	public void openLevel(String json, int[] pixels)
	{
		World.inst = new World();
		WorldLoader.loadWorldFromJson(World.inst, json, pixels);
	}
}