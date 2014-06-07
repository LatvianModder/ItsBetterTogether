package latmod.ibt.world;
import java.util.*;
import latmod.core.util.*;
import latmod.core.util.Timer;
import latmod.ibt.blocks.*;
import latmod.ibt.entity.*;
import latmod.ibt.net.*;
import latmod.ibt.tiles.*;

public abstract class World // WorldClient // WorldServer
{
	public static World inst = null;
	
	public final INet net;
	public final WorldRenderer renderer;
	
	public String load_json = null;
	public int load_pixels[] = null;
	
	public String worldName;
	public int width = 1, height = 1;
	public String backgroundTex = "bg/metal.png";
	public FastMap<String, String> extraArgs;
	public int endPointSP, endPointMP;
	
	public FastMap<Integer, Block> blocks;
	public FastMap<Integer, TileEntity> tiles;
	public FastList<CollisionBox> collisionBoxes;
	
	public EntityPlayerSP playerSP;
	public EntityPlayerMP playerMP;
	public Random rand = new Random();
	
	public int[] powerNetwork;
	public FastList<IPowerProvider> powerProviders;

	public World(INet i)
	{
		net = i;
		
		renderer = new WorldRenderer(this);
		extraArgs = new FastMap<String, String>();
		
		blocks = new FastMap<Integer, Block>();
		tiles = new FastMap<Integer, TileEntity>();
		collisionBoxes = new FastList<CollisionBox>();
		
		powerNetwork = new int[64];
		powerProviders = new FastList<IPowerProvider>();
	}
	
	public void postInit()
	{
		renderer.postInit();
		playerSP = new EntityPlayerSP(this);
		playerMP = new EntityPlayerMP(this);
	}
	
	public void onUpdate(Timer t)
	{
		collisionBoxes.clear();
		
		for(int i = 0; i < blocks.size(); i++)
		{
			Integer c = blocks.keys.get(i);
			Block b = blocks.values.get(i);
			
			int x = getX(c);
			int y = getY(c);
			
			if(b.isSolidFor(this, x, y, playerSP))
			collisionBoxes.add(new CollisionBox(x, y, x + 1D, y + 1D));
		}
		
		powerNetwork = new int[powerNetwork.length];
		for(IPowerProvider i : powerProviders)
		if(i.isProvidingPower()) powerNetwork[i.getFreq()]++;
		
		for(TileEntity te : tiles)
		te.onUpdate(t);
		
		playerSP.onUpdate(t);
		playerMP.onUpdate(t);
	}
	
	public int getX(int index)
	{ return index % width; }
	
	public int getY(int index)
	{ return index / width; }
	
	public int getIndex(double x, double y)
	{ return (int)x + (int)y * width; }
	
	public Block getBlock(double x, double y)
	{ return blocks.get(getIndex(x, y)); }
	
	public void setBlock(double x, double y, Block b, ExtraData data)
	{
		int ix = (int)x;
		int iy = (int)y;
		int index = getIndex(x, y);
		
		Block b0 = blocks.get(index);
		
		if(b0 != null)
		{
			b0.onDestroyed(this, ix, iy);
			
			if(b0.hasTile)
			{
				TileEntity te = tiles.get(index);
				if(te != null)
				{
					te.onDestroyed();
					
					if(te instanceof IPowerProvider)
					powerProviders.remove(te);
				}
				tiles.remove(index);
			}
			
			blocks.remove(index);
		}
		
		if(b != null)
		{
			blocks.put(index, b);
			
			if(b.hasTile)
			{
				TileEntity te = ((ITileBlock)b).createTile(this);
				
				if(te != null)
				{
					te.posX = ix;
					te.posY = iy;
					te.type = b;
					tiles.put(index, te);
					
					te.loadTile((data == null) ? new ExtraData() : data);
					setTile(x, y, te);
					
					if(te instanceof IPowerProvider)
					powerProviders.add((IPowerProvider)te);
				}
			}
			
			b.onCreated(this, ix, iy);
		}
	}
	
	public void setBlock(double x, double y, Block b)
	{ setBlock(x, y, b, null); }
	
	public TileEntity getTile(double x, double y)
	{ return tiles.get(getIndex(x, y)); }
	
	public void setTile(double x, double y, TileEntity te)
	{ tiles.put(getIndex(x, y), te); }
	
	public FastList<CollisionBox> getAllBlocks(CollisionBox cb, double ox, double oy)
	{
		FastList<CollisionBox> list = new FastList<CollisionBox>();
		
		for(int i = 0; i < blocks.size(); i++)
		{
			Integer c = blocks.keys.get(i);
			
			int x = getX(c);
			int y = getY(c);
			
			CollisionBox cb1 = new CollisionBox(x, y, x + 1D, y + 1D);
			if(cb.isColliding(cb1, ox, oy)) list.add(cb1);
		}
		
		return list;
	}
	
	public boolean isPlayerAtEnd(EntityPlayer ep)
	{
		double x = getX((ep instanceof EntityPlayerSP) ? endPointSP : endPointMP) + 0.5D;
		double y = getY((ep instanceof EntityPlayerSP) ? endPointSP : endPointMP) + 0.5D;
		return MathHelper.distSq(ep.posX, ep.posY, x, y) <= 0.25D;
	}
	
	public boolean isSolidBlock(double x, double y)
	{
		Block b = getBlock(x, y);
		return (b != null && b.isSolidFor(this, (int)x, (int)y, null));
	}
	
	public void onClosed()
	{
	}
	
	public WorldServer getServer()
	{ return (WorldServer)this; }
	
	public WorldClient getClient()
	{ return (WorldClient)this; }

	public NetServer getNetServer()
	{ return (NetServer)net; }
	
	public NetClient getNetClient()
	{ return (NetClient)net; }
}