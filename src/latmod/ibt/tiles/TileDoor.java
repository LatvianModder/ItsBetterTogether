package latmod.ibt.tiles;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class TileDoor extends TileEntity
{
	public int freq;
	public boolean horizontal;
	public Color color;
	public int requredButtonCount;
	public Block texture;
	
	public boolean isOpen = false;
	
	public TileDoor(World w)
	{
		super(w);
	}
	
	public void onRender()
	{
		if(!isOpen)
		{
			texture.onRender(worldObj, posX, posY);
			color.set();
			Renderer.setTexture(type.blockTexture);
			Renderer.rect(posX, posY, 1D, 1D);
		}
	}
	
	public void loadTile(ExtraData data)
	{
		freq = data.getN("freq", 0).intValue();
		color = data.getC("color", Color.WHITE);
		requredButtonCount = data.getN("requredButtonCount", 1).intValue();
		texture = data.getBlock("block", Block.wall_stone);
		
		if(worldObj.isSolidBlock(posX - 1D, posY) || worldObj.isSolidBlock(posX + 1D, posY))
			horizontal = true;
	}
	
	public void readTile(DataIOStream dios) throws Exception
	{
		freq = dios.readByte();
		horizontal = dios.readBoolean();
		color = Color.get(dios.readInt());
		requredButtonCount = dios.readByte();
	}
	
	public void writeTile(DataIOStream dios) throws Exception
	{
		dios.writeByte(freq);
		dios.writeBoolean(horizontal);
		dios.writeInt(color.hex);
		dios.writeByte(requredButtonCount);
	}
	
	public void onUpdate(Timer t)
	{ isOpen = worldObj.powerNetwork[freq] >= requredButtonCount; }
}