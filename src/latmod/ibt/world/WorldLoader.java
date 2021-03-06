package latmod.ibt.world;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.imageio.ImageIO;
import latmod.core.rendering.*;
import latmod.core.res.*;
import latmod.core.util.*;
import latmod.ibt.blocks.Block;

import com.google.gson.annotations.Expose;

public class WorldLoader
{
	@Expose public String name;
	@Expose public String background;
	@Expose public int[] size;
	@Expose public String[] playerSP;
	@Expose public String[] playerMP;
	@Expose public Map<String, BlockLoader> blocks;
	@Expose public String[] extraArgs;
	
	public static void loadWorld(World w, ResourceManager rm, Resource json, Resource png)
	{
		try
		{
			BufferedImage img = ImageIO.read(rm.getInputStream(png));
			int[] pixels = Renderer.getPixels(img);
			loadWorld(w, rm.readText(json), pixels);
		}
		catch(Exception e)
		{ e.printStackTrace(); }
	}
	
	public static boolean loadWorld(World w, String json, int[] pixels)
	{
		w.load_json = json;
		w.load_pixels = pixels;
		
		WorldLoader wl = LatCore.getJson(json, WorldLoader.class);
		
		w.worldName = wl.name;
		w.width = wl.size[0];
		w.height = wl.size[1];
		
		if(w.width >= 256 || w.height >= 256) return false;
		
		w.backgroundTex = wl.background;
		if(w.backgroundTex == null) w.backgroundTex = "";
		
		w.extraArgs = new MainArgs(wl.extraArgs);
		
		w.postInit();
		
		Integer playerSPCoord = getCol(wl.playerSP[0]);
		Integer playerMPCoord = getCol(wl.playerMP[0]);
		
		if(playerSPCoord == null || playerMPCoord == null) return false;
		
		Integer playerSPEnd = getCol(wl.playerSP[1]);
		Integer playerMPEnd = getCol(wl.playerMP[1]);
		
		if(playerSPEnd == null) playerSPEnd = 0xFFFFFFFF;
		if(playerMPEnd == null) playerMPEnd = 0xFFFFFFFF;
		
		FastMap<Integer, BlockLoader> blocksCols = new FastMap<Integer, BlockLoader>();
		
		Iterator<String> bKeys = wl.blocks.keySet().iterator();
		Iterator<BlockLoader> bBlocks = wl.blocks.values().iterator();
		
		while(bKeys.hasNext())
		{
			String s = bKeys.next();
			BlockLoader b = bBlocks.next();
			
			Integer i = getCol(s);
			if(i != null) blocksCols.put(i, b);
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
				else if(pixels[i] == playerSPEnd)
					w.endPointSP = i;
				else if(pixels[i] == playerMPEnd)
					w.endPointMP = i;
				else
				{
					BlockLoader b = blocksCols.get(pixels[i]);
					if(b != null) w.setBlock(x, y, Block.blockNameMap.get(b.id), b.getData());
					else w.setBlock(x, y, Block.unknown);
				}
			}
		}
		
		return true;
	}
	
	public static Integer getCol(String s)
	{
		Integer i = Converter.decode('#' + s);
		if(i == null) return null;
		return Color.get(i, 255).hex;
	}
}