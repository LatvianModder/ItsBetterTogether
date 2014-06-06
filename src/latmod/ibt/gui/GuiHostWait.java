package latmod.ibt.gui;
import latmod.core.gui.*;
import latmod.core.rendering.*;
import latmod.ibt.*;
import latmod.ibt.world.*;

public class GuiHostWait extends GuiBasic
{
	public GuiHostWait(int i, boolean b)
	{
		super(Main.inst);
		
		World.inst = new WorldServer();
		World.inst.getNetServer().open(i, b);
	}
	
	public void onRender()
	{
		super.onRender();
		
		String s = "Loading...";
		
		Font.inst.drawText(Font.inst.getCenterX(s, 2D), height / 3D, s, 2D);
	}

	public void loadWidgets()
	{
		addCenterButton(0, "Cancel", height - 100D, 300D, 60D);
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0)
		{
			Main.inst.closeGame();
			Main.inst.openGui(new GuiHost());
		}
	}
}