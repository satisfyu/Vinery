package daniking.vinery.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;

public class DrinkBlockItem extends BlockItem {
    public DrinkBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}
