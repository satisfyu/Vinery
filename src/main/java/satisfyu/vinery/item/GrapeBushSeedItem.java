package satisfyu.vinery.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.util.GrapevineType;
import net.minecraft.block.Block;
import net.minecraft.item.AliasedBlockItem;

import java.util.List;

public class GrapeBushSeedItem extends AliasedBlockItem {

    private final GrapevineType type;

    public GrapeBushSeedItem(Block block, Settings settings, GrapevineType type) {
        super(block, settings);
        this.type = type;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.vinery.grapeseed.tooltip." + this.getTranslationKey()).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    public GrapevineType getType() {
        return type;
    }
}
