package latmod.ibt.gui;
import latmod.core.gui.Widget;
import latmod.ibt.Main;

public class GuiStart extends GuiBasic
{
	public GuiStart()
	{
		super(Main.inst);
	}

	public void loadWidgets()
	{
		addButton(1, "Added", 4, 4, 200, 40);
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
	}
}