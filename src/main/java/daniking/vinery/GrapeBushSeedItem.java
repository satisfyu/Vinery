package daniking.vinery;

import daniking.vinery.block.GrapeBush;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class GrapeBushSeedItem extends AliasedBlockItem {

    private final GrapeBush.Type type;

    public GrapeBushSeedItem(Block block, Settings settings, GrapeBush.Type type) {
        super(block, settings);
        this.type = type;
    }



//    @Override
//    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
//        if (user instanceof PlayerEntity) {
//            final double n = Math.random();
//            // 20% chance
//            if (n < 0.2) {
//                final int max = 3;
//                final int randomCount = user.getRandom().nextInt(max) + 1;
//                ((PlayerEntity) user).getInventory().insertStack(new ItemStack(this.type == GrapeBush.Type.RED ? ObjectRegistry.RED_GRAPE_SEEDS : ObjectRegistry.WHITE_GRAPE_SEEDS, randomCount));
//            }
//        }
//        return super.finishUsing(stack, world, user);
//    }
}
