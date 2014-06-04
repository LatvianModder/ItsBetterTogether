package latmod.ibt.tiles;
import latmod.core.nbt.*;
import latmod.core.util.MathHelper;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class TileLamp extends TileEntity
{
	public int lightValue;
	public Block bgBlock;
	
	public TileLamp(World w)
	{
		super(w);
		lightValue = 15;
		bgBlock = Block.wall_stone;
	}
	
	private void defaults()
	{
		if(bgBlock == null)
		bgBlock = Block.wall_stone;
		
		lightValue = MathHelper.limitInt(lightValue, 0, 15);
	}
	
	public void onRender()
	{
		defaults();
		bgBlock.onRender(worldObj, posX, posY);
		Block.lamp.onRender(worldObj, posX, posY);
	}
	
	public void readFromNBT(NBTMap map)
	{
		lightValue = map.getByte("Light");
		String s = map.getString("Block");
		bgBlock = Block.addedBlocks.get(s);
	}
	
	public void writeToNBT(NBTMap map)
	{
		defaults();
		map.setByte("Light", lightValue);
		map.setString("Block", bgBlock.blockID);
	}
	
	public void onCustomData(NBTMap data)
	{
		if(data.hasKey("block")) bgBlock = Block.addedBlocks.get(data.getString("block"));
		if(data.hasKey("lightValue")) lightValue = (int)data.getDouble("lightValue");
		
		defaults();
		
		worldObj.renderer.lightMapDirty = true;
	}
}