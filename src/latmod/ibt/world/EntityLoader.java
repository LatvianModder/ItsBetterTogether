package latmod.ibt.world;
import java.util.*;
import com.google.gson.annotations.Expose;

public class EntityLoader
{
	@Expose public String id;
	@Expose public List<Double> coords;
	@Expose public Map<String, Object> data;
}