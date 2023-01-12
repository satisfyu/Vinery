package satisfyu.vinery.block;

import satisfyu.vinery.registry.StorageTypes;
import satisfyu.vinery.util.VineryTags;
import satisfyu.vinery.util.VineryUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ShelfBlock extends StorageBlock{


    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.25, 0.5, 1, 0.3125, 1), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.1875, 0.625, 0.1875, 0.25, 0.9375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.1875, 0.625, 0.875, 0.25, 0.9375), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0.0625, 0.9375, 0.1875, 0.25, 1), BooleanBiFunction.OR);
        shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.8125, 0.0625, 0.9375, 0.875, 0.25, 1), BooleanBiFunction.OR);

        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            map.put(direction, VineryUtils.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    public ShelfBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN};
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return !(stack.getItem() instanceof BlockItem) || stack.isIn(VineryTags.IGNORE_BLOCK_ITEM);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE.get(state.get(FACING));
    }

    @Override
    public int size(){
        return 9;
    }

    @Override
    public Identifier type() {
        return StorageTypes.SHELF;
    }

    @Override
    public int getSection(Float f, Float y) {
        int nSection;
        float oneS = (float) 1 / 9;

        if (f < oneS) {
            nSection = 0;
        }
        else if(f < oneS*2){
            nSection = 1;
        }
        else if(f < oneS*3){
            nSection = 2;
        }
        else if(f < oneS*4){
            nSection = 3;
        }
        else if(f < oneS*5){
            nSection = 4;
        }
        else if(f < oneS*6){
            nSection = 5;
        }
        else if(f < oneS*7){
            nSection = 6;
        }
        else if(f < oneS*8){
            nSection = 7;
        }
        else nSection = 8;

        return 8 - nSection;
    }
}
