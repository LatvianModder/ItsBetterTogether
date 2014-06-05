package latmod.ibt.tiles;
import latmod.core.util.*;
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
	
	public void loadTile(ExtraData data)
	{
		bgBlock = Block.blockNameMap.get(data.getS("block", "wall_stone"));
		lightValue = data.getN("lightValue", 15, 0, 15).intValue();
		
		worldObj.renderer.lightMapDirty = true;
	}
	
	public void readTile(DataIOStream dios) throws Exception
	{
		lightValue = dios.readByte();
		bgBlock = Block.blockMap.get(dios.readShort());
	}
	
	public void writeTile(DataIOStream dios) throws Exception
	{
		dios.writeByte(lightValue);
		dios.writeShort(bgBlock.blockID);
	}
}