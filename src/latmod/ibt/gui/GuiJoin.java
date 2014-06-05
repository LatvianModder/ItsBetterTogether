package latmod.ibt.gui;
import latmod.core.gui.*;
import latmod.ibt.*;

public class GuiJoin extends GuiBasic
{
	public GuiJoin()
	{ super(Main.inst); }

	public void loadWidgets()
	{
		addCenterButton(0, "Back", height - 100, 300, 60);
		addCenterButton(1, "Join", height - 200, 300, 80);
		
		TextBox.setCentred(true, true);
		TextBox tb = new TextBox(this, width / 2D, height / 3D, 500, 70);
		tb.charLimit = 0;
		tb.label = "IP Address";
		addWidget(2, tb);
	}
	
	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0) Main.inst.openGui(null);
		else if(i == 1) Main.inst.joinGame(w.txt);
	}
}