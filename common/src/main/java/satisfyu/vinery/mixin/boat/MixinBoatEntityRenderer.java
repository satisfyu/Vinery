package satisfyu.vinery.mixin.boat;

import java.util.Map;

import com.mojang.datafixers.util.Pair;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import satisfyu.vinery.util.boat.impl.client.TerraformBoatEntityRenderer;
import satisfyu.vinery.util.boat.impl.entity.TerraformBoatHolder;

@Mixin(BoatRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinBoatEntityRenderer {
	@Redirect(method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
	private Object getTerraformBoatTextureAndModel(Map<Boat.Type, Pair<ResourceLocation, BoatModel>> map, Object type, Boat entity) {
		if (entity instanceof TerraformBoatHolder && (Object) this instanceof TerraformBoatEntityRenderer) {
			return ((TerraformBoatEntityRenderer) (Object) this).getTextureAndModel((TerraformBoatHolder) entity);
		}
		return map.get(type);
	}
}
