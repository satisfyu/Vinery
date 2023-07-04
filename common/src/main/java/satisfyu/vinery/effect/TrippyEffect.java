package satisfyu.vinery.effect;


import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.client.shader.Shader;
import satisfyu.vinery.registry.VineryEffects;
import satisfyu.vinery.util.ShaderUtils;

public class TrippyEffect extends MobEffect {
    public TrippyEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB80070);
    }

    public static int delay = 0;

    public static int maxDur = 0;

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int i) {
        if (livingEntity.level().isClientSide() && livingEntity instanceof Player) {
            int duration = livingEntity.getEffect(VineryEffects.TRIPPY.get()).getDuration();
            if(duration > maxDur) maxDur = duration;
            int d = (int) ((maxDur / duration) * 2 * (1 - i * 0.1));
            Vinery.LOGGER.error(duration);

            delay++;
            if(d < delay && duration > 5){
                delay = 0;

                ShaderUtils.load(ShaderUtils.getRandomShader());
                if (ShaderUtils.shader != null) {
                    Minecraft client = Minecraft.getInstance();
                    ShaderUtils.shader.resize(client.getWindow().getWidth(), client.getWindow().getHeight());
                }
            }



            if(duration <= 5){
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
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}


