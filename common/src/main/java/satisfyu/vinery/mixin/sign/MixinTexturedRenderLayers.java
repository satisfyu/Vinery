package satisfyu.vinery.mixin.sign;

import java.util.function.Consumer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sheets.class)
public class MixinTexturedRenderLayers {
	@Inject(method = "addDefaultTextures", at = @At("RETURN"))
	private static void injectTerrestriaSigns(Consumer<Material> consumer, CallbackInfo info) {
		for(Material identifier: SpriteIdentifierRegistry.INSTANCE.getIdentifiers()) {
			consumer.accept(identifier);
		}
	}
}
