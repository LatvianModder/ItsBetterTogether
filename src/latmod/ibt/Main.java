package latmod.ibt;
import latmod.core.rendering.*;
import latmod.core.util.*;

public class Main extends LMFrame
{
	public static Main inst = null;
	public Main() { super(800, 600, 60); }
	public String getTitle() { return "It's Better Together"; }
	public static void main(String[] args) { inst = new Main(); }
	
	public void onLoaded()
	{
		LatCore.setProjectName("ItsBetterTogether");
		super.onLoaded();
		
		setIcon("gui/logo_16.png", "gui/logo_32.png", "gui/logo_128.png");
	}
	
	public void onRender()
	{
		Renderer.enter2D();
		Renderer.disableTexture();
		Renderer.drawPoly(16D, 16D, 16D, 20D);
	}
}