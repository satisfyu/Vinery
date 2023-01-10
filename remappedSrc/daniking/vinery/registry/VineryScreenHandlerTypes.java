package daniking.vinery.registry;

import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.gui.handler.CookingPotGuiHandler;
import daniking.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import daniking.vinery.client.gui.handler.StoveGuiHandler;
import daniking.vinery.client.gui.handler.WinePressGuiHandler;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

public class VineryScreenHandlerTypes {

    public static final MenuType<StoveGuiHandler> STOVE_GUI_HANDLER = new MenuType<>(StoveGuiHandler::new);
    public static final MenuType<FermentationBarrelGuiHandler> FERMENTATION_BARREL_GUI_HANDLER = new MenuType<>(FermentationBarrelGuiHandler::new);
    public static final MenuType<CookingPotGuiHandler> COOKING_POT_SCREEN_HANDLER = new MenuType<>(CookingPotGuiHandler::new);
    public static final MenuType<WinePressGuiHandler> WINE_PRESS_SCREEN_HANDLER = new MenuType<>(WinePressGuiHandler::new);


    public static void init() {
        Registry.register(Registry.MENU, new VineryIdentifier("stove_gui_handler"), STOVE_GUI_HANDLER);
        Registry.register(Registry.MENU, new VineryIdentifier("fermentation_barrel_gui_handler"), FERMENTATION_BARREL_GUI_HANDLER);
        Registry.register(Registry.MENU, new VineryIdentifier("cooking_pot_gui_handler"), COOKING_POT_SCREEN_HANDLER);
        Registry.register(Registry.MENU, new VineryIdentifier("wine_press_gui_handler"), WINE_PRESS_SCREEN_HANDLER);

    }
}
