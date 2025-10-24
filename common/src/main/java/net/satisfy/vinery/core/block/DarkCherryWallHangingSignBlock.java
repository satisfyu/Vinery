package net.satisfy.vinery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.satisfy.vinery.core.block.entity.DarkCherryHangingSignBlockEntity;

public class DarkCherryWallHangingSignBlock extends WallHangingSignBlock {
    public DarkCherryWallHangingSignBlock(Properties properties, WoodType type) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DarkCherryHangingSignBlockEntity(pPos, pState);
    }
}
