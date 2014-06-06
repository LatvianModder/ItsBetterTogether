package latmod.ibt.gui;
import latmod.core.gui.*;
import latmod.ibt.*;

public class GuiHost extends GuiBasic
{
	public GuiHost()
	{
		super(Main.inst);
	}
	
	public void loadWidgets()
	{
		addCenterButton(0, "Back", height - 100D, 300D, 60D);
		
		addButton(1, "Start", width / 3D, height / 2D, 200D, 80D);
		addButton(2, "Settings", width / 3D * 2D, height / 2D, 200D, 80D);
		
		addCenterButton(3, "Addresses", 100D, 300D, 60D);
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0) Main.inst.openGui(null);
		else if(i == 1) Main.inst.openGui(new GuiHostWait(GameOptions.props.lastServerPort, GameOptions.props.hasRouter));
		else if(i == 2) Main.inst.openGui(new GuiHostSettings());
		else if(i == 3) Main.inst.openGui(new GuiHostAddresses());
	}
}