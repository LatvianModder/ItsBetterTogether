package latmod.ibt.net;
import latmod.core.util.*;
import latmod.ibt.blocks.Block;
import latmod.ibt.world.*;

public class PacketBlockSet extends Packet
{
	public int posX, posY;
	public Block block;
	
	public PacketBlockSet()
	{ super(ID_NONE); }
	
	public PacketBlockSet(int x, int y, Block b)
	{
		this();
		posX = x;
		posY = y;
		block = b;
	}
	
	public void writePacket(World w, DataIOStream dios) throws Exception
	{
		dios.writeByte(posX);
		dios.writeByte(posY);
		dios.writeShort((block == null) ? 0 : block.blockID);
	}

	public void readPacket(World w, DataIOStream dios) throws Exception
	{
		posX = dios.readByte();
		posY = dios.readByte();
		int bid = dios.readShort();
		block = (bid == 0) ? null : Block.blockMap.get(bid);
		
		w.setBlock(posX, posY, block);
	}
}