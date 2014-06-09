package latmod.ibt.gui;
import latmod.core.gui.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.*;
import latmod.ibt.net.*;
import latmod.ibt.world.*;

public class GuiJoinWait extends GuiBasic
{
	public GuiJoinWait(String s, int p)
	{
		super(Main.inst);
		World.inst = new WorldClient();
		World.inst.getNetClient().connect(s, p);
		
		Main.logger.info("Connecting to " + s + ":" + p);
	}

	public void loadWidgets()
	{
		addCenterButton(0, "Cancel", height - 100D, 300D, 60D);
	}
	
	public void onRender()
	{
		super.onRender();
		
		NetClient client = World.inst.getNetClient();
		
		String s = "Loading...";
		
		if(client.connecting == EnumStatus.WAITING) s = "Connecting";
		else if(client.connecting == EnumStatus.SUCCESS) s = "Loading world";
		else if(client.connecting == EnumStatus.FAILED) s = "Connection failed";
		
		Font.inst.drawText(Font.inst.getCenterX(s, 2D), height / 3D, s, 2D);
	}
	
	public void onEscPressed() { }

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0)
		{
			if(World.inst.canUpdate()) Main.inst.openGui(null);
			else
			{
				Main.inst.closeGame();
				Main.inst.openGui(new GuiJoin());
			}
		}
	}
}