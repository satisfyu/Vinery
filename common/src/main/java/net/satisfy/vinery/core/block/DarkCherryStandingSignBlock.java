package net.satisfy.vinery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.satisfy.vinery.core.block.entity.DarkCherrySignBlockEntity;

public class DarkCherryStandingSignBlock extends StandingSignBlock {
    public DarkCherryStandingSignBlock(Properties properties, WoodType type) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DarkCherrySignBlockEntity(pPos, pState);
    }
}
