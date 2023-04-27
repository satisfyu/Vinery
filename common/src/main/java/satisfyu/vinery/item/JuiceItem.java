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
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import satisfyu.vinery.registry.ObjectRegistry;


public class JuiceItem extends Item {
    private static final int DRINK_DURATION = 40;

    public JuiceItem(Item.Properties arg) {
        super(arg);
    }

    public ItemStack finishUsingItem(ItemStack arg, Level arg2, LivingEntity arg3) {
        super.finishUsingItem(arg, arg2, arg3);
        if (arg3 instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, arg);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!arg2.isClientSide) {
            arg3.removeEffect(MobEffects.POISON);
        }

        if (arg.isEmpty()) {
            return new ItemStack(ObjectRegistry.WINE_BOTTLE.get(), 1);
        } else {
            if (arg3 instanceof Player && !((Player)arg3).getAbilities().instabuild) {
                ItemStack itemStack = new ItemStack(ObjectRegistry.WINE_BOTTLE.get(), 1);
                Player player = (Player)arg3;
                if (!player.getInventory().add(itemStack)) {
                    player.drop(itemStack, false);
                }
            }
            arg.shrink(1);
            return arg;
        }
    }

    public int getUseDuration(ItemStack arg) {
        return 40;
    }

    public UseAnim getUseAnimation(ItemStack arg) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public InteractionResultHolder<ItemStack> use(Level arg, Player arg2, InteractionHand arg3) {
        return ItemUtils.startUsingInstantly(arg, arg2, arg3);
    }
}