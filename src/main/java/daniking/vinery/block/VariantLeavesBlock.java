package daniking.vinery.block;

import com.terraformersmc.terraform.leaves.block.TerraformLeavesBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import org.jetbrains.annotations.Nullable;

public class VariantLeavesBlock extends LeavesBlock {
    public static final IntProperty VARIANT = IntProperty.of("variant", 0, 1);

    public VariantLeavesBlock() {
        super(Block.Settings.copy(Blocks.OAK_LEAVES).nonOpaque());
        this.setDefaultState(this.stateManager.getDefaultState().with(DISTANCE, 7).with(PERSISTENT, false).with(VARIANT, 0));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(VARIANT, ctx.getPlayer() != null ? ctx.getPlayer().getRandom().nextInt(2) : 0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(VARIANT);
    }
}
