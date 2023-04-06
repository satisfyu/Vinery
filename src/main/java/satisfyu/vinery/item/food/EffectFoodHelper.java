package satisfyu.vinery.item.food;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.List;

public class EffectFoodHelper {
    public static final String STORED_EFFECTS_KEY = "StoredEffects";
    public static final String FOOD_STAGE = "CustomModelData";

    public static void addEffect(ItemStack stack, Pair<StatusEffectInstance, Float> effect) {
        NbtList nbtList = getEffectNbt(stack);
        boolean bl = true;
        int id = StatusEffect.getRawId(effect.getFirst().getEffectType());

        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int idTemp = nbtCompound.getInt("id");
            if (idTemp == id) {
                bl = false;
                break;
            }
        }

        if (bl) {
            nbtList.add(createNbt((short)id, effect));
        }

        stack.getOrCreateNbt().put(STORED_EFFECTS_KEY, nbtList);
    }

    private static NbtList getEffectNbt(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null ? nbtCompound.getList(STORED_EFFECTS_KEY, 10) : new NbtList();
    }

    public static NbtCompound createNbt(short id, Pair<StatusEffectInstance, Float> effect) {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putShort("id", id);
        nbtCompound.putInt("duration", effect.getFirst().getDuration());
        nbtCompound.putInt("amplifier", effect.getFirst().getAmplifier());
        nbtCompound.putFloat("chance", effect.getSecond());
        return nbtCompound;
    }

    public static List<Pair<StatusEffectInstance, Float>> getEffects(ItemStack stack) {
        if (stack.getItem() instanceof EffectFood) {
            return fromNbt(getEffectNbt(stack));
        }
        FoodComponent foodComponent = stack.getItem().getFoodComponent();
        assert foodComponent != null;
        return foodComponent.getStatusEffects();
    }

    public static List<Pair<StatusEffectInstance, Float>> fromNbt(NbtList list) {
        List<Pair<StatusEffectInstance, Float>> effects = Lists.newArrayList();
        for(int i = 0; i < list.size(); ++i) {
            NbtCompound nbtCompound = list.getCompound(i);
            StatusEffect effect = StatusEffect.byRawId(nbtCompound.getShort("id"));
            assert effect != null;
            effects.add(new Pair<>(new StatusEffectInstance(effect, nbtCompound.getInt("duration"), nbtCompound.getInt("amplifier")), nbtCompound.getFloat("chance")));
        }

        return effects;
    }

    public static ItemStack setStage(ItemStack stack, int stage) {
        NbtCompound nbtCompound = stack.getNbt() != null ? stack.getNbt() : new NbtCompound();
        nbtCompound.putInt(FOOD_STAGE, stage);
        stack.setNbt(nbtCompound);
        return stack;
    }

    public static int getStage(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null ? nbtCompound.getInt(FOOD_STAGE) : 0;
    }
}
