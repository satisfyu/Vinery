package satisfyu.vinery.block;

import satisfyu.vinery.block.WineRackStorageBlock;
import satisfyu.vinery.registry.VinerySoundEvents;

public class DrawerBlock extends WineRackStorageBlock {
    public DrawerBlock(Properties settings) {
        super(settings, VinerySoundEvents.WINE_RACK_3_OPEN.get(), VinerySoundEvents.WINE_RACK_3_CLOSE.get());
    }
}
