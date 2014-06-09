package latmod.ibt.net;
import java.net.*;

import latmod.core.util.*;
import latmod.ibt.*;
import latmod.ibt.world.*;

public class NetClient implements Runnable, INet
{
	public Socket socket;
	public DataIOStream data;
	public Thread thread;
	
	private String connectIP = null;
	private int connectPort = -1;
	
	public EnumStatus connecting = EnumStatus.NOT_STARTED;
	
	public NetClient()
	{
	}
	
	public void connect(String ip, int port)
	{
		connectIP = ip;
		connectPort = port;
		connecting = EnumStatus.WAITING;
		thread = new Thread(this, "NetClient");
		thread.start();
	}
	
	public void connect(Socket s) throws Exception
	{
		connecting = EnumStatus.WAITING;
		socket = s;
		data = new DataIOStream(socket.getInputStream(), socket.getOutputStream());
		thread = new Thread(this, "NetServerClient");
		thread.start();
		connecting = EnumStatus.SUCCESS;
	}
	
	public void run()
	{
		try
		{
			if(socket == null)
			{
				connecting = EnumStatus.WAITING;
				socket = new Socket(connectIP, connectPort);
				data = new DataIOStream(socket.getInputStream(), socket.getOutputStream());
				connecting = EnumStatus.SUCCESS;
				
				while(data.available() <= 0);
				
				World.inst.getClient().readWorld(data);
				World.inst.getClient().writeWorld(data);
				data.flush();
				
				World.inst.getClient().worldLoaded = true;
				Main.inst.openGui(null);
			}
			
			Thread.sleep(10);
			
			while(thread != null)
			{
				World.inst.packetHandler.update(this);
				Thread.sleep(4);
			}
		}
		catch(Exception e)
		{
			connecting = EnumStatus.FAILED;
			
			e.printStackTrace();
		}
		
		stop();
	}
	
	public void stop()
	{
		thread = null;
		
		try { data.flush(); data.close(); }
		catch(Exception e) { }
		data = null;
		
		try { socket.close(); }
		catch(Exception e) { }
		socket = null;
	}

	public NetClient getClient()
	{ return this; }

	public EnumStatus getConnectionStatus()
	{ return connecting; }

	public static TwoObjects<String, Integer> toIpAddress(String txt)
	{
		String ip = txt;
		int port = GameOptions.DEF_PORT;
		
		if(ip.contains(":"))
		{
			String[] s = ip.split(":");
			ip = s[0];
			
			try { port = Integer.parseInt(s[1]); }
			catch(Exception e) { port = GameOptions.DEF_PORT; }
		}
		
		return new TwoObjects<String, Integer>(ip, port);
	}
}