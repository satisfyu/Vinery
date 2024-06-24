package net.satisfy.vinery.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.List;

public class FoodComponent extends FoodProperties {
    public FoodComponent(List<Pair<MobEffectInstance, Float>> statusEffects) {
		super(1, 0, false, true, false, statusEffects);
	}
}