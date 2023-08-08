package satisfyu.vinery.mixin;

import satisfyu.vinery.config.VineryConfig;
import satisfyu.vinery.item.WineMakerArmorItem;
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

	@Inject(method = "useOn", at = @At("RETURN"))
	public void useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		if (VineryConfig.DEFAULT.getConfig().enableWineMakerSetBonus() && cir.getReturnValue() == InteractionResult.CONSUME) {
			Player player = context.getPlayer();
			if (player != null) {
				ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
				ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
				ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
				ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
				if (helmet.getItem() instanceof WineMakerArmorItem &&
						chestplate.getItem() instanceof WineMakerArmorItem &&
						leggings.getItem() instanceof WineMakerArmorItem &&
						boots.getItem() instanceof WineMakerArmorItem) {
					helmet.hurtAndBreak(2, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.HEAD));
					chestplate.hurtAndBreak(2, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.CHEST));
					leggings.hurtAndBreak(2, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.LEGS));
					boots.hurtAndBreak(2, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.FEET));

					context.getItemInHand().grow(1);
				}
			}
		}
	}
}