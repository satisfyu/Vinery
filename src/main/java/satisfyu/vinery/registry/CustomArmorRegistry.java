package satisfyu.vinery.registry;

import satisfyu.vinery.client.model.feature.StrawHatModel;
import satisfyu.vinery.item.CustomModelArmorItem;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;

import java.util.Map;

public class CustomArmorRegistry {

    /**
     * Das Item muss {@link CustomModelArmorItem} erweitern
    **/


    public static void registerModels(){
        EntityModelLayerRegistry.registerModelLayer(StrawHatModel.LAYER_LOCATION, StrawHatModel::getTexturedModelData);
    }

}