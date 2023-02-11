package satisfyu.vinery.registry;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.StorageBlock;
import satisfyu.vinery.client.model.feature.StrawHatModel;
import satisfyu.vinery.util.VineryApi;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import satisfyu.vinery.client.render.block.*;

import java.util.Map;
import java.util.Set;

public class StorageTypes implements VineryApi {

    public static final Identifier FOUR_BOTTLE = registerStorageType("four_bottle", new FourBottleRenderer());

    public static final Identifier NINE_BOTTLE = registerStorageType("nine_bottle", new NineBottleRenderer());

    public static final Identifier SHELF = registerStorageType("shelf", new ShelfRenderer());

    public static final Identifier WINE_BOX = registerStorageType("wine_box", new WineBoxRenderer());

    public static void init(){
        Vinery.LOGGER.debug("Registering Storage Block Renderers!");
    }

    public static Identifier registerStorageType(String string, StorageTypeRenderer renderer){
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
    public <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelLoader modelLoader) {
        models.put(ObjectRegistry.STRAW_HAT, new StrawHatModel<>(modelLoader.getModelPart(StrawHatModel.LAYER_LOCATION)));
    }
}
