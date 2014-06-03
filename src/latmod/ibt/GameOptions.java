package latmod.ibt;
import java.io.*;
import java.util.*;
import org.lwjgl.input.*;
import com.google.gson.annotations.Expose;
import latmod.core.util.*;

public class GameOptions
{
	public static final int DEF_PORT = 14142;
	
	public static Props props;
	@Expose public static Map<String, String> keys;
	
	// Keys //
	public static KeyBinding KEY_SCREENSHOT = new KeyBinding("screenshot", Keyboard.KEY_F2);
	public static KeyBinding KEY_FULLSCREEN = new KeyBinding("fullscreen", Keyboard.KEY_F11);
	
	public static KeyBinding KEY_CHAT = new KeyBinding("player.chat", Keyboard.KEY_SLASH);
	public static KeyBinding KEY_HIDE_GUI = new KeyBinding("player.hideGui", Keyboard.KEY_F1);
	public static KeyBinding KEY_DEBUG = new KeyBinding("player.debug", Keyboard.KEY_F3);
	public static KeyBinding KEY_TEST = new KeyBinding("player.test", Keyboard.KEY_B);
	
	public static KeyBinding KEY_MOVE_UP = new KeyBinding("player.move.up", Keyboard.KEY_W);
	public static KeyBinding KEY_MOVE_DOWN = new KeyBinding("player.move.down", Keyboard.KEY_S);
	public static KeyBinding KEY_MOVE_LEFT = new KeyBinding("player.move.left", Keyboard.KEY_A);
	public static KeyBinding KEY_MOVE_RIGHT = new KeyBinding("player.move.right", Keyboard.KEY_D);
	public static KeyBinding KEY_MOVE_SPRINT = new KeyBinding("player.move.sprint", Keyboard.KEY_LSHIFT);
	public static KeyBinding KEY_MOVE_SNEAK = new KeyBinding("player.move.sneak", Keyboard.KEY_LCONTROL);
	
	public static FastMap<String, KeyBinding> keyBindings = new FastMap<String, KeyBinding>();
	
	public static final void loadOptions()
	{
		try
		{
			props = LatCore.getJson(LatCore.toString(new FileInputStream(LatCore.newFile("", "options.json"))), Props.class);
			props.setDefaults();
			
			keys = LatCore.getJson(LatCore.toString(new FileInputStream(LatCore.newFile("", "keys.json"))), HashMap.class);
			if(keys == null) keys = new HashMap<String, String>();
			
			keyBindings.clear();
			
			KEY_SCREENSHOT.register();
			KEY_FULLSCREEN.register();
			
			KEY_CHAT.register();
			KEY_HIDE_GUI.register();
			KEY_DEBUG.register();
			KEY_TEST.register();
			
			KEY_MOVE_UP.register();
			KEY_MOVE_DOWN.register();
			KEY_MOVE_LEFT.register();
			KEY_MOVE_RIGHT.register();
			KEY_MOVE_SPRINT.register();
			KEY_MOVE_SNEAK.register();
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		
		saveProps();
		saveKeys();
	}
	
	public static String getKey(String name, String def)
	{
		String s = keys.get(name);
		
		if(s == null)
		{
			keys.put(name, def);
			return def;
		}
		
		return s;
	}

	public static KeyBinding keyBindingFromKey(int key)
	{
		for(KeyBinding k : keyBindings)
		{ if(k.key == key) return k; }
		return null;
	}
	
	public static void saveProps()
	{
		if(props == null) return;
		String s = LatCore.toJson(props, true);
		LatCore.saveFile(LatCore.newFile("", "options.json"), s, false);
	}
	
	public static void saveKeys()
	{
		if(keys == null) return;
		String s = LatCore.toJson(keys, true);
		LatCore.saveFile(LatCore.newFile("", "keys.json"), s, false);
	}
}