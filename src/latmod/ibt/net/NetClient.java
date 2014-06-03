package latmod.ibt.net;

import java.net.*;
import latmod.core.util.*;

public class NetClient implements Runnable
{
	public Socket socket;
	public DataIOStream data;
	public Thread thread;
	
	public EnumStatus connecting = EnumStatus.NOT_STARTED;
	
	public NetClient()
	{
	}
	
	public void connect(String ip, int port)
	{
		try
		{
			Socket s = new Socket(ip, port);
			connect(s);
		}
		catch(Exception e) { }
	}
	
	public void connect(Socket s)
	{
		try
		{
			data = new DataIOStream(s.getInputStream(), s.getOutputStream());
			thread = new Thread(this, "NetClient");
		}
		catch(Exception e) { }
	}
	
	public void run()
	{
		try
		{
			while(Thread.currentThread() == thread)
			{
			}
		}
		catch(Exception e)
		{
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
}