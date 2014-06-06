package latmod.ibt.gui;
import latmod.core.gui.*;
import latmod.ibt.*;

public class GuiHostSettings extends GuiBasic
{
	public GuiHostSettings()
	{ super(Main.inst); }
	
	public void loadWidgets()
	{
		addCenterButton(0, "Back", height - 100D, 300D, 60D);
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0) Main.inst.openGui(new GuiHost());
	}
}