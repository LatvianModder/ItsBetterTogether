package latmod.ibt;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.entity.*;
import latmod.ibt.tiles.*;

public class World
{
	public final int width, height;
	public String backgroundTex;
	private Texture texBG;
	public Block[][] blocks = null;
	public EntityPlayer player1, player2;
	public FastList<TileEntity> tileEntities = new FastList<TileEntity>();
	
	public World(int w, int h)
	{
		width = w;
		height = h;
		blocks = new Block[w][h];
		
		backgroundTex = "bg/metal.png";
		
		blocks[0][0] = blocks[1][0] = blocks[0][1] = blocks[1][1] = blocks[2][0] = Block.wall_stone;
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
		
		for(int y = 0; y < height; y++)
		for(int x = 0; x < width; x++)
		{
			if(blocks[x][y] != null)
			blocks[x][y].onRender(this, x, y);
		}
		
		Renderer.setTexture("gui/logo_128.png");
		Renderer.rect(2D, 2D, 8D, 8D);
	}
}