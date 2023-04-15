package satisfyu.vinery.item.food;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

public class EffectFoodHelper {
    public static final String STORED_EFFECTS_KEY = "StoredEffects";
    public static final String FOOD_STAGE = "CustomModelData";

    public static void addEffect(ItemStack stack, Pair<MobEffectInstance, Float> effect) {
        ListTag nbtList = getEffectNbt(stack);
        boolean bl = true;
        int id = MobEffect.getId(effect.getFirst().getEffect());

        for(int i = 0; i < nbtList.size(); ++i) {
            CompoundTag nbtCompound = nbtList.getCompound(i);
            int idTemp = nbtCompound.getInt("id");
            if (idTemp == id) {
                bl = false;
                break;
            }
        }

        if (bl) {
            nbtList.add(createNbt((short)id, effect));
        }

        stack.getOrCreateTag().put(STORED_EFFECTS_KEY, nbtList);
    }

    private static ListTag getEffectNbt(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTag();
        return nbtCompound != null ? nbtCompound.getList(STORED_EFFECTS_KEY, 10) : new ListTag();
    }

    public static CompoundTag createNbt(short id, Pair<MobEffectInstance, Float> effect) {
        CompoundTag nbtCompound = new CompoundTag();
        nbtCompound.putShort("id", id);
        nbtCompound.putInt("duration", effect.getFirst().getDuration());
        nbtCompound.putInt("amplifier", effect.getFirst().getAmplifier());
        nbtCompound.putFloat("chance", effect.getSecond());
        return nbtCompound;
    }

    public static List<Pair<MobEffectInstance, Float>> getEffects(ItemStack stack) {
        if (stack.getItem() instanceof EffectFood) {
            return fromNbt(getEffectNbt(stack));
        }
        FoodProperties foodComponent = stack.getItem().getFoodProperties();
        assert foodComponent != null;
        return foodComponent.getEffects();
    }

    public static List<Pair<MobEffectInstance, Float>> fromNbt(ListTag list) {
        List<Pair<MobEffectInstance, Float>> effects = Lists.newArrayList();
        for(int i = 0; i < list.size(); ++i) {
            CompoundTag nbtCompound = list.getCompound(i);
            MobEffect effect = MobEffect.byId(nbtCompound.getShort("id"));
            assert effect != null;
            effects.add(new Pair<>(new MobEffectInstance(effect, nbtCompound.getInt("duration"), nbtCompound.getInt("amplifier")), nbtCompound.getFloat("chance")));
        }

        return effects;
    }

    public static ItemStack setStage(ItemStack stack, int stage) {
        CompoundTag nbtCompound = stack.getTag() != null ? stack.getTag() : new CompoundTag();
        nbtCompound.putInt(FOOD_STAGE, stage);
        stack.setTag(nbtCompound);
        return stack;
    }

    public static int getStage(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTag();
        return nbtCompound != null ? nbtCompound.getInt(FOOD_STAGE) : 0;
    }
}
