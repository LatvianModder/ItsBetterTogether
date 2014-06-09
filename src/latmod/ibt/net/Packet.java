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
	
	public String toString()
	{ return getClass().getSimpleName(); }
	
	// -- Static -- //
	
	public static final Packet[] packetMap = new Packet[256];
	
	public static final int ID_NONE = 0;
	public static final int ID_CHAT = 1;
	public static final int ID_PLAYER_UPDATE = 2;
	public static final int ID_PLAYER_DIED = 3;
	public static final int ID_BLOCK_SET = 4;
	public static final int ID_TILE_UPDATE = 5;
	public static final int ID_ENTITY_CREATED = 6;
	public static final int ID_ENTITY_UPDATE = 7;
	public static final int ID_ENTITY_DESTROYED = 8;
	
	public static void addPacket(Packet p)
	{
		if(packetMap[p.packetID] == null) packetMap[p.packetID] = p;
		else Main.logger.info("PacketID " + p.packetID + " for " + p + " already occuped by " + packetMap[p.packetID]);
	}
	
	public static void loadPackets()
	{
		addPacket(new PacketChat(null));
		addPacket(new PacketPlayerUpdate());
		addPacket(new PacketPlayerDied());
		addPacket(new PacketTileUpdate(null));
	}
	
	public static Packet createPacket(int i)
	{ return (i <= 0 || i >= packetMap.length) ? null : packetMap[i]; }
}