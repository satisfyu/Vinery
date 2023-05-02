package satisfyu.vinery.util.boat.api;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class TerraformBoatTypeRegistry {
	private static Map<ResourceLocation, TerraformBoatType> INSTANCE = new HashMap<>();

	public static void register(ResourceLocation location, TerraformBoatType type){
		INSTANCE.put(location, type);
	}

	public static TerraformBoatType get(ResourceLocation location){
		return INSTANCE.get(location);
	}

	public static Set<Map.Entry<ResourceLocation, TerraformBoatType>> entrySet(){
		return INSTANCE.entrySet();
	}

	public static ResourceLocation getId(TerraformBoatType type){
		for(ResourceLocation location : INSTANCE.keySet()){
			if(get(location).equals(type)) return location;
		}
		throw new NullPointerException("Couldn't find BoatType");
	}
}
