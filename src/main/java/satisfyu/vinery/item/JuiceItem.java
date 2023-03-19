package satisfyu.vinery.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JuiceItem extends HoneyBottleItem {
    public JuiceItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.vinery.juice.tooltip." + this.getTranslationKey()).formatted(Formatting.ITALIC, Formatting.GRAY));
    }
}
