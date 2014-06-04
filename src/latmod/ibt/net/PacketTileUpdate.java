package latmod.ibt.net;
import latmod.core.nbt.NBTMap;
import latmod.core.util.*;
import latmod.ibt.tiles.TileEntity;
import latmod.ibt.world.*;

public class PacketTileUpdate extends Packet
{
	public int posX, posY;
	public NBTMap dataMap;
	
	public PacketTileUpdate()
	{ super(ID_TILE_UPDATE); }
	
	public PacketTileUpdate(TileEntity te)
	{
		this();
		posX = te.posX;
		posY = te.posY;
		dataMap = new NBTMap(null);
		te.writeToNBT(dataMap);
	}
	
	public void readPacket(DataIOStream dios) throws Exception
	{
		posX = dios.readByte();
		posY = dios.readByte();
		dataMap = new NBTMap(null);
		dataMap.read(dios);
	}

	public void writePacket(DataIOStream dios) throws Exception
	{
		dios.writeByte(posX);
		dios.writeByte(posY);
		dataMap.write(dios);
	}

	public void processPacket(World w)
	{
		w.playerMP.readFromNBT(dataMap);
	}
}