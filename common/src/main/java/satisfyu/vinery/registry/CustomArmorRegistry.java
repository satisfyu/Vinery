package satisfyu.vinery.registry;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import satisfyu.vinery.client.model.feature.StrawHatModel;
import satisfyu.vinery.item.CustomModelArmorItem;

public class CustomArmorRegistry {

    /**
     * Das Item muss {@link CustomModelArmorItem} erweitern
    **/


    public static void registerModels(){
        EntityModelLayerRegistry.register(StrawHatModel.LAYER_LOCATION, StrawHatModel::getTexturedModelData);
    }

}