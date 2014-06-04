package latmod.ibt.net;
import latmod.core.util.*;
import latmod.ibt.world.*;

public class PacketEmpty extends Packet
{
	public PacketEmpty()
	{ super(ID_NONE); }

	public void readPacket(DataIOStream dios) throws Exception
	{
	}

	public void writePacket(DataIOStream dios) throws Exception
	{
	}

	public void processPacket(World w)
	{
	}
}