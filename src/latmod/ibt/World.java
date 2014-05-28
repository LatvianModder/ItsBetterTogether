package latmod.ibt;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;

public class World
{
	public final int width, height;
	public String backgroundTex;
	private Texture texBG;
	
	public Block[] blocks = null;
	
	public World(int w, int h)
	{
		width = w;
		height = h;
		blocks = new Block[w * h];
		
		backgroundTex = "bg/metal.png";
	}
	
	public void postInit()
	{
		texBG = Renderer.getTexture(backgroundTex);
	}
	
	public void onUpdate(Timer t)
	{
	}
	
	public void onRender()
	{
		Renderer.enableTexture();
		Renderer.setTexture(texBG);
		
		for(int y = 0; y < height + 1; y++)
		for(int x = 0; x < width + 1; x++)
		Renderer.rect(x, y, 1D, 1D);
		
		Renderer.setTexture("gui/logo_128.png");
		Renderer.rect(2D, 2D, 8D, 8D);
	}
}