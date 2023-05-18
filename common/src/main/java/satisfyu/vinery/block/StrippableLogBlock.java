package satisfyu.vinery.block;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;

public class StrippableLogBlock extends RotatedPillarBlock {
    public StrippableLogBlock(Properties properties, Supplier<Block> stripped, MaterialColor wood, MaterialColor bark) {
        super(properties);

        if (stripped != null) {
            AxeItemHooks.addStrippable(this, stripped.get());
        }
    }
}
