package latmod.ibt.gui;
import latmod.core.gui.*;
import latmod.core.rendering.*;
import latmod.core.util.*;
import latmod.ibt.*;

public class GuiHostAddresses extends GuiBasic
{
	public String localIP = null;
	public String externalIP = null;
	public FastList<String> addressText;
	
	public GuiHostAddresses()
	{
		super(Main.inst);
		
		localIP = LMCommon.getHostAddress();
		externalIP = LMCommon.getExternalAddress();
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
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0) Main.inst.openGui(new GuiHost());
	}
	
	public void onRender()
	{
		super.onRender();
		
		Color.reset();
		
		for(int i = 0; i < addressText.size(); i++)
		{
			String s = addressText.get(i);
			parent.font.drawText(parent.font.getCenterX(s, 2D), height / 3D + i * 40D, s, 2D);
		}
	}
}