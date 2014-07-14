package latmod.ibt.gui;
import org.lwjgl.input.Keyboard;

import latmod.core.gui.*;
import latmod.core.input.*;
import latmod.core.rendering.Font;
import latmod.core.util.LMCommon;
import latmod.ibt.Main;

public class GuiChat extends GuiBasic implements IKeyListener.Pressed
{
	public String text = "";
	
	public GuiChat()
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
		Font.inst.drawText(4, Main.inst.height - 24, text);
	}

	public void onKeyPressed(EventKey.Pressed e)
	{
		if(e.key == Keyboard.KEY_RETURN)
		{
		}
		else if(e.key == Keyboard.KEY_BACK)
		{
		}
		else if(e.key == Keyboard.KEY_V && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
		{
		}
		else
		{
			if(LMCommon.isASCIIChar(e.keyChar))
			{
				text += e.keyChar;
			}
		}
	}
}