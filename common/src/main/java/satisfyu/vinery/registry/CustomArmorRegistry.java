package satisfyu.vinery.registry;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import satisfyu.vinery.client.model.feature.StrawHatModel;

import java.util.Map;
import java.util.function.Supplier;

public class CustomArmorRegistry {

    public static void registerArmorModelLayers(){
        EntityModelLayerRegistry.register(StrawHatModel.LAYER_LOCATION, StrawHatModel::getTexturedModelData);
    }

    public static  <T extends LivingEntity> void registerArmorModels(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        models.put(ObjectRegistry.STRAW_HAT.get(), new StrawHatModel<>(modelLoader.bakeLayer(StrawHatModel.LAYER_LOCATION)));
    }
}