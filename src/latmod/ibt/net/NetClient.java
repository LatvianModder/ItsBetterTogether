package latmod.ibt.net;

import java.net.*;

import latmod.core.util.*;
import latmod.ibt.GameOptions;

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
		thread = new Thread(this, "NetClient");
		thread.start();
		connecting = EnumStatus.WAITING;
	}
	
	public void connect(Socket s)
	{
		socket = s;
		connecting = EnumStatus.WAITING;
	}
	
	public void run()
	{
		try
		{
			connecting = EnumStatus.WAITING;
			
			if(socket == null)
			socket = new Socket(connectIP, connectPort);
			
			data = new DataIOStream(socket.getInputStream(), socket.getOutputStream());
			thread = new Thread(this, "NetClient");
			
			connecting = EnumStatus.SUCCESS;
			
			while(Thread.currentThread() == thread)
			{
			}
		}
		catch(Exception e)
		{
			connecting = EnumStatus.FAILED;
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