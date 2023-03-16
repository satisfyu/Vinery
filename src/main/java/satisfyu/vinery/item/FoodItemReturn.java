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
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.util.GrapevineType;

import java.util.List;

public class FoodItemReturn extends Item {
    private static final double CHANCE_OF_GETTING_SEEDS = 0.2;

    private final GrapevineType type;
    public FoodItemReturn(Settings settings, GrapevineType type) {
        super(settings);
        this.type = type;
    }

    public GrapevineType getType() {
        return type;
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.vinery.grapevine.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));

    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entityLiving) {
        if (entityLiving instanceof PlayerEntity playerEntity) {
            if (stack.getItem() == ObjectRegistry.RED_GRAPE || stack.getItem() == ObjectRegistry.WHITE_GRAPE) {

                if (Random.create().nextDouble() < CHANCE_OF_GETTING_SEEDS) {
                    ItemStack seeds = ItemStack.EMPTY;
                    if (stack.getItem() == ObjectRegistry.RED_GRAPE) {
                        seeds = new ItemStack(ObjectRegistry.RED_GRAPE_SEEDS);
                    }
                    else if (stack.getItem() == ObjectRegistry.WHITE_GRAPE) {
                        seeds = new ItemStack(ObjectRegistry.WHITE_GRAPE_SEEDS);
                    }

                    playerEntity.giveItemStack(seeds);
                }
            }
        }
        return super.finishUsing(stack, world, entityLiving);
    }
}