package satisfyu.vinery.block;


import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.util.GrapevineType;
import net.minecraft.item.Item;

import java.util.List;

/**
 * Namespace for grape items
 */
public class GrapeItem extends Item {
    private static final double CHANCE_OF_GETTING_SEEDS = 0.2;
    private final ItemStack returnStack;

    private final GrapevineType type;
    public GrapeItem(Settings settings, GrapevineType type, ItemStack returnStack) {
        super(settings);
        this.type = type;
        this.returnStack = returnStack;
    }

    public GrapevineType getType() {
        return type;
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.vinery.grape.tooltip." + this.getTranslationKey()).formatted(Formatting.ITALIC, Formatting.GRAY));
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
