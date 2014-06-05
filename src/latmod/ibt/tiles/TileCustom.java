package latmod.ibt.tiles;
import latmod.core.rendering.*;
import latmod.core.util.*;
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
		Renderer.setTexture(texture.getTexture(worldObj, posX, posY));
		Renderer.rect(posX, posY, 1D, 1D);
	}
	
	public void readTile(DataIOStream dios) throws Exception
	{
		lightValue = dios.readByte();
		texture = worldObj.registry.getBlock(dios.readShort());
		color = Color.get(dios.readInt());
		rotation = dios.readByte();
		isGhost = dios.readBoolean();
	}
	
	public void writeTile(DataIOStream dios) throws Exception
	{
		dios.writeByte(lightValue);
		dios.writeShort(texture.getID(worldObj));
		dios.writeInt(color.hex);
		dios.writeByte(rotation);
		dios.writeBoolean(isGhost);
	}
}