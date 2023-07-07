package satisfyu.vinery.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.registry.ObjectRegistry;

public class VineryTab {
    public static final DeferredRegister<CreativeModeTab> VINERY_TABS = DeferredRegister.create(Vinery.MODID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> VINERY_TAB = VINERY_TABS.register("vinery", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(ObjectRegistry.WINE_BOTTLE.get()))
            .title(Component.translatable("creativetab.vinery.tab"))
            .displayItems((parameters, output) -> {
                output.accept(ObjectRegistry.WINE_BOTTLE.get());
            })
            .build());

    public static void init() {
        VINERY_TABS.register();
    }
}
