package latmod.ibt.tiles;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.blocks.*;
import latmod.ibt.world.*;

public class TileButton extends TileEntity implements IPowerProvider
{
	public int freq;
	public Color color;
	
	public CollisionBox collisionBox = null;
	public boolean isPressed = false;
	
	public TileButton(World w)
	{
		super(w);
	}
	
	public void onUpdate(Timer t)
	{
		boolean b = isPressed;
		isPressed = collisionBox.isColliding(worldObj.playerSP.collisionBox, 0D, 0D) || collisionBox.isColliding(worldObj.playerMP.collisionBox, 0D, 0D);
		if(b != isPressed) isDirty = true;
	}
	
	public void onRender()
	{
		color.set();
		Main.inst.textureManager.setTexture(isPressed ? ((BlockButton)type).textureOn : type.blockTexture);
		Renderer.rect(posX, posY, 1D, 1D);
	}
	
	public void loadTile(ExtraData data)
	{
		freq = data.getN("freq", 0).intValue();
		color = data.getC("color", Color.WHITE);
		
		collisionBox = new CollisionBox(posX, posY, posX + 1D, posY + 1D);
	}
	
	public void readTile(DataIOStream dios) throws Exception
	{
		freq = dios.readByte();
		color = Color.get(dios.readInt());
		isPressed = dios.readBoolean();
	}
	
	public void writeTile(DataIOStream dios) throws Exception
	{
		dios.writeByte(freq);
		dios.writeInt(color.hex);
		dios.writeBoolean(isPressed);
	}

	public int getFreq()
	{ return freq; }

	public boolean isProvidingPower()
	{ return isPressed; }
}