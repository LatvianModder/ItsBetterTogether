package latmod.ibt.world;
import java.util.*;
import com.google.gson.annotations.Expose;

public class BlockLoader
{
	@Expose public String id;
	@Expose public int[] coords;
	@Expose public Map<String, Object> data;
}