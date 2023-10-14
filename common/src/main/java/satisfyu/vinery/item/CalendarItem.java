package satisfyu.vinery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.config.VineryConfig;
import satisfyu.vinery.util.WineYears;

import java.util.List;

public class CalendarItem extends BlockItem {
    public CalendarItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        if(level.isClientSide() && entity instanceof Player player && player.isHolding(this)){
            player.displayClientMessage(getTime(level), true);
        }
    }

    public static Component getTime(Level level){
        long hour = (level.getDayTime() / 1000) % 24;
        long day = (level.getDayTime() / 24000) % VineryConfig.DEFAULT.getConfig().yearLengthInDays();
        return Component.literal(hour + "h / " + day + "d / " + WineYears.getYear(level) + "y").withStyle(ChatFormatting.YELLOW);
    }
}