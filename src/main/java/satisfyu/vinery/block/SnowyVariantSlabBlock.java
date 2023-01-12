package satisfyu.vinery.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SnowyVariantSlabBlock extends SlabBlock {
	public static final IntProperty VARIANT = IntProperty.of("variant", 0, 2);
	public static final BooleanProperty SNOWY = Properties.SNOWY;
	
	public SnowyVariantSlabBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, false).with(VARIANT, 0).with(SNOWY, false));
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction == Direction.UP) {
			return state.with(SNOWY, neighborState.isIn(BlockTags.SNOW));
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(VARIANT, ctx.getPlayer() != null ? ctx.getPlayer().getRandom().nextInt(3) : 0);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(VARIANT);
		builder.add(SNOWY);
	}
}