package latmod.ibt.gui;
import latmod.core.gui.*;
import latmod.core.rendering.Color;
import latmod.core.rendering.Font;
import latmod.core.util.FastList;
import latmod.core.util.LatCore;
import latmod.ibt.*;

public class GuiHostAddresses extends GuiBasic
{
	public String localIP = null;
	public String externalIP = null;
	public FastList<String> addressText;
	
	public GuiHostAddresses()
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
	}

	public void onWidgetEvent(int i, Widget w, String event, Object... args)
	{
		if(i == 0) Main.inst.openGui(new GuiHost());
	}
	
	public void onRender()
	{
		super.onRender();
		
		Color.WHITE.set();
		
		for(int i = 0; i < addressText.size(); i++)
		{
			String s = addressText.get(i);
			Font.inst.drawText(Font.inst.getCenterX(s, 2D), height / 3D + i * 40D, s, 2D);
		}
	}
}