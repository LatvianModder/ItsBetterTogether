package latmod.ibt.net.packets;
import latmod.core.util.*;
import latmod.ibt.net.NetClient;
import latmod.ibt.world.*;

public class PacketHandler
{
	public final World worldObj;
	
	public FastList<Packet> packetsToSend;
	public FastList<Packet> packetsToAdd;
	
	public long packetsRX = 0L;
	public long packetsTX = 0L;
	
	public PacketHandler(World w)
	{
		worldObj = w;
		packetsToSend = new FastList<Packet>();
		packetsToAdd = new FastList<Packet>();
	}
	
	public void update(NetClient c) throws Exception
	{
		if(packetsToAdd.size() > 0)
		{
			packetsToSend.addAll(packetsToAdd);
			packetsToAdd.clear();
		}
		
		if(packetsToSend.size() > 0)
		{
			for(int i = 0; i < packetsToSend.size(); i++)
			{
				Packet p = packetsToSend.get(i);
				c.data.writeByte(p.packetID);
				p.writePacket(worldObj, c.data);
				packetsTX++;
			}
			
			c.data.flush();
		}
		
		packetsToSend.clear();
		
		while(c.data.available() > 0)
		{
			int id = c.data.readByte();
			Packet.createPacket(id).readPacket(worldObj, c.data);
			packetsRX++;
		}
	}

	public void sendPacket(Packet p)
	{ packetsToAdd.add(p); }
}