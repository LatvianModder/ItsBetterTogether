package latmod.ibt;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import org.lwjgl.input.*;

import latmod.core.input.*;
import latmod.core.rendering.*;
import latmod.core.res.Resource;
import latmod.core.util.*;
import latmod.ibt.entity.*;
import latmod.ibt.gui.*;
import latmod.ibt.net.packets.Packet;
import latmod.ibt.tiles.TileRegistry;
import latmod.ibt.world.*;

public class Main extends LMFrame implements IMouseListener.Scrolled, IMouseListener.Pressed, IKeyListener.Pressed
{
	public static Main inst = null;
	public Main(String[] args) { super(args, 800, 600, 60); }
	public static void main(String[] args)
	{ inst = new Main(args); }
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
		
		width = mainArgs.getN("width", width).shortValue();
		height = mainArgs.getN("height", height).shortValue();
		
		super.onLoaded();
		logger.setParent(LatCore.logger);
		
		int s = LMCommon.getAllClassesInDir(Main.class).size();
		s += LMCommon.getAllClassesInDir(LMCommon.class).size();
		
		logger.info("Found totally " + s + " classes in this game");
		
		loadGame();
		
		openGui(new GuiStart());
		
		executeArguments(mainArgs);
	}
	
	public void executeArguments(MainArgs args)
	{
		if(args.has("host"))
		{
			boolean router = args.getB("router", false);
			int port = args.getN("port", GameOptions.DEF_PORT).intValue();
			
			InputStream json = null;
			InputStream png = null;
			
			String stream = args.getS("levelStream", null);
			
			if(stream != null)
			{
				json = WorldLoader.class.getResourceAsStream(stream + ".json");
				png = WorldLoader.class.getResourceAsStream(stream + ".png");
			}
			
			String url = args.getS("levelURL", null);
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
			
			String gitHub = args.getS("levelGitHub", null);
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
			{
				World.inst = new WorldServer();
				WorldLoader.loadWorld(World.inst, Main.inst.resManager, Resource.get("/levels/level1.json"), Resource.get("/levels/level1.png"));
				World.inst.getNetServer().open(port, router);
				
				Main.logger.info("Hosted server @ port " + port + (router ? " with" : " without") + " router");
			}
		}
		else if(args.has("join"))
		{
			String ip = args.getS("join", "127.0.0.1");
			int port = args.getN("port", GameOptions.DEF_PORT).intValue();
			
			World.inst = new WorldClient();
			World.inst.postInit();
			openGui(new GuiJoinWait(ip, port));
		}
		
		usernameOverride = args.getS("username", null);
		colorOverride = args.getS("color", null);
	}
	
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