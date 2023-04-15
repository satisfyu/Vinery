package satisfyu.vinery.mixin;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTypes.class)
public abstract class RegisterDamageSourceMixin{

	private static final ResourceKey<DamageType> STOVE_BLOCK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("stove_block"));

	@Inject(method = "bootstrap", at = @At(value = "HEAD"))
	private static void register(BootstapContext<DamageType> damageTypeRegisterable, CallbackInfo ci) {
		damageTypeRegisterable.register(STOVE_BLOCK, new DamageType("stoveBlock", 0.1f, DamageEffects.BURNING));
	}
}