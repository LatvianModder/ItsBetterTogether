package latmod.ibt.net;
import latmod.core.util.*;
import latmod.ibt.world.*;

public class PacketPlayerUpdate extends Packet
{
	public PacketPlayerUpdate()
	{ super(ID_PLAYER_UPDATE); }
	
	public void writePacket(World w, DataIOStream dios) throws Exception
	{
		w.playerSP.writeEntity(dios);
	}
	
	public void readPacket(World w, DataIOStream dios) throws Exception
	{
		w.playerMP.readEntity(dios);
	}
}