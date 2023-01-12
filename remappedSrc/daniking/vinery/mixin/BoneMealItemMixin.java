package daniking.vinery.mixin;

import daniking.vinery.item.WineMakerArmorItem;
import daniking.vinery.item.WinemakerDefaultArmorItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public abstract class BoneMealItemMixin {
	
	@Inject(method = "useOnBlock", at = @At("RETURN"))
	public void useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		if (cir.getReturnValue() == InteractionResult.CONSUME) {
			Player player = context.getPlayer();
			ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
			ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
			ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
			ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
			if (helmet != null && helmet.getItem() instanceof WineMakerArmorItem &&
					chestplate != null && chestplate.getItem() instanceof WineMakerArmorItem &&
					leggings != null && leggings.getItem() instanceof WineMakerArmorItem &&
					boots != null && boots.getItem() instanceof WineMakerArmorItem) {
				context.getItemInHand().grow(1);
			}
		}
	}
}