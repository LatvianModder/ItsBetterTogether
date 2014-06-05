package latmod.ibt.net;
import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.world.*;

public abstract class Packet
{
	public final int packetID;
	
	public Packet(int i)
	{ packetID = i; }
	
	public abstract void writePacket(World w, DataIOStream dios) throws Exception;
	public abstract void readPacket(World w, DataIOStream dios) throws Exception;
	
	public final void processPacket(World w) {}
	
	public void printChat(String s)
	{
		Main.logger.info(s);
	}
	
	// -- Static -- //
	
	@SuppressWarnings("all")
	public static final Class[] packetMap = new Class[256];
	
	public static final int ID_NONE = 0;
	public static final int ID_CHAT = 1;
	public static final int ID_PLAYER_UPDATE = 2;
	public static final int ID_PLAYER_DIED = 3;
	public static final int ID_BLOCK_SET = 4;
	public static final int ID_TILE_UPDATE = 5;
	public static final int ID_ENTITY_CREATED = 6;
	public static final int ID_ENTITY_UPDATE = 7;
	public static final int ID_ENTITY_DESTROYED = 8;
	
	public static void addPacket(Class<? extends Packet> c)
	{
		try
		{
			Packet p = (Packet) c.newInstance();
			int pid = p.packetID;
			
			if(pid > 0 && pid < packetMap.length)
			{
				if(packetMap[pid] == null)
				packetMap[pid] = c;
				else Main.logger.info("PacketID " + pid + " for " + c.getSimpleName() + " already occuped by " + packetMap[pid].getSimpleName());
			}
			else Main.logger.info("Invalid PacketID " + pid + " for " + c.getSimpleName());
		}
		catch(Exception e)
		{ e.printStackTrace(); }
	}
	
	public static void loadPackets()
	{
		addPacket(PacketPlayerUpdate.class);
		addPacket(PacketPlayerDied.class);
		addPacket(PacketBlockSet.class);
		addPacket(PacketTileUpdate.class);
		//addPacket(PacketEntityCreated.class);
		//addPacket(PacketEntityUpdate.class);
		//addPacket(PacketEntityDestroyed.class);
	}
	
	public static Packet createPacket(int i)
	{
		if(i <= 0 || i >= packetMap.length) return null;
		
		try { return (Packet) packetMap[i].newInstance(); }
		catch(Exception e)
		{ e.printStackTrace(); }
		
		return null;
	}
}