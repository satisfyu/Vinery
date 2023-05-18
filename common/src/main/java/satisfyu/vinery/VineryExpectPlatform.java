package satisfyu.vinery;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import satisfyu.vinery.util.boat.api.TerraformBoatType;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class VineryExpectPlatform {

    @ExpectPlatform
    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks){
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }


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
}
