package satisfyu.vinery;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.nio.file.Path;
import java.util.Map;

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
}
