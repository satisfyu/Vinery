package satisfyu.vinery.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ConsumAndReturnItem extends Item {

    private final int useDuration;
    private final Supplier<Item> returnItem;

    private final boolean removePoison;
    public ConsumAndReturnItem(Properties properties, int useDuration, Supplier<Item> returnItem, boolean removePoison) {
        super(properties);
        this.useDuration = useDuration;
        this.returnItem = returnItem;
        this.removePoison = removePoison;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(itemStack, level, livingEntity);
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        if (!level.isClientSide && removePoison) {
            livingEntity.removeEffect(MobEffects.POISON);
        }
        if (itemStack.isEmpty()) {
            return new ItemStack(returnItem.get());
        }
        if (livingEntity instanceof Player player && !((Player)livingEntity).getAbilities().instabuild) {
            ItemStack itemStack2 = new ItemStack(returnItem.get());
            if (!player.getInventory().add(itemStack2)) {
                player.drop(itemStack2, false);
            }
        }
        return itemStack;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return useDuration;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }
}
