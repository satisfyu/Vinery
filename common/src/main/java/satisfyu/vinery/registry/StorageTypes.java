package satisfyu.vinery.registry;

import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.StorageBlock;
import satisfyu.vinery.client.model.feature.StrawHatModel;
import satisfyu.vinery.util.VineryApi;
import satisfyu.vinery.client.render.block.*;

import java.util.Map;
import java.util.Set;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class StorageTypes implements VineryApi {

    public static final ResourceLocation FOUR_BOTTLE = registerStorageType("four_bottle", new FourBottleRenderer());

    public static final ResourceLocation NINE_BOTTLE = registerStorageType("nine_bottle", new NineBottleRenderer());

    public static final ResourceLocation SHELF = registerStorageType("shelf", new ShelfRenderer());

    public static final ResourceLocation WINE_BOX = registerStorageType("wine_box", new WineBoxRenderer());

    public static void init(){
        VineryO.LOGGER.debug("Registering Storage Block Renderers!");
    }

    public static ResourceLocation registerStorageType(String string, StorageTypeRenderer renderer){
         return StorageBlock.registerStorageType(new VineryIdentifier(string), renderer);
    }

    @Override
    public void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.WINE_RACK_2);
        blocks.add(ObjectRegistry.WINE_RACK_1);
        blocks.add(ObjectRegistry.SHELF);
        blocks.add(ObjectRegistry.WINE_BOX);
    }

    @Override
    public <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        models.put(ObjectRegistry.STRAW_HAT, new StrawHatModel<>(modelLoader.bakeLayer(StrawHatModel.LAYER_LOCATION)));
    }
}
