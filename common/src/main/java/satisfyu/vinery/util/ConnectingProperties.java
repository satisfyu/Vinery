package satisfyu.vinery.util;

import net.minecraft.world.level.block.state.properties.EnumProperty;


public class ConnectingProperties {
    public static final EnumProperty<LineConnectingType> VINERY_LINE_CONNECTING_TYPE;

    static {
        VINERY_LINE_CONNECTING_TYPE = EnumProperty.create("type", LineConnectingType.class);
    }
}