package daniking.vinery.registry;

import daniking.vinery.VineryIdentifier;
import daniking.vinery.client.gui.handler.StoveGuiHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class VineryScreenHandlerTypes {

    public static final ScreenHandlerType<StoveGuiHandler> STOVE_GUI_HANDLER = new ScreenHandlerType<>(StoveGuiHandler::new);

    public static void init() {
        Registry.register(Registry.SCREEN_HANDLER, new VineryIdentifier("stove_gui_handler"), STOVE_GUI_HANDLER);
    }
}
