package satisfyu.vinery.mixin;

import net.minecraft.util.RandomSource;
import satisfyu.vinery.config.VineryConfig;
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
import satisfyu.vinery.item.*;
import satisfyu.vinery.util.GeneralUtil;

@Mixin(BoneMealItem.class)
public abstract class BoneMealItemMixin {
	
	@Inject(method = "useOn", at = @At("RETURN"))
	public void useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		VineryConfig config = VineryConfig.DEFAULT.getConfig();
		RandomSource random = context.getLevel().getRandom();

		if (config.enableWineMakerSetBonus() && random.nextFloat() < GeneralUtil.getInPercent(config.probabilityToKeepBoneMeal()) && cir.getReturnValue() == InteractionResult.CONSUME) {
			Player player = context.getPlayer();
			if (player != null) {
				ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
				ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
				ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
				ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
				if (helmet.getItem() instanceof WinemakerHatItem &&
						chestplate.getItem() instanceof WinemakerChest &&
						leggings.getItem() instanceof WinemakerLegs &&
						boots.getItem() instanceof WinemakerBoots) {

					if(random.nextFloat() < GeneralUtil.getInPercent(config.probabilityForDamage())){
						int damage = config.damagePerUse();
						helmet.hurtAndBreak(damage, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.HEAD));
						chestplate.hurtAndBreak(damage, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.CHEST));
						leggings.hurtAndBreak(damage, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.LEGS));
						boots.hurtAndBreak(damage, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.FEET));
					}

					context.getItemInHand().grow(1);
				}
			}
		}
	}
}