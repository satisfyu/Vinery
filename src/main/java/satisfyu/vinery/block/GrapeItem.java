package satisfyu.vinery.block;


import net.minecraft.text.TranslatableText;
import satisfyu.vinery.util.GrapevineType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Namespace for grape items
 */
public class GrapeItem extends Item {

    private final GrapevineType type;
    public GrapeItem(Settings settings, GrapevineType type) {
        super(settings);
        this.type = type;
    }

    public GrapevineType getType() {
        return type;
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.vinery.grapevine.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

    }
}
