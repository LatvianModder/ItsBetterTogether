package latmod.ibt.gui;
import latmod.core.gui.Widget;
import latmod.ibt.Main;

public class GuiIngame extends GuiBasic
{
	public GuiIngame()
	{
		super(Main.inst);
	}

	public void loadWidgets()
	{
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
	}
	
	public void onRender()
	{
	}
	
	public boolean allowPlayerInput()
	{ return false; }
	
	public void onEscPressed()
	{
		Main.inst.openGui(new GuiPause());
	}
}