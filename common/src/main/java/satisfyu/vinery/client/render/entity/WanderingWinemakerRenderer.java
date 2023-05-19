package satisfyu.vinery.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WanderingTraderRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.WanderingTrader;
import satisfyu.vinery.VineryIdentifier;
@Environment(EnvType.CLIENT)
public class WanderingWinemakerRenderer extends WanderingTraderRenderer {
	private static final ResourceLocation TEXTURE = new VineryIdentifier("textures/entity/wandering_winemaker.png");
	
	public WanderingWinemakerRenderer(EntityRendererProvider.Context context) {
		super(context);
	}
	
	@Override
	public ResourceLocation getTextureLocation(WanderingTrader entity) {
		return TEXTURE;
	}
}