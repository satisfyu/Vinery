package satisfyu.vinery.fabric;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import satisfyu.vinery.util.PreInit;

public class VineryFabricPre implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        PreInit.preInit();
    }
}