package satisfyu.vinery.mixin;

import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageTypes.class)
public abstract class RegisterDamageSourceMixin{

	private static final RegistryKey<DamageType> STOVE_BLOCK = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("stove_block"));

	@Inject(method = "bootstrap", at = @At(value = "HEAD"))
	private static void register(Registerable<DamageType> damageTypeRegisterable, CallbackInfo ci) {
		damageTypeRegisterable.register(STOVE_BLOCK, new DamageType("stoveBlock", 0.1f, DamageEffects.BURNING));
	}
}