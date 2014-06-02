package latmod.ibt.tiles;
import java.util.Map;

import latmod.ibt.Main;
import latmod.ibt.world.World;

public class TileDoors extends TileEntity // BlockSign
{
	public TileDoors(World w)
	{
		super(w);
	}
	
	public void onRender()
	{
	}
	
	public void onCustomData(Map<String, Object> data)
	{
		Main.logger.info("" + data);
	}

	public boolean isOpen()
	{ return false; }
}