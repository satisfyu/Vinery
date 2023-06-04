package satisfyu.vinery.block;

import satisfyu.vinery.block.WineRackStorageBlock;
import satisfyu.vinery.registry.VinerySoundEvents;

public class CabinetBlock extends WineRackStorageBlock {
    public CabinetBlock(Properties settings) {
        super(settings, VinerySoundEvents.WINE_RACK_5_OPEN.get(), VinerySoundEvents.WINE_RACK_5_CLOSE.get());
    }
}
