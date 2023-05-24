package satisfyu.vinery.block.storage;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfyu.vinery.block.storage.api.StorageBlock;
import satisfyu.vinery.registry.VineryStorageTypes;
import satisfyu.vinery.util.GeneralUtil;
import satisfyu.vinery.util.VineryTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class FlowerBoxBlock extends StorageBlock {
	private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0, 0.5625, 1, 0.375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.5625, 0.0625, 0.375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.5625, 0.9375, 0.375, 0.625), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.9375, 0.9375, 0.375, 1), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0, 0.625, 0.9375, 0.3125, 0.9375), BooleanOp.OR);
		return shape;
	};

	public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
		for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
			map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
		}
	});

	public FlowerBoxBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE.get(state.getValue(FACING));
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (player.isShiftKeyDown()) { return InteractionResult.PASS; }
		return super.use(state, world, pos, player, hand, hit);
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public ResourceLocation type() {
		return VineryStorageTypes.FLOWER_BOX;
	}

	@Override
	public Direction[] unAllowedDirections() {
		return new Direction[] { Direction.DOWN };
	}

	@Override
	public boolean canInsertStack(ItemStack stack) {
		return stack.is(VineryTags.SMALL_FLOWER);
	}

	@Override
	public int getSection(Float x, Float y) {
		if (x < 0.5) { return 0; }
		return 1;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
		tooltip.add(new TranslatableComponent("block.vinery.canbeplaced.tooltip").withStyle(ChatFormatting.ITALIC,
				ChatFormatting.GRAY));
	}
}

