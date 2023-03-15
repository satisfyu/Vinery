package satisfyu.vinery.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.Random;

public class FoodItemReturn extends Item {
    private static final Random RANDOM = new Random();
    private static final double CHANCE_OF_GETTING_SEEDS = 0.2;

    public FoodItemReturn(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack heldStack = playerEntity.getStackInHand(hand);
        if (heldStack.getItem() == ObjectRegistry.RED_GRAPE || heldStack.getItem() == ObjectRegistry.WHITE_GRAPE) {
            return new TypedActionResult<>(ActionResult.PASS, heldStack);
        }

        return new TypedActionResult<>(ActionResult.FAIL, heldStack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entityLiving) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityLiving;

            if (stack.getItem() == ObjectRegistry.RED_GRAPE || stack.getItem() == ObjectRegistry.RED_GRAPE_SEEDS) {
                double random = RANDOM.nextDouble();

                if (random < CHANCE_OF_GETTING_SEEDS) {
                    ItemStack seeds = null;
                    if (stack.getItem() == ObjectRegistry.RED_GRAPE) {
                        seeds = new ItemStack(ObjectRegistry.RED_GRAPE_SEEDS, 1);
                    } else {
                        if (stack.getItem() == ObjectRegistry.WHITE_GRAPE) {
                            seeds = new ItemStack(ObjectRegistry.WHITE_GRAPE_SEEDS, 1);
                        }

                        boolean added = playerEntity.getInventory().insertStack(seeds);

                        if (added) {
                            stack.decrement(1);
                        }
                    }
                }
            }
        }
        return stack;
    }
}
