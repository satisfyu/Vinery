package daniking.vinery.mixin;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AbstractCauldronBlock.class)
public interface AbstractCauldronBlockAccessor {
	@Accessor
	Map<Item, CauldronBehavior> getBehaviorMap();
}