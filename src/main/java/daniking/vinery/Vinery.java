package daniking.vinery;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Vinery implements ModInitializer {

    public static final String MODID = "vinery";
    public static final ItemGroup CREATIVE_TAB = FabricItemGroupBuilder.build(new VineryIdentifier("creative_tab"), () -> new ItemStack(ObjectRegistry.GRAPE));

    @Override
    public void onInitialize() {
        ObjectRegistry.init();
    }
}

class VineryIdentifier extends Identifier {

    public VineryIdentifier(String path) {
        super(Vinery.MODID, path);
    }
}
