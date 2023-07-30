package satisfyu.vinery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.config.VineryConfig;
import satisfyu.vinery.util.WineYears;

import java.util.List;

public class Calendar extends Item {
    public Calendar(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        if(level.isClientSide() && entity instanceof Player player && player.isHolding(this)){
            player.displayClientMessage(getTime(level), true);
        }
    }

    private Component getTime(Level level){
        long hour = (level.getDayTime() / 1000) % 24;
        long day = (level.getDayTime() / 24000) % VineryConfig.DEFAULT.getConfig().yearLengthInDays();
        return Component.literal(hour + "h / " + day + "d / " + WineYears.getYear(level) + "y").withStyle(ChatFormatting.YELLOW);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        if(level == null) return;
        list.add(Component.empty());
        list.add(getTime(level));
    }
}
