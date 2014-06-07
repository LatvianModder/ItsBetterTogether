package latmod.ibt.entity;
import latmod.core.rendering.Color;
import latmod.core.util.Timer;
import latmod.ibt.world.*;

public class EntityPlayerMP extends EntityPlayer
{
	public EntityPlayerMP(World w)
	{
		super(w);
		color = Color.RED;
		username = "Testificate";
	}
	
	public void onUpdate(Timer t)
	{
	}
}