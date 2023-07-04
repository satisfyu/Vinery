package satisfyu.vinery.effect;


import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.client.shader.Shader;
import satisfyu.vinery.registry.VineryEffects;
import satisfyu.vinery.util.ShaderUtils;

public class TrippyEffect extends MobEffect {
    public TrippyEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB80070);
    }

    private static int delay = 0;

    private static int maxDur = 0;

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int i) {
        if (livingEntity.level().isClientSide() && livingEntity instanceof Player) {
            int duration = livingEntity.getEffect(VineryEffects.TRIPPY.get()).getDuration();
            if(duration > maxDur) maxDur = duration;

            if(duration > 5){
                double d = exponentialDecrease(duration, maxDur, 1.2);
                Vinery.LOGGER.error("Scaled: " + d);
                Vinery.LOGGER.error("D: " + duration);

                int requiredD = (int) ((maxDur / d) * (1 - i * 0.1)); // effects change slower and slower

                delay++;
                if(requiredD < delay){
                    delay = 0;

                    ShaderUtils.load(ShaderUtils.getRandomShader());
                    if (ShaderUtils.shader != null) {
                        Minecraft client = Minecraft.getInstance();
                        ShaderUtils.shader.resize(client.getWindow().getWidth(), client.getWindow().getHeight());
                    }
                }
            }
            else {
                maxDur = 0;
                ShaderUtils.load(ShaderUtils.getShader(Shader.NONE));
                if (ShaderUtils.shader != null) {
                    Minecraft client = Minecraft.getInstance();
                    ShaderUtils.shader.resize(client.getWindow().getWidth(), client.getWindow().getHeight());
                }
            }

        }
        super.applyEffectTick(livingEntity, i);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int i) {
        super.removeAttributeModifiers(livingEntity, attributeMap, i);
    }

    public static double exponentialDecrease(double inputValue, double baseValue, double decadeMultiplier) {
        double step = 1 / baseValue;
        double scaled = inputValue * step;
        return Math.pow(baseValue, Math.pow(scaled, 1 / Math.max(1, decadeMultiplier)));
    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}


