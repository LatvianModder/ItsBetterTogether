package latmod.ibt.gui;
import latmod.core.gui.*;
import latmod.ibt.Main;

public abstract class GuiBasic extends Gui
{
	public float width, height;
	
	public GuiBasic(Main i)
	{
		super(i);
	}
	
	public void addButton(int id, String s, double x, double y, double w, double h)
	{ addWidget(id, new Button(this, x, y, w, h, s)); }

	public void onEscPressed()
	{
		Main.inst.openGui(null);
	}

	public void onReplacedBy(GuiBasic g)
	{
	}

	public boolean allowPlayerInput()
	{ return false; }
}