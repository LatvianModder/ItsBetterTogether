package latmod.ibt.gui;
import latmod.core.gui.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.*;
import latmod.ibt.net.*;
import latmod.ibt.world.*;

public class GuiHostWait extends GuiBasic
{
	public GuiHostWait(int i, boolean b)
	{
		super(Main.inst);
		
		World.inst = new WorldServer();
		WorldLoader.loadWorldFromLocalStream(World.inst, "/levels/level1");
		World.inst.getNetServer().open(i, b);
		
		Main.logger.info("Hosted server @ port " + i + (b ? " with" : " without") + " router");
	}
	
	public void onRender()
	{
		super.onRender();
		
		NetServer server = World.inst.getNetServer();
		
		String s = "Loading...";
		
		if(server.opening == EnumStatus.WAITING) s = "Opening server";
		else if(server.opening == EnumStatus.SUCCESS) s = "Server opened";
		else if(server.opening == EnumStatus.FAILED) s = "Server failed";
		
		if(server.waitingClient == EnumStatus.WAITING) s = "Waiting for client";
		if(server.waitingClient == EnumStatus.SUCCESS) s = "Sending world";
		if(server.waitingClient == EnumStatus.FAILED) s = "Sending world failed";
		
		Font.inst.drawText(Font.inst.getCenterX(s, 2D), height / 3D, s, 2D);
	}

	public void loadWidgets()
	{
		addCenterButton(0, "Cancel", height - 100D, 300D, 60D);
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
				Main.inst.openGui(new GuiHost());
			}
		}
	}
}