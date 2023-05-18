package satisfyu.vinery.forge.extraapiutil;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.util.api.VineryApi;
import satisfyu.vinery.util.api.VineryPlugin;

import java.util.List;


public class APIFinder {


    public static List<Pair<List<String>, VineryApi>> scanForAPIs() {
        List<Pair<List<String>, VineryApi>> instances = Lists.newArrayList();
        for (ModFileScanData data : ModList.get().getAllScanData()) {
            List<ModFileScanData.AnnotationData> ebsTargets = data.getAnnotations().stream().
                    filter(annotationData -> Type.getType(VineryPlugin.class).equals(annotationData.annotationType())).
                    toList();

            List<String> modIds = data.getIModInfoData().stream()
                    .flatMap(info -> info.getMods().stream())
                    .map(IModInfo::getModId)
                    .toList();

            for(ModFileScanData.AnnotationData ad : ebsTargets){
                Class<VineryApi> clazz;

                try {
                    clazz = (Class<VineryApi>) Class.forName(ad.memberName());
                } catch (ClassNotFoundException e) {
                    Vinery.LOGGER.error("Failed to load api class {} for @CristelPlugin annotation", ad.clazz(), e);
                    continue;
                }
                try {
                    instances.add(new Pair<>(modIds, clazz.getDeclaredConstructor().newInstance()));
                } catch (Throwable throwable) {
                    Vinery.LOGGER.error("Failed to load api: " + ad.memberName(), throwable);
                }
            }
        }
        return instances;
    }
}
