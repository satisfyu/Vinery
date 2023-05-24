package satisfyu.vinery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class FoodItemReturn extends Item {
	private static final double CHANCE_OF_GETTING_SEEDS = 0.2;

	private final ItemStack returnStack;

	public FoodItemReturn(Properties settings, ItemStack returnStack) {
		super(settings);
		this.returnStack = returnStack;
	}

	public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
		tooltip.add(new TranslatableComponent("item.vinery.grape.tooltip." + this.getDescriptionId()).withStyle(
				ChatFormatting.ITALIC, ChatFormatting.GRAY));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
		if (entityLiving instanceof Player playerEntity) {
			if (stack.getItem() == this) {
				if (new Random().nextDouble() < CHANCE_OF_GETTING_SEEDS) {
					playerEntity.addItem(returnStack);
				}
			}
		}
		return super.finishUsingItem(stack, world, entityLiving);
	}
}