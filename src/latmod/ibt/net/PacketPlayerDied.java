package latmod.ibt.net;
import latmod.core.util.*;
import latmod.ibt.world.*;

public class PacketPlayerDied extends Packet
{
	public PacketPlayerDied()
	{ super(ID_PLAYER_DIED); }

	public void readPacket(DataIOStream dios) throws Exception
	{
	}

	public void writePacket(DataIOStream dios) throws Exception
	{
	}

	public void processPacket(World w)
	{
		printChat(w.playerMP.username + " died");
	}
}