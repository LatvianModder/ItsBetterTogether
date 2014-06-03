package latmod.ibt;
import org.lwjgl.input.*;

public final class KeyBinding
{
	public final String name;
	public final int keyDefault;
	public int key;
	
	public KeyBinding(String s, int def)
	{ name = s; keyDefault = def; key = def; }
	
	public int hashCode()
	{ return name.hashCode(); }
	
	public boolean equals(Object o)
	{
		if(o == null || key == Keyboard.KEY_NONE) return false;
		if(o instanceof KeyBinding) 
		return name.equals(((KeyBinding)o).name);
		if(o instanceof Integer)
		return key == ((Integer)o).intValue();
		return false;
	}
	
	public boolean isPressed()
	{ return Keyboard.isKeyDown(key); }
	
	public void register()
	{
		String getS = GameOptions.getKey(name, Keyboard.getKeyName(keyDefault));
		key = Keyboard.getKeyIndex(getS);
		GameOptions.keyBindings.put(name, this);
	}
	
	public boolean isRegistred()
	{ return GameOptions.keyBindings.values.contains(this); }
}