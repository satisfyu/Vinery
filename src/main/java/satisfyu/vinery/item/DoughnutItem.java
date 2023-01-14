package satisfyu.vinery.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DoughnutItem extends Item {

    public DoughnutItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslatableText("item.vinery.doughnut_line1.tooltip"));
            tooltip.add(new TranslatableText("item.vinery.doughnut_line2.tooltip"));
            tooltip.add(new TranslatableText("item.vinery.doughnut_line3.tooltip"));
            tooltip.add(new TranslatableText("item.vinery.oven.tooltip"));
        } else {
            tooltip.add(new TranslatableText("item.vinery.ingredient.tooltip").formatted(Formatting.GRAY, Formatting.ITALIC));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

}