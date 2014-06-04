package latmod.ibt.gui;
import latmod.core.gui.Widget;
import latmod.ibt.Main;

public class GuiPause extends GuiBasic
{
	public GuiPause()
	{ super(Main.inst); }

	public void loadWidgets()
	{
		addCenterButton(0, "Back", height - 100, 300, 60);
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0) Main.inst.openGui(null);
	}
}