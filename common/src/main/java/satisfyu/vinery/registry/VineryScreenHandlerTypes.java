package satisfyu.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import satisfyu.vinery.client.gui.handler.ApplePressGuiHandler;

import java.util.function.Supplier;

public class VineryScreenHandlerTypes {

    private static final Registrar<MenuType<?>> MENU_TYPES = DeferredRegister.create(Vinery.MODID, Registries.MENU).getRegistrar();

    public static final RegistrySupplier<MenuType<FermentationBarrelGuiHandler>> FERMENTATION_BARREL_GUI_HANDLER = register("fermentation_barrel_gui_handler", () -> new MenuType<>(FermentationBarrelGuiHandler::new, FeatureFlags.VANILLA_SET));
    public static final RegistrySupplier<MenuType<ApplePressGuiHandler>> APPLE_PRESS_GUI_HANDLER = register("apple_press_gui_handler", () -> new MenuType<>(ApplePressGuiHandler::new, FeatureFlags.VANILLA_SET));


    public static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> register(String name, Supplier<MenuType<T>> menuType){
        return MENU_TYPES.register(new VineryIdentifier(name), menuType);
    }

    public static void init() {
    }
}
