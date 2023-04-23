package satisfyu.vinery.fabric;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.util.VineryApi;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class VineryExpectPlatformImpl {

    public static Block[] getBlocksForStorage() {
        Set<Block> set = new HashSet<>();
        FabricLoader.getInstance().getEntrypointContainers("vinery", VineryApi.class).forEach(entrypoint -> {
            String modId = entrypoint.getProvider().getMetadata().getId();
            try {
                VineryApi api = entrypoint.getEntrypoint();
                api.registerBlocks(set);
            } catch (Throwable e) {
                Vinery.LOGGER.error("Mod {} provides a broken implementation of VineryApi, therefore couldn't register blocks to the Storage Block Entity", modId, e);
            }
        });
        return set.toArray(new Block[0]);
    }

    public static <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        FabricLoader.getInstance().getEntrypointContainers("vinery", VineryApi.class).forEach(entrypoint -> {
            String modId = entrypoint.getProvider().getMetadata().getId();
            try {
                VineryApi api = entrypoint.getEntrypoint();
                api.registerArmor(models, modelLoader);
            } catch (Throwable e) {
                Vinery.LOGGER.error("Mod {} provides a broken implementation of VineryApi, therefore couldn't register custom models", modId, e);
            }
        });
    }

}
