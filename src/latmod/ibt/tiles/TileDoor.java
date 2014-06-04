package latmod.ibt.tiles;
import latmod.core.nbt.*;
import latmod.core.rendering.Color;
import latmod.ibt.world.*;

public class TileDoor extends TileEntity
{
	public String freq;
	public int rotation;
	public Color color;
	public int requredButtonCount;
	
	public TileDoor(World w)
	{
		super(w);
	}
	
	public void loadTile(ExtraData data)
	{
		freq = data.getS("freq", "def");
		color = data.getC("color", Color.WHITE);
		rotation = data.getN("rotation", 0).intValue();
		requredButtonCount = data.getN("requredButtonCount", 1).intValue();
	}
	
	public void readFromNBT(NBTMap map)
	{
		freq = map.getString("Freq");
		rotation = map.getByte("Rot");
		color = Color.get(map.getInt("Col"));
		requredButtonCount = map.getByte("BCount");
	}
	
	public void writeToNBT(NBTMap map)
	{
		map.setString("Freq", freq);
		map.setByte("Rot", rotation);
		map.setInt("Col", color.hex);
		map.setByte("BCount", requredButtonCount);
	}
	
	public void onRender()
	{
	}
	
	public boolean isOpen()
	{ return true; }
}