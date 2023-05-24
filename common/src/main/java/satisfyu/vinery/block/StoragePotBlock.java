package satisfyu.vinery.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class StoragePotBlock extends BarrelBlock {
	private final SoundEvent openSound;

	private final SoundEvent closeSound;

	public StoragePotBlock(BlockBehaviour.Properties settings, SoundEvent openSound, SoundEvent closeSound) {
		super(settings);
		this.openSound = openSound;
		this.closeSound = closeSound;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.or(shape, Shapes.box(0.9375, 0, 0, 1, 0.625, 1));
		shape = Shapes.or(shape, Shapes.box(0, 0, 0, 0.0625, 0.625, 1));
		shape = Shapes.or(shape, Shapes.box(0.0625, 0, 0, 0.9375, 0.625, 0.0625));
		shape = Shapes.or(shape, Shapes.box(0.0625, 0, 0.9375, 0.9375, 0.625, 1));
		shape = Shapes.or(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375));
		return shape;
	}

	protected Component getContainerName() {
		return new TranslatableComponent("container.storagepot");
	}

	public void playSound(Level world, BlockPos pos, boolean open) {
		world.playSound(null, pos, open ? openSound : closeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
	}

	public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
		if (Screen.hasShiftDown()) {
			tooltip.add(new TranslatableComponent("block.vinery.storage.tooltip.shift").withStyle(
					ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC));
		}
	}
}



