package satisfyu.vinery.item;

import net.minecraft.world.item.Item;
import satisfyu.vinery.registry.ItemRegistry;

public class JuiceItem extends ConsumAndReturnItem {
	public JuiceItem(Item.Properties settings) {
		super(settings, 40, ItemRegistry.WINE_BOTTLE, true);
	}
}