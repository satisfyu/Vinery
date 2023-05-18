package satisfyu.vinery.item;


import net.minecraft.world.item.Item;
import satisfyu.vinery.registry.ObjectRegistry;


public class JuiceItem extends ConsumAndReturnItem {
    public JuiceItem(Item.Properties settings) {
        super(settings, 40, ObjectRegistry.WINE_BOTTLE, true);
    }

}