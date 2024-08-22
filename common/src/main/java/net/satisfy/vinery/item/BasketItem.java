package net.satisfy.vinery.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class BasketItem extends BlockItem {
    public BasketItem(Block block, Properties settings) {
        super(block, new Properties().stacksTo(1));
    }

    // No ported
    private static Stream<ItemStack> getContents(ItemStack itemStack) {
        CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag compoundTag = customData.copyTag();

        CompoundTag compoundTag2 = compoundTag.getCompound("BlockEntityTag");
        ListTag listTag = compoundTag2.getList("Items", 10);
        Stream<Tag> var10000 = listTag.stream();
        Objects.requireNonNull(CompoundTag.class);
        //return var10000.map(CompoundTag.class::cast).map((compoundTagx) -> ItemStack.parse());
        return null;
    }

//    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
//        NonNullList<ItemStack> nonNullList = NonNullList.create();
//        Stream<ItemStack> var10000 = getContents(itemStack);
//        Objects.requireNonNull(nonNullList);
//        var10000.forEach(nonNullList::add);
//        return Optional.of(new BundleTooltip(new BundleContents(nonNullList)));
//    }
}