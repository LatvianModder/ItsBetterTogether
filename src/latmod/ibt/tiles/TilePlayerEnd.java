package latmod.ibt.tiles;
import latmod.core.nbt.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.blocks.*;
import latmod.ibt.entity.*;
import latmod.ibt.world.*;

public class TilePlayerEnd extends TileEntity
{
	public boolean isPressed = false;
	
	public TilePlayerEnd(World w)
	{
		super(w);
	}
	
	public void onRender()
	{
		Renderer.setTexture(isPressed ? type.blockTexture : ((BlockPlayerEnd)type).textureOff);
		Renderer.rect(posX, posY, 1D, 1D);
	}
	
	public void loadTile(ExtraData data)
	{
	}
	
	public void readFromNBT(NBTMap map)
	{
		isPressed = map.getBoolean("Pressed");
	}
	
	public void writeToNBT(NBTMap map)
	{
		map.setBoolean("Pressed", isPressed);
	}
	
	public void onUpdate(Timer t)
	{
		EntityPlayer ep = ((BlockPlayerEnd)type).isSP() ? worldObj.playerSP : worldObj.playerMP;
		
		boolean b = worldObj.getIndex(ep.posX, ep.posY) == worldObj.getIndex(posX, posY);
		
		if(isPressed != b)
		{
			isPressed = b;
			isDirty = true;
		}
	}
}