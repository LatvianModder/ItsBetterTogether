package latmod.ibt.world;
import latmod.core.rendering.Color;
import latmod.core.util.*;
import latmod.ibt.net.*;

public class WorldServer extends World
{
	public boolean worldSent = false;
	
	public WorldServer()
	{
		super(new NetServer());
	}
	
	public void onUpdate(Timer t)
	{
		if(!canUpdate()) return;
		
		super.onUpdate(t);
	}
	
	public boolean canUpdate()
	{ return worldSent && super.canUpdate(); }
	
	public void writeWorld(DataIOStream dios) throws Exception // WorldClient
	{
		dios.writeString(load_json);
		
		dios.writeInt(load_pixels.length);
		for(int i = 0; i < load_pixels.length; i++)
			dios.writeInt(load_pixels[i]);
		
		dios.writeString(playerSP.username);
		dios.writeInt(playerSP.color.hex);
		
		dios.writeDouble(playerSP.posX);
		dios.writeDouble(playerSP.posY);
		dios.writeInt(endPointSP);
		
		dios.writeDouble(playerMP.posX);
		dios.writeDouble(playerMP.posY);
		dios.writeInt(endPointMP);
	}
	
	public void readWorld(DataIOStream dios) throws Exception
	{
		playerMP.username = dios.readString();
		playerMP.color = Color.get(dios.readInt());
	}
}