package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class WindowBlock extends IronBarsBlock {


    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 3);

    public WindowBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(!world.isClientSide()){
            updateWindows(world, getHighestWindow(world, pos));
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {

        updateWindows2(world, getHighestWindow2(world, pos));

        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        if (direction.getAxis().isHorizontal()) {
            return state.setValue(PROPERTY_BY_DIRECTION.get(direction), this.attachsTo(neighborState, neighborState.isFaceSturdy(world, neighborPos, direction.getOpposite())));
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }



    private void updateWindows(Level world, BlockPos pos){
        int i = getWindowHeight(world, pos);

        if(i == 3){
            world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(PART, 3));
            world.setBlockAndUpdate(pos.below(), world.getBlockState(pos.below()).setValue(PART, 2));
            world.setBlockAndUpdate(pos.below(2), world.getBlockState(pos.below(2)).setValue(PART, 1));
        }
        else if(i == 2){
            world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(PART, 3));
            world.setBlockAndUpdate(pos.below(), world.getBlockState(pos.below()).setValue(PART, 1));
        }
        else if(i == 1){
            world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(PART, 0));
        }
    }

    private BlockPos getHighestWindow(Level world, BlockPos pos){
        do{
            pos = pos.above();
        }
        while(world.getBlockState(pos).is(ObjectRegistry.WINDOW));
        return pos.below();
    }


    private int getWindowHeight(Level world, BlockPos pos){
        BlockPos highestPos = getHighestWindow(world, pos);
        int i = 0;
        do{
            i++;
            highestPos = highestPos.below();
        }
        while(world.getBlockState(highestPos).is(ObjectRegistry.WINDOW));
        return i;
    }


    private void updateWindows2(LevelAccessor world, BlockPos pos){
        int i = getWindowHeight2(world, pos);

        if(i == 3){
            world.setBlock(pos, world.getBlockState(pos).setValue(PART, 3), 3);
            world.setBlock(pos.below(), world.getBlockState(pos.below()).setValue(PART, 2), 3);
            world.setBlock(pos.below(2), world.getBlockState(pos.below(2)).setValue(PART, 1), 3);
        }
        else if(i == 2){
            world.setBlock(pos, world.getBlockState(pos).setValue(PART, 3), 3);
            world.setBlock(pos.below(), world.getBlockState(pos.below()).setValue(PART, 1), 3);
        }
        else if(i == 1){
            world.setBlock(pos, world.getBlockState(pos).setValue(PART, 0), 3);
        }
    }

    private BlockPos getHighestWindow2(LevelAccessor world, BlockPos pos){
        do{
            pos = pos.above();
        }
        while(world.getBlockState(pos).is(ObjectRegistry.WINDOW));
        return pos.below();
    }


    private int getWindowHeight2(LevelAccessor world, BlockPos pos){
        BlockPos highestPos = getHighestWindow2(world, pos);
        int i = 0;
        do{
            i++;
            highestPos = highestPos.below();
        }
        while(world.getBlockState(highestPos).is(ObjectRegistry.WINDOW));
        return i;
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.below());
        BlockState downState2 = world.getBlockState(pos.below(2));
        return !downState.is(ObjectRegistry.WINDOW) || downState.getValue(PART) != 3 || !downState2.is(ObjectRegistry.WINDOW) || downState2.getValue(PART) != 2;
    }

}
