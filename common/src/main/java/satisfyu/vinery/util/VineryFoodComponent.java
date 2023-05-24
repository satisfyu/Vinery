package satisfyu.vinery.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.List;

public class VineryFoodComponent extends FoodProperties {
	public VineryFoodComponent(List<Pair<MobEffectInstance, Float>> statusEffects) {
		super(1, 0, false, true, false, statusEffects);
	}
}