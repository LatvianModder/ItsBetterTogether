package latmod.ibt.world;
import java.lang.reflect.Constructor;
import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.blocks.*;
import latmod.ibt.tiles.*;

public class IDReg
{
	public final World worldObj;
	public final SimpleReg blocks;
	//public final SimpleReg entities;
	public final SimpleReg tiles;
	
	public FastMap<Integer, Block> blockIDMap;
	
	//public FastMap<Integer, String> entityIDMap;
	//public FastMap<Integer, Class<? extends Entity>> entityClassMap;
	
	public FastMap<Integer, String> tileIDMap;
	public FastMap<Integer, Class<? extends TileEntity>> tileClassMap;
	
	public IDReg(World w)
	{
		worldObj = w;
		
		blocks = new SimpleReg("labmod");
		//entities = new SimpleReg("labmod");
		tiles = new SimpleReg("labmod");
		
		blockIDMap = new FastMap<Integer, Block>();
		
		//entityIDMap = new FastMap<Integer, String>();
		//entityClassMap = new FastMap<Integer, Class<? extends Entity>>();
		
		tileIDMap = new FastMap<Integer, String>();
		tileClassMap = new FastMap<Integer, Class<? extends TileEntity>>();
	}
	
	public void load()
	{
		// Register blocks & tiles //
		
		for(Block b : Block.addedBlocks)
		{
			blockIDMap.put(blocks.getOrCreateID(b.blockID), b);
			
			if(b instanceof ITileBlock)
			((ITileBlock)b).registerTiles(this);
		}
		
		// Register entities //
		
		// no entities yet
		
		// Print registred IDs //
		
		StringBuilder info = new StringBuilder();
		printReg(info, "blocks", blocks.getFullMap());
		//printReg(info, "entities", entities.getFullMap());
		printReg(info, "tiles", tiles.getFullMap());
		info.append('\n');
		Main.logger.info(info.toString());
	}
	
	private void printReg(StringBuilder info, String s, FastMap<String, Integer> m)
	{
		info.append("\n\nRegistred ");
		info.append(m.size());
		info.append(" ");
		info.append(s);
		info.append(":");
		
		for(int i = 0; i < m.size(); i++)
		{
			String key = m.keys.get(i);
			int id = m.values.get(i);
			info.append("\n[" + id + "]: " + key);
		}
	}
	
	public Block getBlock(int id)
	{ return blockIDMap.get(id); }
	
	/*public void addEntity(String s, Class<? extends Entity> c)
	{
		if(s == null || s.length() == 0 || c == null) return;
		int id = entities.getOrCreateID(s);
		entityIDMap.put(id, s);
		entityClassMap.put(id, c);
	}
	
	public Entity createEntity(String id)
	{ return createEntity(entityIDMap.getKey(id)); }
	
	public Entity createEntity(int id)
	{
		try
		{
			Constructor<? extends Entity> cons = entityClassMap.get(id).getConstructor(World.class);
			return cons.newInstance(worldObj);
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		
		return null;
	}
	*/
	
	public void addTile(String s, Class<? extends TileEntity> c)
	{
		if(s == null || s.length() == 0 || c == null) return;
		
		int id = tiles.getOrCreateID(s);
		tileIDMap.put(id, s);
		tileClassMap.put(id, c);
	}
	
	public TileEntity createTile(String id)
	{ return createTile(tileIDMap.getKey(id)); }
	
	public TileEntity createTile(int id)
	{
		try
		{
			Constructor<? extends TileEntity> cons = tileClassMap.get(id).getConstructor(World.class);
			return cons.newInstance(worldObj);
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		
		return null;
	}
	
	public void write(DataIOStream dios) throws Exception
	{
		blocks.write(dios);
		//entities.write(dios);
		tiles.write(dios);
	}
	
	public void read(DataIOStream dios) throws Exception
	{
		blocks.read(dios);
		//entities.read(dios);
		tiles.read(dios);
	}
}