package latmod.ibt.world;
import java.io.*;
import latmod.core.util.LatCore;
import latmod.ibt.blocks.Block;

import com.google.gson.annotations.Expose;

public class WorldLoader
{
	@Expose public String name;
	@Expose public String background;
	@Expose public int[] size;
	@Expose public double[] playerSP;
	@Expose public double[] playerMP;
	@Expose public BlockLoader[] blocks;
	@Expose public EntityLoader[] entities;
	
	public static void loadWorldFromJson(World w, String json)
	{
		WorldLoader wl = LatCore.getJson(json, WorldLoader.class);
		
		w.worldName = wl.name;
		w.width = wl.size[0];
		w.height = wl.size[1];
		w.backgroundTex = wl.background;
		
		w.postInit();
		
		w.playerSP.posX = wl.playerSP[0] + 0.5D;
		w.playerSP.posY = wl.playerSP[1] + 0.5D;
		
		w.playerMP.posX = wl.playerMP[0] + 0.5D;
		w.playerMP.posY = wl.playerMP[1] + 0.5D;
		
		if(wl.blocks != null) for(BlockLoader b : wl.blocks)
		{
			try
			{
				w.setBlock(b.coords[0], b.coords[1], Block.addedBlocks.get(b.id), b.data);
			}
			catch(Exception e)
			{ e.printStackTrace(); }
		}
	}
	
	public static void loadWorldFromStream(World w, InputStream is)
	{
		try
		{
			byte[] b = new byte[is.available()];
			is.read(b);
			loadWorldFromJson(w, new String(b));
		}
		catch(Exception e)
		{ e.printStackTrace(); }
	}
}