package latmod.ibt;
import java.io.InputStream;
import java.util.logging.Logger;

import org.lwjgl.input.*;

import latmod.core.input.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.gui.*;
import latmod.ibt.tiles.TileRegistry;
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
	
	private GuiBasic openedGui = null;
	
	public double mouseX, mouseY;
	
	public void onLoaded()
	{
		LatCore.setProjectName("ItsBetterTogether");
		super.onLoaded();
		logger.setParent(LatCore.logger);
		
		loadGame();
		
		openGui(new GuiStart());
		
		if(mainArgs.keys.contains("-host"))
		{
			boolean b = Boolean.parseBoolean(mainArgs.get("-host"));
			String json = mainArgs.get("-json");
			String png = mainArgs.get("-png");
			
			if(json != null && png != null)
			{
				hostGame(WorldLoader.class.getResourceAsStream(json), WorldLoader.class.getResourceAsStream(png), b);
			}
		}
	}
	
	public void loadGame()
	{
		GameOptions.loadOptions();
		TileRegistry.loadTiles();
		
		for(Block b : Block.blockMap)
		b.reloadTextures();
	}
	
	public void onRender()
	{
		Renderer.enter2D();
		Renderer.disableTexture();
		
		if(World.inst != null && World.inst.playerSP != null)
		{
			mouseX = (mouse.x - width / 2D) / zoom + World.inst.playerSP.posX;
			mouseY = (mouse.y - height / 2D) / zoom + World.inst.playerSP.posY;
			
			Renderer.push();
			Renderer.translate(-(World.inst.playerSP.posX * zoom - width / 2D), -(World.inst.playerSP.posY * zoom - height / 2D));
			Renderer.scale(zoom, zoom, 1D);
			
			World.inst.renderer.onRender();
			
			Renderer.pop();
			
			World.inst.playerSP.onGuiRender();
		}
		else mouseX = mouseY = -1D;
		
		openedGui.onRender();
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
			Renderer.takeScreenshot();
			return Cancel.TRUE;
		}
		else
		{
			if(openedGui.allowPlayerInput() && World.inst != null && World.inst.playerSP != null)
				World.inst.playerSP.keyPressed(key);
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
	
	public void hostGame(boolean router)
	{
		hostGame(WorldLoader.class.getResourceAsStream("/levels/level1.json"), WorldLoader.class.getResourceAsStream("/levels/level1.png"), router);
	}
	
	public void hostGame(InputStream json, InputStream png, boolean router)
	{
		World.inst = new World();
		WorldLoader.loadWorldFromStream(World.inst, json, png);
		openGui(null);
	}
	
	public void hostGame(String json, int[] pixels, boolean router)
	{
		World.inst = new World();
		WorldLoader.loadWorldFromJson(World.inst, json, pixels);
		openGui(null);
	}
	
	public void joinGame(String ip)
	{
	}
}