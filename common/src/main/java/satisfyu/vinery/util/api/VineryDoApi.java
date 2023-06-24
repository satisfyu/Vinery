package satisfyu.vinery.util.api;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.client.render.feature.FullCustomArmor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.registry.CustomArmorRegistry;
import satisfyu.vinery.registry.VineryStorageTypes;

import java.util.Map;
import java.util.Set;

@DoApiPlugin
public class VineryDoApi implements DoApiAPI {

    @Override
    public void registerBlocks(Set<Block> blocks) {
        VineryStorageTypes.registerBlocks(blocks);
    }

    @Override
    public <T extends LivingEntity> void registerHat(Map<Item, EntityModel<T>> map, EntityModelSet entityModelSet) {
        CustomArmorRegistry.registerHatModels(map, entityModelSet);
    }

    @Override
    public <T extends LivingEntity> void registerArmor(Map<FullCustomArmor, Pair<HumanoidModel<T>, HumanoidModel<T>>> models, EntityModelSet modelLoader) {

    }
}
