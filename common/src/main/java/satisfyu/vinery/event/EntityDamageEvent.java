package satisfyu.vinery.event;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import satisfyu.vinery.registry.MobEffectRegistry;

public class EntityDamageEvent implements EntityEvent.LivingHurt {
    @Override
    public EventResult hurt(LivingEntity entity, DamageSource source, float amount) {
        MobEffectInstance effectInstance = entity.getEffect(MobEffectRegistry.SHIRAAZ_EFFECT.get());
        if (effectInstance != null && source.getEntity() instanceof LivingEntity attacker) {
            float reflectedDamage = amount * 0.5F;
            if (entity != attacker) {
                attacker.hurt(entity.level().damageSources().generic(), reflectedDamage);
                return EventResult.interrupt(false);
            }
        }
        return EventResult.pass();
    }
}