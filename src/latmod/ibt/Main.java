package latmod.ibt;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import org.lwjgl.input.*;

import latmod.core.input.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.entity.*;
import latmod.ibt.gui.*;
import latmod.ibt.net.Packet;
import latmod.ibt.tiles.TileRegistry;
import latmod.ibt.world.*;

public class Main extends LMFrame implements IMouseListener.Scrolled, IMouseListener.Pressed, IKeyListener.Pressed
{
	public static FastMap<String, String> mainArgs;
	public static Main inst = null;
	public Main() { super(800, 600, 60); }
	public String getTitle() { return "It's Better Together"; }
	public static void main(String[] args) { mainArgs = LatCore.createArgs(args); inst = new Main(); }
	public static Logger logger = Logger.getLogger("Game");
	
	public double zoom = 64D;
	
	private GuiBasic openedGui = null;
	private String usernameOverride = null;
	private String colorOverride = null;
	
	public Entity cameraEntity = null;
	public double mouseX, mouseY;
	
	public void onLoaded()
	{
		LatCore.setProjectName("ItsBetterTogether");
		
		String widthS = mainArgs.get("-width");
		if(widthS != null) width = Integer.parseInt(widthS);
		
		String heightS = mainArgs.get("-height");
		if(heightS != null) height = Integer.parseInt(heightS);
		
		super.onLoaded();
		logger.setParent(LatCore.logger);
		
		int s = LatCore.getAllClassesInDir(Main.class).size();
		s += LatCore.getAllClassesInDir(LatCore.class).size();
		
		logger.info("Found totally " + s + " classes in this game");
		
		loadGame();
		
		openGui(new GuiStart());
		
		executeArguments(mainArgs);
	}
	
	public void executeArguments(FastMap<String, String> args)
	{
		if(args.keys.contains("-host"))
		{
			boolean router = args.keys.contains("-router");
			String portS = args.get("-port");
			int port = (portS == null) ? GameOptions.DEF_PORT : Integer.parseInt(portS);
			
			InputStream json = null;
			InputStream png = null;
			
			String stream = args.get("-levelStream");
			if(stream != null)
			{
				json = WorldLoader.class.getResourceAsStream(stream + ".json");
				png = WorldLoader.class.getResourceAsStream(stream + ".png");
			}
			
			String url = args.get("-levelURL");
			if(url != null)
			{
				try
				{
					json = new URL(url + ".json").openStream();
					png = new URL(url + ".png").openStream();
				}
				catch(Exception e)
				{ e.printStackTrace(); json = png = null; }
			}
			
			String gitHub = args.get("-levelGitHub");
			if(gitHub != null)
			{
				try
				{
					json = new URL("https://raw.githubusercontent.com/" + url + ".json").openStream();
					png = new URL("https://raw.githubusercontent.com/" + url + ".png").openStream();
				}
				catch(Exception e)
				{ e.printStackTrace(); json = png = null; }
			}
			if(json != null && png != null)
				hostGame(json, png, port, router);
		}
		else if(args.keys.contains("-join"))
		{
			String ip = args.get("-join");
			String portS = args.get("-port");
			int port = (portS == null) ? GameOptions.DEF_PORT : Integer.parseInt(portS);
			
			World.inst = new WorldClient();
			World.inst.postInit();
			openGui(new GuiJoinWait(ip, port));
		}
		
		usernameOverride = args.get("-username");
		colorOverride = args.get("-color");
	}
	
	public void executeArguments(String... args)
	{ executeArguments(LatCore.createArgs(args)); }
	
	public String getPlayerUsername()
	{ if(usernameOverride != null) return usernameOverride;
	return GameOptions.props.username; }
	
	public String getPlayerColor()
	{ if(colorOverride != null) return colorOverride;
	return GameOptions.props.playerColor; }
	
	public void loadGame()
	{
		GameOptions.loadOptions();
		TileRegistry.loadTiles();
		Packet.loadPackets();
		
		for(Block b : Block.blockMap)
		b.reloadTextures();
	}
	
	public void onRender()
	{
		Renderer.enter2D();
		Renderer.disableTexture();
		
		if(World.inst != null && World.inst.canUpdate())
		{
			if(cameraEntity == null) cameraEntity = World.inst.playerSP;
			
			mouseX = (mouse.x - width / 2D) / zoom + cameraEntity.posX;
			mouseY = (mouse.y - height / 2D) / zoom + cameraEntity.posY;
			
			Renderer.push();
			Renderer.translate(-(cameraEntity.posX * zoom - width / 2D), -(cameraEntity.posY * zoom - height / 2D));
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
	
	public void onMouseScrolled(EventMouse.Scrolled e)
	{
		if(e.scroll < 0) zoom *= 0.5D;
		else zoom *= 2D;
		
		if(zoom > 256D) zoom = 256D;
		if(zoom < 8D) zoom = 8D;
	}
	
	public void onMousePressed(EventMouse.Pressed e)
	{
		if(openedGui.allowPlayerInput() && World.inst != null && World.inst.playerSP != null)
			World.inst.playerSP.mousePressed(e);
	}
	
	public void onKeyPressed(EventKey.Pressed e)
	{
		if(e.key == Keyboard.KEY_ESCAPE)
		{
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			destroy(); else openedGui.onEscPressed();
			e.cancel();
		}
		else if(e.key == GameOptions.KEY_SCREENSHOT.key)
		{
			Renderer.takeScreenshot();
			e.cancel();
		}
		else
		{
			if(openedGui.allowPlayerInput() && World.inst != null && World.inst.playerSP != null)
				World.inst.playerSP.keyPressed(e);
		}
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
	
	public void hostGame(InputStream json, InputStream png, int port, boolean router)
	{
		World.inst = new WorldServer();
		WorldLoader.loadWorldFromStream(World.inst, json, png);
		openGui(null);
	}
	
	public void hostGame(String json, int[] pixels, int port, boolean router)
	{
		World.inst = new WorldServer();
		WorldLoader.loadWorldFromJson(World.inst, json, pixels);
		openGui(null);
	}
	
	public void closeGame()
	{
		if(World.inst != null)
		{
			World.inst.onClosed();
			World.inst = null;
		}
		
		openGui(null);
	}
}