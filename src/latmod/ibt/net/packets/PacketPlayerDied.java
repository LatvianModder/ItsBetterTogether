package latmod.ibt.net.packets;
import latmod.core.util.*;
import latmod.ibt.world.*;

public class PacketPlayerDied extends Packet
{
	public PacketPlayerDied()
	{ super(ID_PLAYER_DIED); }

	public void writePacket(World w, DataIOStream dios) throws Exception
	{
	}
	
	public void readPacket(World w, DataIOStream dios) throws Exception
	{
		printChat(w.playerMP.username + " died");
	}
}