package latmod.ibt.net;
import java.net.*;

public class NetServer
{
	public ServerSocket serverSocket;
	public NetClient client;
	
	public EnumStatus opening = EnumStatus.NOT_STARTED;
	public EnumStatus waitingClient = EnumStatus.NOT_STARTED;
	
	public NetServer()
	{
	}
	
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
	
	public void open(boolean hasRouter, int port)
	{
		try
		{
			waitingClient = EnumStatus.WAITING;
			open(hasRouter ? InetAddress.getLocalHost() : null, port);
		}
		catch(Exception e)
		{
			waitingClient = EnumStatus.FAILED;
		}
	}
	
	public void waitForClient()
	{
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
}