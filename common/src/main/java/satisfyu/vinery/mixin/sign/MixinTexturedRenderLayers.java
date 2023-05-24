package satisfyu.vinery.mixin.sign;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.util.sign.SpriteIdentifierRegistry;

import java.util.function.Consumer;

@Mixin(Sheets.class)
public class MixinTexturedRenderLayers {
	@Inject(method = "getAllMaterials", at = @At("RETURN"))
	private static void injectTerrestriaSigns(Consumer<Material> consumer, CallbackInfo info) {
		for (Material identifier : SpriteIdentifierRegistry.INSTANCE.getIdentifiers()) {
			consumer.accept(identifier);
		}
	}
}
