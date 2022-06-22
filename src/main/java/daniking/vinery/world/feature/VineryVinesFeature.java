package daniking.vinery.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class VineryVinesFeature extends Feature<SimpleBlockFeatureConfig> {

    public VineryVinesFeature(Codec<SimpleBlockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        if (!structureWorldAccess.isAir(blockPos)) {
            return false;
        }
        SimpleBlockFeatureConfig cfg = context.getConfig();
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN || !VineBlock.shouldConnectTo(structureWorldAccess, blockPos.offset(direction), direction)) continue;
            structureWorldAccess.setBlockState(blockPos, cfg.toPlace().getBlockState(context.getRandom(), blockPos).with(VineBlock.getFacingProperty(direction), true), Block.NOTIFY_LISTENERS);
            return true;
        }
        return false;
    }
}
