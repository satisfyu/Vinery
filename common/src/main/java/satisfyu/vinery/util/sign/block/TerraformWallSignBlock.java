package satisfyu.vinery.util.sign.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import satisfyu.vinery.util.sign.TerraformSign;

public class TerraformWallSignBlock extends WallSignBlock implements TerraformSign {
	private final ResourceLocation texture;

	public TerraformWallSignBlock(ResourceLocation texture, Properties settings) {
		super(settings, WoodType.OAK);
		this.texture = texture;
	}

	@Override
	public ResourceLocation getTexture() {
		return texture;
	}
}
