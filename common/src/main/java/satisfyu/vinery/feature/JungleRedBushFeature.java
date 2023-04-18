package satisfyu.vinery.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import satisfyu.vinery.block.grape.GrapeVineBlock;
import satisfyu.vinery.registry.ObjectRegistry;

public class JungleRedBushFeature extends Feature<NoneFeatureConfiguration> {
    public JungleRedBushFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        context.config();
        if (!structureWorldAccess.isEmptyBlock(blockPos)) {
            return false;
        } else {
            Direction[] directions = Direction.values();

            for (Direction direction : directions) {
                if (direction != Direction.DOWN && GrapeVineBlock.isAcceptableNeighbour(structureWorldAccess, blockPos.relative(direction), direction)) {
                    structureWorldAccess.setBlock(blockPos, ObjectRegistry.JUNGLE_RED_GRAPE_BUSH.get().defaultBlockState().setValue(GrapeVineBlock.AGE, 2).setValue(GrapeVineBlock.getPropertyForFace(direction), true), 2);
                    return true;
                }
            }

            return false;
        }
    }
}
