package net.satisfy.vinery.mixin;

import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.satisfy.vinery.config.VineryConfig;
import net.satisfy.vinery.item.WinemakerBootsItem;
import net.satisfy.vinery.item.WinemakerChestItem;
import net.satisfy.vinery.item.WinemakerHatItem;
import net.satisfy.vinery.item.WinemakerLegsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
						chestplate.getItem() instanceof WinemakerChestItem &&
						leggings.getItem() instanceof WinemakerLegsItem &&
						boots.getItem() instanceof WinemakerBootsItem) {

					if(random.nextFloat() < GeneralUtil.getInPercent(config.probabilityForDamage())){
						int damage = config.damagePerUse();
						helmet.hurtAndBreak(damage, player, EquipmentSlot.HEAD);
						chestplate.hurtAndBreak(damage, player, EquipmentSlot.CHEST);
						leggings.hurtAndBreak(damage, player, EquipmentSlot.LEGS);
						boots.hurtAndBreak(damage, player, EquipmentSlot.FEET);
					}

					context.getItemInHand().grow(1);
				}
			}
		}
	}
}