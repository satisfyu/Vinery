package daniking.vinery.block;

import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class StoragePotBlock extends BarrelBlock {

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.9375, 0, 0, 1, 0.625, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 0.0625, 0.625, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0, 0.9375, 0.625, 0.0625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.9375, 0.9375, 0.625, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375));

        return shape;
    }


    protected Text getContainerName() {
        return Text.translatable("container.storagepot");
    }

    private final SoundEvent openSound;
    private final SoundEvent closeSound;


    public StoragePotBlock(AbstractBlock.Settings settings, SoundEvent openSound, SoundEvent closeSound) {
        super(settings);
        this.openSound = openSound;
        this.closeSound = closeSound;
    }


        public void playSound(World world, BlockPos pos, boolean open) {
            world.playSound(null, pos, open ? openSound : closeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }

        public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("block.vinery.storage.tooltip.shift").formatted(Formatting.LIGHT_PURPLE, Formatting.ITALIC));}

        }
    }



