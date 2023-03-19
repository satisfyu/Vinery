package satisfyu.vinery.block;


import satisfyu.vinery.util.GrapevineType;
import net.minecraft.item.Item;

import java.util.List;

/**
 * Namespace for grape items
 */
public class GrapeItem extends Item {

    private final GrapevineType type;
    public GrapeItem(Settings settings, GrapevineType type) {
        super(settings);
        this.type = type;
    }

    public GrapevineType getType() {
        return type;
    }

}
