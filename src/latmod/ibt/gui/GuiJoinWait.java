package latmod.ibt.gui;
import latmod.core.gui.Widget;
import latmod.ibt.*;

public class GuiJoinWait extends GuiBasic
{
	public GuiJoinWait()
	{ super(Main.inst); }

	public void loadWidgets()
	{
		addCenterButton(0, "Cancel", height - 100D, 300D, 60D);
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0)
		{
			Main.inst.closeGame();
		}
	}
}