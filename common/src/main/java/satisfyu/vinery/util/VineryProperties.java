package satisfyu.vinery.util;

import net.minecraft.world.level.block.state.properties.EnumProperty;


public class VineryProperties {
    public static final EnumProperty<VineryLineConnectingType> VINERY_LINE_CONNECTING_TYPE;

    static {
        VINERY_LINE_CONNECTING_TYPE = EnumProperty.create("type", VineryLineConnectingType.class);
    }
}