package satisfyu.vinery.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FoodItemReturn extends Item {
    private static final double CHANCE_OF_GETTING_SEEDS = 0.2;
    private final ItemStack returnStack;

    public FoodItemReturn(Settings settings, ItemStack returnStack) {
        super(settings);
        this.returnStack = returnStack;
    }


    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.vinery.grapevine.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entityLiving) {
        if (entityLiving instanceof PlayerEntity playerEntity) {
            if (stack.getItem() == this) {

                if (Random.create().nextDouble() < CHANCE_OF_GETTING_SEEDS) {
                    playerEntity.giveItemStack(returnStack);
                }
            }
        }
        return super.finishUsing(stack, world, entityLiving);
    }
}