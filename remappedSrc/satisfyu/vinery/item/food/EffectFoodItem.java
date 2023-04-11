package satisfyu.vinery.item.food;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static satisfyu.vinery.item.food.EffectFoodHelper.*;

public class EffectFoodItem extends Item implements EffectFood {

    private final int foodStages;

    public EffectFoodItem(Settings settings, int foodStages) {
        super(settings);
        this.foodStages = foodStages;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            List<Pair<StatusEffectInstance, Float>> effects = getEffects(stack);
            for (Pair<StatusEffectInstance, Float> effect : effects) {
                if (effect.getFirst() != null && world.random.nextFloat() < effect.getSecond()) {
                    user.addStatusEffect(new StatusEffectInstance(effect.getFirst()));
                }
            }
        }

        int slot = -1;
        PlayerInventory playerInventory = null;
        if (user instanceof PlayerEntity player && !player.isCreative()) {
            playerInventory = player.getInventory();
            slot = playerInventory.indexOf(stack);
        }
        ItemStack returnStack =  super.finishUsing(stack, world, user);
        int stage = getStage(stack);
        if (playerInventory != null && stage < foodStages) {
            ItemStack itemStack = setStage(new ItemStack(this), stage + 1);
            if (playerInventory.getStack(slot).isEmpty()) {
                playerInventory.insertStack(slot, itemStack);
            }
            else {
                slot = playerInventory.getOccupiedSlotWithRoomForStack(itemStack);
                playerInventory.insertStack(slot, itemStack);
            }
        }
        return returnStack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        List<Pair<StatusEffectInstance, Float>> effects = getEffects(stack);
        if (effects.isEmpty()) {
            tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
        } else {
            for (Pair<StatusEffectInstance, Float> statusEffectInstance : effects) {
                MutableText mutableText = Text.translatable(statusEffectInstance.getFirst().getTranslationKey());
                StatusEffect statusEffect = statusEffectInstance.getFirst().getEffectType();

                if (statusEffectInstance.getFirst().getDuration() > 20) {
                    mutableText = Text.translatable(
                            "potion.withDuration",
                            mutableText, StatusEffectUtil.durationToString(statusEffectInstance.getFirst(), statusEffectInstance.getSecond()));
                }

                tooltip.add(mutableText.formatted(statusEffect.getCategory().getFormatting()));
            }
        }
    }
}
