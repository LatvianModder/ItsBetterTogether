package latmod.ibt.tiles;
import latmod.core.nbt.*;
import latmod.core.rendering.Color;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class TileCustom extends TileEntity
{
	public Block texture;
	public int lightValue;
	public Color color;
	public int rotation;
	public boolean isGhost;
	
	public TileCustom(World w)
	{
		super(w);
		lightValue = 15;
		texture = Block.wall_stone;
	}
	
	public void loadTile(ExtraData data)
	{
		texture = data.getBlock("block", Block.wall_stone);
		lightValue = data.getN("lightValue", 15, 0, 15).intValue();
		color = data.getC("color", Color.WHITE);
		rotation = data.getN("rotation", 0).intValue();
		isGhost = data.getB("ghost", false);
		
		worldObj.renderer.lightMapDirty = true;
	}
	
	public void onRender()
	{
		color.set();
		
		texture.onRender(worldObj, posX, posY);
		Block.lamp.onRender(worldObj, posX, posY);
	}
	
	public void readFromNBT(NBTMap map)
	{
		lightValue = map.getByte("Light");
		String s = map.getString("Block");
		texture = Block.addedBlocks.get(s);
		color = Color.get(map.getInt("Col"));
		rotation = map.getByte("Rot");
		isGhost = map.getBoolean("Ghost");
	}
	
	public void writeToNBT(NBTMap map)
	{
		map.setByte("Light", lightValue);
		map.setString("Block", texture.blockID);
		map.setInt("Col", color.hex);
		map.setByte("Rot", rotation);
		map.setBoolean("Ghost", isGhost);
	}
}