package satisfyu.vinery.fabric;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import satisfyu.vinery.util.VineryPre;

public class VineryFabricPre implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        VineryPre.preInit();
    }
}
