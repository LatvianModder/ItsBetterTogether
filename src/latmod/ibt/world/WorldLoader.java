package latmod.ibt.world;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import latmod.core.rendering.Color;
import latmod.core.rendering.Renderer;
import latmod.core.util.*;
import latmod.ibt.blocks.Block;

import com.google.gson.annotations.Expose;

public class WorldLoader
{
	@Expose public String name;
	@Expose public String background;
	@Expose public int[] size;
	@Expose public String playerSP;
	@Expose public String playerMP;
	@Expose public Map<String, BlockLoader> blocks;
	
	public static void loadWorldFromJson(World w, String json, int[] pixels)
	{
		WorldLoader wl = LatCore.getJson(json, WorldLoader.class);
		
		w.worldName = wl.name;
		w.width = wl.size[0];
		w.height = wl.size[1];
		w.backgroundTex = wl.background;
		
		w.postInit();
		
		int playerSPCoord = getCol(wl.playerSP);
		int playerMPCoord = getCol(wl.playerMP);
		
		FastMap<Integer, BlockLoader> blocksCols = new FastMap<Integer, BlockLoader>();
		
		{
			Iterator<String> bKeys = wl.blocks.keySet().iterator();
			Iterator<BlockLoader> bBlocks = wl.blocks.values().iterator();
			
			while(bKeys.hasNext())
			{
				String s = bKeys.next();
				BlockLoader b = bBlocks.next();
				
				Integer i = getCol(s);
				if(i != null) blocksCols.put(i, b);
			}
		}
		
		for(int i = 0; i < w.width * w.height; i++)
		{
			if(pixels[i] != 0xFF000000)
			{
				int x = w.getX(i);
				int y = w.getY(i);
				
				if(pixels[i] == playerSPCoord)
					w.playerSP.setPos(x + 0.5D, y + 0.5D);
				
				else if(pixels[i] == playerMPCoord)
					w.playerMP.setPos(x + 0.5D, y + 0.5D);
				else
				{
					BlockLoader b = blocksCols.get(pixels[i]);
					if(b != null) w.setBlock(x, y, Block.addedBlocks.get(b.id), b.data);
					else w.setBlock(x, y, Block.unknown);
				}
			}
		}
	}
	
	public static int getCol(String s)
	{ return Color.get(Converter.decode('#' + s), 255).hex; }
	
	public static void loadWorldFromStream(World w, InputStream json, InputStream image)
	{
		try
		{
			byte[] b = new byte[json.available()];
			json.read(b);
			
			BufferedImage img = ImageIO.read(image);
			int[] pixels = Renderer.getPixels(img);
			loadWorldFromJson(w, new String(b), pixels);
		}
		catch(Exception e)
		{ e.printStackTrace(); }
	}
}