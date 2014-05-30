package latmod.ibt.world;
import latmod.core.util.*;

public class IDReg
{
	public final World worldObj;
	public final SimpleReg blocks, entities, tiles;
	
	public IDReg(World w)
	{
		worldObj = w;
		blocks = new SimpleReg();
		entities = new SimpleReg();
		tiles = new SimpleReg();
	}
	
	public void write(DataIOStream dios) throws Exception
	{
		blocks.write(dios);
		entities.write(dios);
		tiles.write(dios);
	}
	
	public void read(DataIOStream dios) throws Exception
	{
		blocks.read(dios);
		entities.read(dios);
		tiles.read(dios);
	}
}