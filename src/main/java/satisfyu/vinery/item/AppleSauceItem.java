package satisfyu.vinery.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;

public class AppleSauceItem extends Item {

    public AppleSauceItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.vinery.applesauce_line1.tooltip"));
            tooltip.add(Text.translatable("item.vinery.applesauce_line2.tooltip"));
            tooltip.add(Text.translatable("item.vinery.applesauce_line3.tooltip"));

            tooltip.add(Text.translatable("item.vinery.cookingpot.tooltip"));
        } else {
            tooltip.add(Text.translatable("item.vinery.ingredient.tooltip").formatted(Formatting.GRAY, Formatting.ITALIC));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        return user instanceof PlayerEntity && ((PlayerEntity)user).getAbilities().creativeMode ? itemStack : new ItemStack(Items.BOWL);
    }

}