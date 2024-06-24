package net.satisfy.vinery.item;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class BasketItem extends BlockItem {
    public BasketItem(Block block, Properties settings) {
        super(block, new Properties().stacksTo(1));
    }

    private static Stream<ItemStack> getContents(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag == null) {
            return Stream.empty();
        } else {
            CompoundTag compoundTag2 = compoundTag.getCompound("BlockEntityTag");
            ListTag listTag = compoundTag2.getList("Items", 10);
            Stream<Tag> var10000 = listTag.stream();
            Objects.requireNonNull(CompoundTag.class);
            return var10000.map(CompoundTag.class::cast).map(ItemStack::of);
        }
    }

    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
        NonNullList<ItemStack> nonNullList = NonNullList.create();
        Stream<ItemStack> var10000 = getContents(itemStack);
        Objects.requireNonNull(nonNullList);
        var10000.forEach(nonNullList::add);
        return Optional.of(new BundleTooltip(nonNullList, 64));
    }
}