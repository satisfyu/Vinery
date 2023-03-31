package satisfyu.vinery.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import satisfyu.vinery.block.grape.GrapeVineBlock;
import satisfyu.vinery.registry.ObjectRegistry;

public class JungleWhiteBushFeature extends Feature<DefaultFeatureConfig> {
    public JungleWhiteBushFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        context.getConfig();
        if (!structureWorldAccess.isAir(blockPos)) {
            return false;
        } else {
            Direction[] directions = Direction.values();

            for (Direction direction : directions) {
                if (direction != Direction.DOWN && GrapeVineBlock.shouldConnectTo(structureWorldAccess, blockPos.offset(direction), direction)) {
                    structureWorldAccess.setBlockState(blockPos, ObjectRegistry.JUNGLE_WHITE_GRAPE_BUSH.getDefaultState().with(GrapeVineBlock.AGE, 2).with(GrapeVineBlock.getFacingProperty(direction), true), 2);
                    return true;
                }
            }

            return false;
        }
    }
}
