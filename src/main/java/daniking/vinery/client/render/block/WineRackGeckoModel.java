package daniking.vinery.client.render.block;

import daniking.vinery.Vinery;
import daniking.vinery.block.DisplayRackBlock;
import daniking.vinery.block.WineBoxBlock;
import daniking.vinery.block.entity.GeckoStorageBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WineRackGeckoModel extends AnimatedGeoModel<GeckoStorageBlockEntity> {
    @Override
    public Identifier getAnimationFileLocation(GeckoStorageBlockEntity e) {
        return new Identifier(Vinery.MODID, "animations/empty.animation.json");
    }

    @Override
    public Identifier getModelLocation(GeckoStorageBlockEntity e) {
        return new Identifier(Vinery.MODID, "geo/" + e.getModelName() + ".geo.json");
    }

    @Override
    public Identifier getTextureLocation(GeckoStorageBlockEntity e) {
        if (e.getCachedState().getBlock() instanceof WineBoxBlock) {
            return new Identifier(Vinery.MODID, "textures/block/wine_box.png");
        } else if (e.getCachedState().getBlock() instanceof DisplayRackBlock) {
            return new Identifier(Vinery.MODID, "textures/block/wine_rack_4.png");
        }
        return new Identifier(Vinery.MODID, "textures/block/wine_rack_ref.png");
    }
}