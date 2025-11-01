package net.satisfy.vinery.core.event;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.satisfy.vinery.core.registry.MobEffectRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class EventHandler {
    private static final Random random = new Random();

    public static EventResult onAttack(Player player, Level level, Entity target, InteractionHand hand, @Nullable EntityHitResult result) {
        if (player.hasEffect(MobEffectRegistry.getHolder(MobEffectRegistry.PARTY_EFFECT))) {
            if (target instanceof LivingEntity entity) {
                int color = random.nextInt(0xFFFFFF);

                ItemStack fireworkStack = new ItemStack(Items.FIREWORK_ROCKET);
                CompoundTag fireworkNbt = new CompoundTag();
                ListTag explosions = new ListTag();
                CompoundTag explosion = new CompoundTag();

                explosion.putIntArray("Colors", new int[]{color});
                explosion.putByte("Type", (byte) 0);
                explosions.add(explosion);

                CompoundTag fireworksTag = new CompoundTag();
                fireworksTag.put("Explosions", explosions);
                fireworksTag.putByte("Flight", (byte) 0);

                fireworkStack.set(DataComponents.FIREWORKS, new net.minecraft.world.item.component.Fireworks(0, explosions.stream()
                        .map(tag -> new net.minecraft.world.item.component.FireworkExplosion(
                                net.minecraft.world.item.component.FireworkExplosion.Shape.SMALL_BALL,
                                IntList.of(color),
                                IntList.of(),
                                false,
                                false
                        ))
                        .toList()));

                FireworkRocketEntity fireworkRocket = new FireworkRocketEntity(level, fireworkStack, entity);
                fireworkRocket.setAirSupply(0);
                level.addFreshEntity(fireworkRocket);

                if (!(target instanceof Player || target instanceof Mob)) {
                    return EventResult.interruptTrue();
                }

                return EventResult.pass();
            }
        }

        return EventResult.pass();
    }


    public static void init() {
        PlayerEvent.ATTACK_ENTITY.register(EventHandler::onAttack);
    }
}
