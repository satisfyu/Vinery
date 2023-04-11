package satisfyu.vinery.util;

import net.minecraft.block.Block;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;

import java.util.Map;
import java.util.Set;

public interface VineryApi {

    void registerBlocks(Set<Block> blocks);

    <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelLoader modelLoader);

}
