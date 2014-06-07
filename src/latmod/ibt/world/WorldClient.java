package latmod.ibt.world;
import latmod.core.util.*;
import latmod.ibt.net.*;

public class WorldClient extends World
{
	public WorldClient()
	{
		super(new NetClient());
	}
	
	public void onUpdate(Timer t)
	{
		playerSP.onUpdate(t);
	}
	
	public void readWorld(DataIOStream dios) throws Exception
	{
		load_json = dios.readString();
		
		load_pixels = new int[dios.readInt()];
		for(int i = 0; i < load_pixels.length; i++)
			load_pixels[i] = dios.readInt();
		
		WorldLoader.loadWorldFromJson(this, load_json, load_pixels);
		
		playerMP.username = dios.readString();
	}
}