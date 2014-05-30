package latmod.ibt.entity;
import latmod.core.rendering.*;
import latmod.ibt.world.*;

public class EntityPlayer extends Entity
{
	public String username;
	
	public EntityPlayer(World w)
	{
		super(w);
		username = "LatvianModder";
	}
	
	public void onRender()
	{
		Renderer.disableTexture();
		Color.WHITE.set(100);
		Renderer.drawPoly(posX, posY, 0.5D, 16D);
		Renderer.enableTexture();
		
		double scale = 1D / 64D;
		double d = Font.inst.textWidth(username, scale);
		Font.inst.drawText(posX - d / 2D + 0.11D, posY - 1.2D, username, scale);
	}
	
	public boolean isVisible()
	{ return true; }
}