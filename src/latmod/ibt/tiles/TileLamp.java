package latmod.ibt.tiles;
import latmod.core.nbt.*;
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
	
	public void onRender()
	{
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
		map.setByte("Light", lightValue);
		map.setString("Block", bgBlock.blockID);
	}
	
	public void loadTile(ExtraData data)
	{
		bgBlock = Block.addedBlocks.get(data.getS("block", "wall_stone"));
		lightValue = data.getN("lightValue", 15, 0, 15).intValue();
		
		worldObj.renderer.lightMapDirty = true;
	}
}