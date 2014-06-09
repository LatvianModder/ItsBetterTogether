package latmod.ibt.net;
import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.world.*;

public class PacketChat extends Packet
{
	public String text;
	
	public PacketChat(String s)
	{ super(ID_CHAT); text = s; }
	
	public void writePacket(World w, DataIOStream dios) throws Exception
	{
		dios.writeString(text);
	}
	
	public void readPacket(World w, DataIOStream dios) throws Exception
	{
		text = dios.readString();
		Main.logger.info(text);
	}
}