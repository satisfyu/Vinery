package satisfyu.vinery.util.api;

import satisfyu.vinery.registry.CustomArmorRegistry;
import satisfyu.vinery.registry.VineryStorageTypes;

import java.util.Map;
import java.util.Set;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class VineryDefaultAPI implements VineryApi {

    @Override
    public void registerBlocks(Set<Block> blocks) {
        VineryStorageTypes.registerBlocks(blocks);
    }

    @Override
    public <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        CustomArmorRegistry.registerArmorModels(models, modelLoader);
    }
}
