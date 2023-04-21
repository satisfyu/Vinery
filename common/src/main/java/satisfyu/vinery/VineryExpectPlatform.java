package satisfyu.vinery;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.util.boat.api.TerraformBoatType;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class VineryExpectPlatform {
    @ExpectPlatform
    public static Block[] getBlocksForStorage() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }



    @ExpectPlatform
    public static  void register(ResourceLocation location, Supplier<TerraformBoatType> boatTypeSupplier) {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void loadInstance() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }


    @ExpectPlatform
    public static ResourceKey<TerraformBoatType> createKey(ResourceLocation id) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static ResourceLocation getId(TerraformBoatType type) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static TerraformBoatType get(ResourceLocation location) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static Set<Map.Entry<ResourceKey<TerraformBoatType>, TerraformBoatType>> entrySet() {
        throw new AssertionError();
    }

}
