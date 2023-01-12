package daniking.vinery.util;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class VineryFoodComponent extends FoodProperties {
	public VineryFoodComponent(List<Pair<MobEffectInstance, Float>> statusEffects) {
		super(1, 0, false, true, false, statusEffects);
	}
}