package daniking.vinery;

import daniking.vinery.block.GrapeBush;
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

    private final GrapeBush.Type type;

    public GrapeBushSeedItem(Block block, Settings settings, GrapeBush.Type type) {
        super(block, settings);
        this.type = type;
    }

    public GrapeBush.Type getType() {
        return type;
    }
}
