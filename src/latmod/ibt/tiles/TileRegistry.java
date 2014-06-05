package latmod.ibt.tiles;
import java.lang.reflect.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class TileRegistry
{
	public static FastMap<Integer, Class<? extends TileEntity>> tileClassMap;
	
	public static void loadTiles()
	{
		tileClassMap = new FastMap<Integer, Class<? extends TileEntity>>();
		
		for(Block b : Block.blockMap)
		if(b instanceof ITileBlock)
		((ITileBlock)b).registerTiles();
	}
	
	public static void addTile(Block b, Class<? extends TileEntity> c)
	{
		if(b == null || c == null) return;
		tileClassMap.put(b.blockID, c);
	}
	
	public static TileEntity createTile(World w, int id)
	{
		try
		{
			Constructor<? extends TileEntity> cons = tileClassMap.get(id).getConstructor(World.class);
			return cons.newInstance(w);
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		
		return null;
	}
}