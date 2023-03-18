package satisfyu.vinery.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.registry.VinerySoundEvents;

import java.util.List;

public class RottenCherryItem extends Item {

    public RottenCherryItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);

        if (!world.isClient) {
            Entity entity = getEntityHitResult(world, playerEntity);
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                if (livingEntity.isAlive()) {
                    // Poison the entity
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200));
                }
            }
            world.playSoundFromEntity(null, playerEntity, VinerySoundEvents.BLOCK_GRAPEVINE_POT_SQUEEZE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack);
    }

    private Entity getEntityHitResult(World world, PlayerEntity playerEntity) {
        HitResult hitResult = playerEntity.raycast(5.0D, 0.0F, false);
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            Entity entity = entityHitResult.getEntity();
            return entity;
        }
        return null;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(  "item.vinery.rottencherry.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
    }
}