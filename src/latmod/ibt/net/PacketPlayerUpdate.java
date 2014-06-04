package latmod.ibt.net;
import latmod.core.nbt.*;
import latmod.core.util.*;
import latmod.ibt.entity.*;
import latmod.ibt.world.*;

public class PacketPlayerUpdate extends Packet
{
	public NBTMap dataMap;
	
	public PacketPlayerUpdate()
	{ super(ID_PLAYER_UPDATE); }
	
	public PacketPlayerUpdate(EntityPlayerSP ep)
	{
		this();
		dataMap = new NBTMap(null);
		ep.writeToNBT(dataMap);
	}
	
	public void readPacket(DataIOStream dios) throws Exception
	{
		dataMap = new NBTMap(null);
		dataMap.read(dios);
	}

	public void writePacket(DataIOStream dios) throws Exception
	{
		dataMap.write(dios);
	}

	public void processPacket(World w)
	{
		w.playerMP.readFromNBT(dataMap);
	}
}