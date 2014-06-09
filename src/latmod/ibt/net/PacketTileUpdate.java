package latmod.ibt.net;
import latmod.core.util.*;
import latmod.ibt.tiles.*;
import latmod.ibt.world.*;

public class PacketTileUpdate extends Packet
{
	public TileEntity tile;
	
	public PacketTileUpdate(TileEntity te)
	{
		super(ID_TILE_UPDATE);
		tile = te;
	}
	
	public void writePacket(World w, DataIOStream dios) throws Exception
	{
		dios.writeByte(tile.posX);
		dios.writeByte(tile.posY);
		tile.writeTile(dios);
	}
	
	public void readPacket(World w, DataIOStream dios) throws Exception
	{
		int x = dios.readByte();
		int y = dios.readByte();
		
		TileEntity te = w.getTile(x, y);
		te.readTile(dios);
	}
}