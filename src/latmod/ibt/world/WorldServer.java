package latmod.ibt.world;
import latmod.core.util.*;
import latmod.ibt.net.*;

public class WorldServer extends World
{
	public WorldServer()
	{
		super(new NetServer());
	}
	
	public void writeWorld(DataIOStream dios) throws Exception
	{
		dios.writeString(load_json);
		
		dios.writeInt(load_pixels.length);
		for(int i = 0; i < load_pixels.length; i++)
			dios.writeInt(load_pixels[i]);
		
		dios.writeString(playerSP.username);
	}
}