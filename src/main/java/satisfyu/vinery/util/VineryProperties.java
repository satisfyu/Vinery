package satisfyu.vinery.util;

import net.minecraft.state.property.EnumProperty;


public class VineryProperties {
    public static final EnumProperty<VineryLineConnectingType> VINERY_LINE_CONNECTING_TYPE;

    static {
        VINERY_LINE_CONNECTING_TYPE = EnumProperty.of("type", VineryLineConnectingType.class);
    }
}