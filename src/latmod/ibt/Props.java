package latmod.ibt;
import com.google.gson.annotations.Expose;

public class Props
{
	@Expose public String username;
	@Expose public Integer logCount;
	@Expose public Double soundVolume;
	@Expose public Boolean smoothLighting;
	
	public void setDefaults()
	{
		if(username == null) username = "Player";
		if(logCount == null) logCount = 3;
		if(soundVolume == null) soundVolume = 1D;
		if(smoothLighting == null) smoothLighting = false;
	}
}