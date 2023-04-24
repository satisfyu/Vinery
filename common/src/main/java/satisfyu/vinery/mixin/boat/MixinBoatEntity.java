package satisfyu.vinery.mixin.boat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.ItemLike;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.impl.entity.MyHolder;

@Mixin(Boat.class)
public class MixinBoatEntity {
	@ModifyArg(method = "checkFallDamage", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/Boat;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;", ordinal = 0))
	private ItemLike replaceTerraformPlanksDropItem(ItemLike original) {
		if (this instanceof MyHolder) {
			TerraformBoatType boat = ((MyHolder) this).getTerraformBoat();
			return boat.getPlanks();
		}
		return original;
	}
}
