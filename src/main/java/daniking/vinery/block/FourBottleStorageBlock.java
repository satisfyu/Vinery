package daniking.vinery.block;

import daniking.vinery.item.DrinkBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

public class FourBottleStorageBlock extends StorageBlock {

    public FourBottleStorageBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.getItem() instanceof DrinkBlockItem;
    }

    @Override
    public int size(){
        return 4;
    }

    @Override
    public StorageType type() {
        return StorageType.FOUR_BOTTLE;
    }

    @Override
    public int getSection(Pair<Float, Float> ff) {
        float x = ff.getLeft();
        float y = ff.getRight();

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

        return 1000000;
    }
}
