package daniking.vinery;

import daniking.vinery.block.GrapeBush;
import daniking.vinery.util.GrapevineType;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class GrapeBushSeedItem extends AliasedBlockItem {

    private final GrapevineType type;

    public GrapeBushSeedItem(Block block, Settings settings, GrapevineType type) {
        super(block, settings);
        this.type = type;
    }

    public GrapevineType getType() {
        return type;
    }
}
