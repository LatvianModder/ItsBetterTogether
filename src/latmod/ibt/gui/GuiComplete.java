package latmod.ibt.gui;
import latmod.core.gui.Widget;
import latmod.ibt.*;

public class GuiComplete extends GuiBasic
{
	public GuiComplete()
	{
		super(Main.inst);
	}
	
	public void onRender()
	{
		String s = "You won!";
		parent.font.drawText(parent.font.getCenterX(s, 2D), 64D, s, 2D);
		
		super.onRender();
	}

	public void loadWidgets()
	{
		addCenterButton(0, "Exit", height - 100, 300, 60);
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0) Main.inst.closeGame();
	}
}