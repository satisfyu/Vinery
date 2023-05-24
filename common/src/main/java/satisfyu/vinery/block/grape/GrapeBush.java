package satisfyu.vinery.block.grape;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfyu.vinery.util.GrapevineType;

import java.util.Random;

public class GrapeBush extends BushBlock implements BonemealableBlock {
	public static final IntegerProperty AGE;

	private static final VoxelShape SHAPE;

	static {
		AGE = BlockStateProperties.AGE_3;
		SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	}

	public final GrapevineType type;

	private final int chance;

	public GrapeBush(Properties settings, GrapevineType type) {
		this(settings, type, 5);
	}

	public GrapeBush(Properties settings, GrapevineType type, int chance) {
		super(settings);
		this.chance = chance;
		this.type = type;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	public GrapevineType getType() {
		return type;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		int i = state.getValue(AGE);
		boolean bl = i == 3;
		if (!bl && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
			return InteractionResult.PASS;
		}
		else if (i > 1) {
			int x = world.random.nextInt(2);
			popResource(world, pos, new ItemStack(getGrapeType().getItem(), x + (bl ? 1 : 0)));
			world.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F,
					0.8F + world.random.nextFloat() * 0.4F);
			world.setBlock(pos, state.setValue(AGE, 1), 2);
			return InteractionResult.sidedSuccess(world.isClientSide);
		}
		else {
			return super.use(state, world, pos, player, hand, hit);
		}
	}

	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		int i = state.getValue(AGE);
		if (i < 3 && random.nextInt(chance) == 0 && canGrowPlace(world, pos, state)) {
			BlockState blockState = state.setValue(AGE, i + 1);
			world.setBlock(pos, blockState, 2);
			world.gameEvent(GameEvent.BLOCK_CHANGE, pos);
		}
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(AGE) < 3;
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, boolean bl) {
		return blockState.getValue(AGE) < 3;
	}

	@Override
	public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	public boolean canGrowPlace(LevelReader world, BlockPos blockPos, BlockState blockState) {
		return world.getRawBrightness(blockPos, 0) > 9;
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader world, BlockPos blockPos) {
		return canGrowPlace(world, blockPos, blockState) && this.mayPlaceOn(world.getBlockState(blockPos.below()),
				world, blockPos);
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
		return floor.isSolidRender(world, pos);
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return new ItemStack(this.type.getSeeds());
	}

	public ItemStack getGrapeType() {
		return new ItemStack(this.type.getFruit());
	}

	@Override
	public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
		int i = Math.min(3, state.getValue(AGE) + 1);
		world.setBlock(pos, state.setValue(AGE, i), 2);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}

