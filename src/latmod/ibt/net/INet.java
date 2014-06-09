package latmod.ibt.net;

import latmod.core.util.EnumStatus;

public interface INet
{
	public NetClient getClient();
	public EnumStatus getConnectionStatus();
	public void stop();
}