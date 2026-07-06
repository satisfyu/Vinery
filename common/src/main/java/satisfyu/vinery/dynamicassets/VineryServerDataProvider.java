package satisfyu.vinery.dynamicassets;

import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynServerResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicDataPack;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicTexturePack;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.config.VineryConfig;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VineryServerDataProvider {

    public static void init() {
        ServerDataGenerator generator = new ServerDataGenerator();
        generator.register();
    }

    public static class ServerDataGenerator extends DynServerResourcesGenerator {

        protected ServerDataGenerator() {
            super(new DynamicDataPack(new VineryIdentifier("dynamic_data_pack"), Pack.Position.TOP, true, true));
        }

        @Override
        public Logger getLogger() {
            return Vinery.LOGGER;
        }

        @Override
        public boolean dependsOnLoadedPacks() {
            return false;
        }

        @Override
        public void regenerateDynamicAssets(ResourceManager resourceManager) {
            this.getLogger().debug("Generating lattice data pack");
            AtomicInteger latticeCount = new AtomicInteger(); // wtf is an atomicinteger I'm just doing what IDEA says
            BlockSetAPI.getBlockSet(WoodType.class).getValues().forEach((WoodType woodType) -> {
                if (!VineryConfig.DEFAULT.getConfig().enableNetherLattices()) {
                    if (woodType.toVanilla() == net.minecraft.world.level.block.state.properties.WoodType.WARPED ||
                            woodType.toVanilla() == net.minecraft.world.level.block.state.properties.WoodType.CRIMSON) {
                        return;
                    }
                }
                StaticResource baseRecipe = StaticResource.getOrFail(resourceManager, new VineryIdentifier("recipes/oak_lattice.json"));
                this.addSimilarJsonResource(resourceManager, baseRecipe, (string) -> string.replaceAll("oak", woodType.getVariantId("%s")).replaceAll("vinery:cherry_lattice", "vinery:dark_cherry_lattice"));
                this.getLogger().debug("Generated recipe for {} lattice", woodType.getReadableName());
                latticeCount.getAndIncrement();
            });
            this.getLogger().debug("Generated {} lattice recipes", latticeCount);

            // Keep the "wine_collector" advancement completable when wines are disabled: its single
            // criterion requires holding every wine at once, so drop any disabled wine's requirement.
            List<String> disabledWines = VineryConfig.DEFAULT.getConfig().disabledWines();
            if (disabledWines != null && !disabledWines.isEmpty()) {
                try {
                    StaticResource wineCollector = StaticResource.getOrFail(resourceManager, new VineryIdentifier("advancements/main/wine_collector.json"));
                    this.addSimilarJsonResource(resourceManager, wineCollector,
                            content -> filterWineCollector(content, disabledWines),
                            path -> path);
                    this.getLogger().info("Patched wine_collector advancement to skip {} disabled wine(s)", disabledWines.size());
                } catch (Exception e) {
                    this.getLogger().error("Failed to patch wine_collector advancement for disabled wines", e);
                }
            }
        }

        /** Removes any {@code inventory_changed} item predicate that references a disabled wine. */
        private static String filterWineCollector(String json, List<String> disabledWines) {
            JsonObject obj = GsonHelper.parse(json);
            JsonObject conditions = obj.getAsJsonObject("criteria").getAsJsonObject("get_wines").getAsJsonObject("conditions");
            JsonArray items = conditions.getAsJsonArray("items");
            JsonArray kept = new JsonArray();
            for (JsonElement entry : items) {
                boolean disabled = false;
                for (JsonElement id : entry.getAsJsonObject().getAsJsonArray("items")) {
                    if (disabledWines.contains(id.getAsString())) {
                        disabled = true;
                        break;
                    }
                }
                if (!disabled) {
                    kept.add(entry);
                }
            }
            conditions.add("items", kept);
            return obj.toString();
        }
    }
}

