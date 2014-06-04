package latmod.ibt.net;
import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.world.*;

public class PacketChat extends Packet
{
	public String text;
	
	public PacketChat()
	{ super(ID_CHAT); }
	
	public PacketChat(String s)
	{ this(); text = s; }
	
	public void readPacket(DataIOStream dios) throws Exception
	{
		text = dios.readString();
	}

	public void writePacket(DataIOStream dios) throws Exception
	{
		dios.writeString(text);
	}

	public void processPacket(World w)
	{
		Main.logger.info(text);
	}
}