package satisfyu.vinery.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.*;

public class VineryScreenHandlerTypes {

    public static final MenuType<StoveGuiHandler> STOVE_GUI_HANDLER = new MenuType<>(StoveGuiHandler::new, FeatureFlags.VANILLA_SET);
    public static final MenuType<FermentationBarrelGuiHandler> FERMENTATION_BARREL_GUI_HANDLER = new MenuType<>(FermentationBarrelGuiHandler::new, FeatureFlags.VANILLA_SET);
    public static final MenuType<CookingPotGuiHandler> COOKING_POT_SCREEN_HANDLER = new MenuType<>(CookingPotGuiHandler::new, FeatureFlags.VANILLA_SET);
    public static final MenuType<WinePressGuiHandler> WINE_PRESS_SCREEN_HANDLER = new MenuType<>(WinePressGuiHandler::new, FeatureFlags.VANILLA_SET);


    public static void init() {
        Registry.register(BuiltInRegistries.MENU, new VineryIdentifier("stove_gui_handler"), STOVE_GUI_HANDLER);
        Registry.register(BuiltInRegistries.MENU, new VineryIdentifier("fermentation_barrel_gui_handler"), FERMENTATION_BARREL_GUI_HANDLER);
        Registry.register(BuiltInRegistries.MENU, new VineryIdentifier("cooking_pot_gui_handler"), COOKING_POT_SCREEN_HANDLER);
        Registry.register(BuiltInRegistries.MENU, new VineryIdentifier("wine_press_gui_handler"), WINE_PRESS_SCREEN_HANDLER);
    }
}
