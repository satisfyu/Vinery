package net.satisfy.vinery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.satisfy.vinery.util.VineryIdentifier;

import java.util.List;

public class StandardItem extends de.cristelknight.doapi.common.item.StandardItem {
    public StandardItem(Properties properties) {
        super(properties, VineryIdentifier.of("textures/standard/vinery_standard.png"), () -> new MobEffectInstance(MobEffects.REGENERATION, 200, 1, true, false, true));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("tooltip.vinery.thankyou_1").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.vinery.thankyou_2").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.vinery.thankyou_4").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.vinery.thankyou_3").withStyle(ChatFormatting.GOLD));
    }
}
