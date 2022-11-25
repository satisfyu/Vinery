package daniking.vinery.block;

import daniking.vinery.Vinery;
import daniking.vinery.registry.ObjectRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class WindowBlock extends PaneBlock {


    public static final IntProperty PART = IntProperty.of("part", 0, 3);

    public WindowBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(!world.isClient()){
            updateWindows(world, getHighestWindow(world, pos));
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {

        updateWindows((World) world, getHighestWindow((World) world, pos));

        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction.getAxis().isHorizontal()) {
            return state.with(FACING_PROPERTIES.get(direction), this.connectsTo(neighborState, neighborState.isSideSolidFullSquare(world, neighborPos, direction.getOpposite())));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }



    private void updateWindows(World world, BlockPos pos){
        int i = getWindowHeight(world, pos);

        if(i == 3){
            world.setBlockState(pos, world.getBlockState(pos).with(PART, 3));
            world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(PART, 2));
            world.setBlockState(pos.down(2), world.getBlockState(pos.down(2)).with(PART, 1));
        }
        else if(i == 2){
            world.setBlockState(pos, world.getBlockState(pos).with(PART, 3));
            world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(PART, 1));
        }
        else if(i == 1){
            world.setBlockState(pos, world.getBlockState(pos).with(PART, 0));
        }
    }

    private BlockPos getHighestWindow(World world, BlockPos pos){
        do{
            pos = pos.up();
        }
        while(world.getBlockState(pos).isOf(ObjectRegistry.WINDOW));
        return pos.down();
    }


    private int getWindowHeight(World world, BlockPos pos){
        BlockPos highestPos = getHighestWindow(world, pos);
        int i = 0;
        do{
            i++;
            highestPos = highestPos.down();
        }
        while(world.getBlockState(highestPos).isOf(ObjectRegistry.WINDOW));
        return i;
    }



    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());
        BlockState downState2 = world.getBlockState(pos.down(2));
        return !downState.isOf(ObjectRegistry.WINDOW) || downState.get(PART) != 3 || !downState2.isOf(ObjectRegistry.WINDOW) || downState2.get(PART) != 2;
    }

}
