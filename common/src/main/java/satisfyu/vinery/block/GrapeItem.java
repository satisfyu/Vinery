package satisfyu.vinery.block;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.util.GrapevineType;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

/**
 * Namespace for grape items
 */
public class GrapeItem extends Item {
    private static final double CHANCE_OF_GETTING_SEEDS = 0.2;
    private final Item returnItem;

    private final GrapevineType type;
    public GrapeItem(Properties settings, GrapevineType type, Item returnItem) {
        super(settings);
        this.type = type;
        this.returnItem = returnItem;
    }

    public GrapevineType getType() {
        return type;
    }

    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("item.vinery.grape.tooltip." + this.getDescriptionId()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (!world.isClientSide() && entityLiving instanceof Player player) {
            if (stack.getItem() == this) {
                if (world.getRandom().nextFloat() < CHANCE_OF_GETTING_SEEDS) {
                    ItemStack returnStack = new ItemStack(returnItem);
                    if (!player.getInventory().add(returnStack)) {
                        player.drop(returnStack, false);
                    }
                }
            }
        }
        return super.finishUsingItem(stack, world, entityLiving);
    }

}
