package latmod.ibt.entity;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.world.*;

public class EntityPlayer extends Entity
{
	public String username = null;
	public Color color = Color.WHITE;
	public double tarX, tarY;
	
	public EntityPlayer(World w)
	{
		super(w);
		radius = 0.25D;
	}
	
	public void onRender()
	{
		Renderer.disableTexture();
		Color.set(color, 100);
		Renderer.drawPoly(posX, posY, radius, 16D);
		Renderer.enableTexture();
		
		double scale = 1D / 64D;
		double d = Main.inst.font.textWidth(username, scale);
		Main.inst.font.drawText(posX - d / 2D + 0.11D, posY - 1.2D, username, scale);
	}
	
	public boolean isVisible()
	{ return true; }
	
	public void readEntity(DataIOStream dios) throws Exception
	{
		super.readEntity(dios);
		color = Color.get(dios.readInt(), 255);
	}
	
	public void writeEntity(DataIOStream dios) throws Exception
	{
		super.writeEntity(dios);
		dios.writeInt(color.hex);
	}
}