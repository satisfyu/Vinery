package satisfyu.vinery.client.gui.handler.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import satisfyu.vinery.registry.TagRegistry;

public class PicnicBasketSlot extends Slot {
    public PicnicBasketSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return !stack.is(TagRegistry.BASKET_BLACKLIST);
    }

}
