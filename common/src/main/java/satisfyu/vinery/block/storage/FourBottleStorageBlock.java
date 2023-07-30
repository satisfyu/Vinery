package satisfyu.vinery.block.storage;

import de.cristelknight.doapi.common.block.StorageBlock;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import satisfyu.vinery.item.DrinkBlockSmallItem;
import satisfyu.vinery.registry.VineryStorageTypes;

import java.util.List;

public class FourBottleStorageBlock extends StorageBlock {

    public FourBottleStorageBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.getItem() instanceof DrinkBlockSmallItem;
    }

    @Override
    public int size(){
        return 4;
    }


    @Override
    public ResourceLocation type() {
        return VineryStorageTypes.FOUR_BOTTLE;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN, Direction.UP};
    }


    @Override
    public int getSection(Float x, Float y) {

        if (x > 0.375 && x < 0.625) {
            if(y >= 0.55){
                return  0;
            }
            else if(y <= 0.45) {
                return 3;
            }
        }
        else if(y > 0.35 && y < 0.65){
            if(x < 0.4){
                return 1;
            }
            else if(x > 0.65){
                return 2;
            }
        }

        return Integer.MIN_VALUE;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("block.vinery.winebox_small.tooltip.shift_1"));

    }
}
