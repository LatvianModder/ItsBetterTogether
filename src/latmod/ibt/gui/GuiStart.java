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
		addCenterButton(1, "Host Game", height / 7D * 1D, 300, 60);
		addCenterButton(2, "Join Game", height / 7D * 2D, 300, 60);
		addCenterButton(3, "Options", height / 7D * 3D, 300, 60);
		addCenterButton(4, "Help", height / 7D * 4D, 300, 60);
		
		addCenterButton(0, "Exit", height / 7D * 6D, 300, 60);
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0) Main.inst.destroy();
		else if(i == 1) Main.inst.openGui(new GuiHost());
		else if(i == 2) Main.inst.openGui(new GuiJoin());
		else if(i == 3) Main.inst.openGui(null);
		else if(i == 4) Main.inst.openGui(null);
	}
}