package daniking.vinery.mixin;

import daniking.vinery.item.WineMakerArmorItem;
import daniking.vinery.item.WinemakerDefaultArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public abstract class BoneMealItemMixin {
	
	@Inject(method = "useOnBlock", at = @At("RETURN"))
	public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		if (cir.getReturnValue() == ActionResult.CONSUME) {
			PlayerEntity player = context.getPlayer();
			ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
			ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
			ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
			ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
			if (helmet != null && helmet.getItem() instanceof WineMakerArmorItem &&
					chestplate != null && chestplate.getItem() instanceof WineMakerArmorItem &&
					leggings != null && leggings.getItem() instanceof WineMakerArmorItem &&
					boots != null && boots.getItem() instanceof WineMakerArmorItem) {
				context.getStack().increment(1);
			}
		}
	}
}