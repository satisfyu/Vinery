package daniking.vinery.registry;

import daniking.vinery.client.model.feature.StrawHatModel;
import daniking.vinery.item.CustomModelArmorItem;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import java.util.Map;

public class CustomArmorRegistry {

    /**
     * Das Item muss {@link CustomModelArmorItem} erweitern
    **/
    public static <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader){
        models.put(ObjectRegistry.STRAW_HAT, new StrawHatModel<>(modelLoader.bakeLayer(StrawHatModel.LAYER_LOCATION)));
    }

    public static void registerModels(){
        EntityModelLayerRegistry.registerModelLayer(StrawHatModel.LAYER_LOCATION, StrawHatModel::getTexturedModelData);
    }

}
