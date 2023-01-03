package daniking.vinery.block;

import daniking.vinery.item.DrinkBlockBigItem;
import daniking.vinery.item.DrinkBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

public class FourBottleStorageBlock extends StorageBlock {

    public FourBottleStorageBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.getItem() instanceof DrinkBlockItem || stack.getItem() instanceof DrinkBlockBigItem;
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
        int nSection;
        float oneS = (float) 1 / 9;
        float f = ff.getLeft();
        float y = ff.getRight();

        if (f < oneS) {
            nSection = 0;
        }
        else if(f < oneS*2){
            nSection = 1;
        }
        else if(f < oneS*3){
            nSection = 2;
        }
        else if(f < oneS*4){
            nSection = 3;
        }
        else if(f < oneS*5){
            nSection = 4;
        }
        else if(f < oneS*6){
            nSection = 5;
        }
        else if(f < oneS*7){
            nSection = 6;
        }
        else if(f < oneS*8){
            nSection = 7;
        }
        else nSection = 8;

        return 8 - nSection;
    }
}
