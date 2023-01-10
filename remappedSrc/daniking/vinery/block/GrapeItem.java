package daniking.vinery.block;


import daniking.vinery.util.GrapevineType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

/**
 * Namespace for grape items
 */
public class GrapeItem extends Item {

    private final GrapevineType type;
    public GrapeItem(Properties settings, GrapevineType type) {
        super(settings);
        this.type = type;
    }

    public GrapevineType getType() {
        return type;
    }

    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("item.vinery.grapevine.tooltip").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));

    }
}
