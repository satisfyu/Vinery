package daniking.vinery.util;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;

import java.util.List;

public class VineryFoodComponent extends FoodComponent {
	public VineryFoodComponent(List<Pair<StatusEffectInstance, Float>> statusEffects) {
		super(1, 0, false, true, false, statusEffects);
	}
}