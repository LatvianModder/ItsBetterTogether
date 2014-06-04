package latmod.ibt.tiles;
import latmod.core.nbt.*;
import latmod.ibt.world.*;

public class TileDoor extends TileEntity
{
	public String freq;
	public boolean horizontal;
	public int color;
	
	public TileDoor(World w)
	{
		super(w);
	}
	
	public void onRender()
	{
	}
	
	public void readFromNBT(NBTMap map)
	{
		super.readFromNBT(map);
		freq = map.getString("Freq");
		horizontal  = map.getBoolean("Hor");
		color = map.getInt("Col");
	}
	
	public void writeToNBT(NBTMap map)
	{
		super.writeToNBT(map);
		map.setString("Freq", freq);
		map.setBoolean("Hor", horizontal);
		map.setInt("Col", color);
	}
	
	public void onCustomData(NBTMap data)
	{
		if(data.hasKey("freq")) freq = data.getString("freq");
		if(data.hasKey("color")) color = WorldLoader.getCol(data.getString("color"));
		if(data.hasKey("horizontal")) horizontal = data.getBoolean("horizontal");
	}

	public boolean isOpen()
	{ return false; }
}