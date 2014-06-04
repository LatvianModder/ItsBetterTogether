package latmod.ibt.net;
import latmod.core.util.*;
import latmod.ibt.world.*;

public class PacketBlockSet extends Packet
{
	public int posX, posY, blockID;
	
	public PacketBlockSet()
	{ super(ID_NONE); }

	public void readPacket(DataIOStream dios) throws Exception
	{
		posX = dios.readByte();
		posY = dios.readByte();
		blockID = dios.readShort();
	}

	public void writePacket(DataIOStream dios) throws Exception
	{
		dios.writeByte(posX);
		dios.writeByte(posY);
		dios.writeShort(blockID);
	}

	public void processPacket(World w)
	{
	}
}