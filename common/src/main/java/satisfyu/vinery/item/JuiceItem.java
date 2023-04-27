package satisfyu.vinery.item;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import satisfyu.vinery.registry.ObjectRegistry;

public class JuiceItem extends ConsumAndReturnItem {
    public JuiceItem(Properties settings) {
        super(settings, 40, ObjectRegistry.WINE_BOTTLE, true);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("item.vinery.juice.tooltip." + this.getDescriptionId()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}
