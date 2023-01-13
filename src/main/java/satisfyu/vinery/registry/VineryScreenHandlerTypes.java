package satisfyu.vinery.registry;

import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.gui.handler.CookingPotGuiHandler;
import satisfyu.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import satisfyu.vinery.client.gui.handler.StoveGuiHandler;
import satisfyu.vinery.client.gui.handler.WinePressGuiHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class VineryScreenHandlerTypes {

    public static final ScreenHandlerType<StoveGuiHandler> STOVE_GUI_HANDLER = new ScreenHandlerType<>(StoveGuiHandler::new);
    public static final ScreenHandlerType<FermentationBarrelGuiHandler> FERMENTATION_BARREL_GUI_HANDLER = new ScreenHandlerType<>(FermentationBarrelGuiHandler::new);
    public static final ScreenHandlerType<CookingPotGuiHandler> COOKING_POT_SCREEN_HANDLER = new ScreenHandlerType<>(CookingPotGuiHandler::new);
    public static final ScreenHandlerType<WinePressGuiHandler> WINE_PRESS_SCREEN_HANDLER = new ScreenHandlerType<>(WinePressGuiHandler::new);


    public static void init() {
        Registry.register(Registry.SCREEN_HANDLER, new VineryIdentifier("stove_gui_handler"), STOVE_GUI_HANDLER);
        Registry.register(Registry.SCREEN_HANDLER, new VineryIdentifier("fermentation_barrel_gui_handler"), FERMENTATION_BARREL_GUI_HANDLER);
        Registry.register(Registry.SCREEN_HANDLER, new VineryIdentifier("cooking_pot_gui_handler"), COOKING_POT_SCREEN_HANDLER);
        Registry.register(Registry.SCREEN_HANDLER, new VineryIdentifier("wine_press_gui_handler"), WINE_PRESS_SCREEN_HANDLER);

    }
}
