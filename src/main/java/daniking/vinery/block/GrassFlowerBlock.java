package daniking.vinery.block;

import daniking.vinery.registry.ObjectRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.random.Random;

public class GrassFlowerBlock extends GrassBlock {

    public enum Type {
        RED,
        PINK,
        WHITE,
    }

    private final Type type;
    public GrassFlowerBlock(Settings settings, Type type) {
        super(settings);
        this.type = type;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        final Random n = ctx.getWorld().getRandom();
        BlockState state = this.getDefaultState();
        switch (this.type) {
            case RED -> state = n.nextDouble() < 0.5D ? ObjectRegistry.RED_GRASS_FLOWER.getDefaultState() : ObjectRegistry.RED_GRASS_FLOWER_VARIANT_B.getDefaultState();
            case PINK -> state = n.nextDouble() < 0.5D ? ObjectRegistry.PINK_GRASS_FLOWER.getDefaultState() : ObjectRegistry.PINK_GRASS_FLOWER_VARIANT_B.getDefaultState();
            case WHITE -> state = ObjectRegistry.WHITE_GRASS_FLOWER.getDefaultState();
        }
        return state;
    }

    public Type getType() {
        return type;
    }
}
