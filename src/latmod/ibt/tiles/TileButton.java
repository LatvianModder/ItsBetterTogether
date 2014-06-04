package latmod.ibt.tiles;
import latmod.core.nbt.*;
import latmod.ibt.world.*;

public class TileButton extends TileEntity
{
	public String freq;
	public int color;
	
	public TileButton(World w)
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
		color = map.getInt("Col");
	}
	
	public void writeToNBT(NBTMap map)
	{
		super.writeToNBT(map);
		map.setString("Freq", freq);
		map.setInt("Col", color);
	}
	
	public void onCustomData(NBTMap data)
	{
		if(data.hasKey("freq")) freq = data.getString("freq");
		if(data.hasKey("color")) color = WorldLoader.getCol(data.getString("color"));
	}

	public boolean isPressed()
	{ return false; }
}