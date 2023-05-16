package satisfyu.vinery.util.api;

import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import satisfyu.vinery.registry.CustomArmorRegistry;
import satisfyu.vinery.registry.VineryStorageTypes;

import java.util.Map;
import java.util.Set;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@DoApiPlugin
public class VineryDoApi implements DoApiAPI {

    @Override
    public void registerBlocks(Set<Block> blocks) {
        VineryStorageTypes.registerBlocks(blocks);
    }

    @Override
    public <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        CustomArmorRegistry.registerArmorModels(models, modelLoader);
    }
}
