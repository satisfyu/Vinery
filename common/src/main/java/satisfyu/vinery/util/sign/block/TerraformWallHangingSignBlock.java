package satisfyu.vinery.util.sign.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import satisfyu.vinery.util.sign.TerraformHangingSign;

public class TerraformWallHangingSignBlock extends WallHangingSignBlock implements TerraformHangingSign {
	private final ResourceLocation texture;
	private final ResourceLocation guiTexture;

	public TerraformWallHangingSignBlock(ResourceLocation texture, ResourceLocation guiTexture, Properties settings) {
		super(settings, WoodType.OAK);
		this.texture = texture;
		this.guiTexture = guiTexture;
	}

	@Override
	public ResourceLocation getTexture() {
		return texture;
	}

	@Override
	public ResourceLocation getGuiTexture() {
		return guiTexture;
	}
}
