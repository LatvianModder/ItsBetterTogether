package latmod.ibt.net;
import java.net.*;
import latmod.core.util.*;
import latmod.ibt.Main;
import latmod.ibt.world.*;

public class NetServer implements Runnable, INet
{
	public ServerSocket serverSocket;
	public NetClient client;
	
	public EnumStatus opening = EnumStatus.NOT_STARTED;
	public EnumStatus waitingClient = EnumStatus.NOT_STARTED;
	
	public Thread thread;
	
	private int port = -1;
	private boolean hasRouter;
	
	public NetServer() { }
	
	public void open(InetAddress ip, int port)
	{
		opening = EnumStatus.WAITING;
		
		try
		{
			serverSocket = new ServerSocket(port, 1, ip);
			opening = EnumStatus.SUCCESS;
		}
		catch(Exception e)
		{
			opening = EnumStatus.FAILED;
		}
	}
	
	public void open(int p, boolean b)
	{
		port = p;
		hasRouter = b;
		thread = new Thread(this, "NetServer");
		thread.start();
	}
	
	public void run()
	{
		if(port != -1)
		{
			try
			{
				opening = EnumStatus.WAITING;
				open(hasRouter ? InetAddress.getLocalHost() : null, port);
				opening = EnumStatus.SUCCESS;
			}
			catch(Exception e)
			{
				opening = EnumStatus.FAILED;
			}
		}
		
		waitingClient = EnumStatus.WAITING;
		
		try
		{
			Socket s = serverSocket.accept();
			client = new NetClient();
			client.connect(s);
			waitingClient = EnumStatus.SUCCESS;
			
			while(client.connecting == EnumStatus.WAITING);
			
			World.inst.getServer().writeWorld(client.data);
			client.data.flush();
			
			while(client.data.available() <= 0);
			World.inst.getServer().readWorld(client.data);
			
			World.inst.getServer().worldSent = true;
			Main.inst.openGui(null);
		}
		catch(Exception e)
		{
			waitingClient = EnumStatus.FAILED;
			e.printStackTrace();
		}
		
		thread = null;
	}
	
	public void stop()
	{
		thread = null;
		
		if(client != null)
		client.stop();
		client = null;
		
		try { if(serverSocket != null) serverSocket.close(); }
		catch(Exception e) {} serverSocket = null;
	}
	
	public NetClient getClient()
	{ return client; }

	public EnumStatus getConnectionStatus()
	{ return waitingClient; }
}