package latmod.ibt.world;
import latmod.core.rendering.Color;
import latmod.core.util.*;
import latmod.ibt.net.*;

public class WorldClient extends World
{
	public boolean worldLoaded = false;
	
	public WorldClient()
	{
		super(new NetClient());
	}
	
	public void onUpdate(Timer t)
	{
		if(!canUpdate()) return;
		
		playerSP.onUpdate(t);
		renderer.onUpdate(t);
	}
	
	public boolean canUpdate()
	{ return worldLoaded && super.canUpdate(); }
	
	public void readWorld(DataIOStream dios) throws Exception // WorldServer
	{
		load_json = dios.readString();
		
		load_pixels = new int[dios.readInt()];
		for(int i = 0; i < load_pixels.length; i++)
			load_pixels[i] = dios.readInt();
		
		WorldLoader.loadWorld(this, load_json, load_pixels);
		
		playerMP.username = dios.readString();
		playerMP.color = Color.get(dios.readInt());
		
		playerMP.posX = dios.readDouble();
		playerMP.posY = dios.readDouble();
		endPointMP = dios.readInt();
		
		playerSP.posX = dios.readDouble();
		playerSP.posY = dios.readDouble();
		endPointSP = dios.readInt();
	}
	
	public void writeWorld(DataIOStream dios) throws Exception
	{
		dios.writeString(playerSP.username);
		dios.writeInt(playerSP.color.hex);
	}
}