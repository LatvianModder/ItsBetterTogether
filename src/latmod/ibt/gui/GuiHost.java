package latmod.ibt.gui;
import latmod.core.gui.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.*;

public class GuiHost extends GuiBasic
{
	public String localIP = null;
	public String externalIP = null;
	public FastList<String> addressText;
	
	public GuiHost()
	{
		super(Main.inst);
		
		localIP = LatCore.getHostAddress();
		externalIP = LatCore.getExternalAddress();
		addressText = new FastList<String>();
		
		if(externalIP != null)
		{
			addressText.add("Your addresses:");
			addressText.add(localIP);
			addressText.add(externalIP);
		}
		else
		{
			addressText.add("No Internet access");
			addressText.add("Your address:");
			addressText.add(localIP);
		}
	}
	
	public void loadWidgets()
	{
		addCenterButton(0, "Back", height - 100D, 300D, 60D);
		
		addButton(1, "Router", width / 3D, height / 2D, 200D, 80D);
		addButton(2, "No router", width / 3D * 2D, height / 2D, 200D, 80D);
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0) Main.inst.openGui(null);
		else if(i == 1) Main.inst.hostGame(true);
		else if(i == 2) Main.inst.hostGame(false);
	}
	
	public void onRender()
	{
		super.onRender();
		
		Color.WHITE.set();
		
		for(int i = 0; i < addressText.size(); i++)
		{
			String s = addressText.get(i);
			Font.inst.drawText(Font.inst.getCenterX(s, 2D), 32 + i * 40D, s, 2D);
		}
	}
}