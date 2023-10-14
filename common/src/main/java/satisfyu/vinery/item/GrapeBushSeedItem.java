package satisfyu.vinery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.block.grape.GrapeType;

import java.util.List;

public class GrapeBushSeedItem extends ItemNameBlockItem {
    private final GrapeType type;

    public GrapeBushSeedItem(Block block, Properties settings, GrapeType type) {
        super(block, settings);
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("item.vinery.grapeseed.tooltip." + this.getDescriptionId()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }

    public GrapeType getType() {
        return this.type;
    }

}
