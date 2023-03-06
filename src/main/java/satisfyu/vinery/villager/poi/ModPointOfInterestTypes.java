package satisfyu.vinery.villager.poi;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.registry.ObjectRegistry;

public class ModPointOfInterestTypes{

    public static final PointOfInterestType SHOP = registerPOI("shop", ObjectRegistry.BASKET);
    public static final RegistryKey<PointOfInterestType> SHOP_KEY = RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(Vinery.MODID, "shop"));

    public static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(Vinery.MODID, name),
                5, 1, ImmutableSet.copyOf(block.getStateManager().getStates()));
    }

    public static void init() {
        System.out.println("Init ModPOI"); //TODO
    }
}
