package latmod.ibt.net;
import java.net.*;

import latmod.core.util.EnumStatus;

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
		waitingClient = EnumStatus.WAITING;
		
		try
		{
			serverSocket = new ServerSocket(port, 1, ip);
			waitingClient = EnumStatus.SUCCESS;
		}
		catch(Exception e)
		{
			waitingClient = EnumStatus.FAILED;
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
		try
		{
			if(port != -1)
			{
				try
				{
					waitingClient = EnumStatus.WAITING;
					open(hasRouter ? InetAddress.getLocalHost() : null, port);
					waitingClient = EnumStatus.SUCCESS;
				}
				catch(Exception e)
				{
					waitingClient = EnumStatus.FAILED;
				}
			}
			
			waitingClient = EnumStatus.WAITING;
			
			try
			{
				Socket s = serverSocket.accept();
				client = new NetClient();
				client.connect(s);
				
				waitingClient = EnumStatus.SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				waitingClient = EnumStatus.FAILED;
			}
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		
		thread = null;
	}
	
	public void stop()
	{
		thread = null;
		
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