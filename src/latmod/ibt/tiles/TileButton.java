package latmod.ibt.tiles;
import latmod.core.nbt.*;
import latmod.core.rendering.Color;
import latmod.ibt.world.*;

public class TileButton extends TileEntity
{
	public String freq;
	public Color color;
	
	public TileButton(World w)
	{
		super(w);
	}
	
	public void onRender()
	{
	}
	
	public void readFromNBT(NBTMap map)
	{
		freq = map.getString("Freq");
		color = Color.get(map.getInt("Col"));
	}
	
	public void writeToNBT(NBTMap map)
	{
		map.setString("Freq", freq);
		map.setInt("Col", color.hex);
	}
	
	public void loadTile(ExtraData data)
	{
		freq = data.getS("freq", "def");
		color = data.getC("col", Color.WHITE);
	}

	public boolean isPressed()
	{ return false; }
}