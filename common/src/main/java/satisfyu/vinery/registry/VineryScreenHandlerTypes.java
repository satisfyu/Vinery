package satisfyu.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.CookingPotGuiHandler;
import satisfyu.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import satisfyu.vinery.client.gui.handler.StoveGuiHandler;
import satisfyu.vinery.client.gui.handler.WinePressGuiHandler;

import java.util.function.Supplier;

public class VineryScreenHandlerTypes {

    private static final Registrar<MenuType<?>> MENU_TYPES = DeferredRegister.create(Vinery.MODID, Registry.MENU_REGISTRY).getRegistrar();

    public static final RegistrySupplier<MenuType<StoveGuiHandler>> STOVE_GUI_HANDLER = register("stove_gui_handler", () -> new MenuType<>(StoveGuiHandler::new));
    public static final RegistrySupplier<MenuType<FermentationBarrelGuiHandler>> FERMENTATION_BARREL_GUI_HANDLER = register("fermentation_barrel_gui_handler", () -> new MenuType<>(FermentationBarrelGuiHandler::new));
    public static final RegistrySupplier<MenuType<CookingPotGuiHandler>> COOKING_POT_SCREEN_HANDLER = register("cooking_pot_gui_handler", () -> new MenuType<>(CookingPotGuiHandler::new));
    public static final RegistrySupplier<MenuType<WinePressGuiHandler>> WINE_PRESS_SCREEN_HANDLER = register("wine_press_gui_handler", () -> new MenuType<>(WinePressGuiHandler::new));


    public static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> register(String name, Supplier<MenuType<T>> menuType){
        return MENU_TYPES.register(new VineryIdentifier(name), menuType);
    }

    public static void init() {
    }
}
