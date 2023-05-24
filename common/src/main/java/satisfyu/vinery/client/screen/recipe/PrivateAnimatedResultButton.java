package satisfyu.vinery.client.screen.recipe;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import satisfyu.vinery.client.recipebook.AbstractPrivateRecipeScreenHandler;

import java.util.List;

public class PrivateAnimatedResultButton extends AbstractWidget {
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/recipe_book.png");

	private AbstractPrivateRecipeScreenHandler craftingScreenHandler;

	private Recipe<?> recipe;

	private float bounce;

	public PrivateAnimatedResultButton() {
		super(0, 0, 25, 25, Component.nullToEmpty(""));
	}

	public void showResultCollection(Recipe<?> recipe, AbstractPrivateRecipeScreenHandler craftingScreenHandler) {
		this.recipe = recipe;
		this.craftingScreenHandler = craftingScreenHandler;
	}

	public Recipe<?> getRecipe() {
		return this.recipe;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void renderButton(PoseStack matrices, int mouseX, int mouseY, float delta) {
		Minecraft minecraftClient = Minecraft.getInstance();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
		int i = 29;
		if (!craftingScreenHandler.hasIngredient(this.recipe)) {
			i += 25;
		}
		int j = 206;
		boolean bl = this.bounce > 0.0F;
		PoseStack matrixStack = RenderSystem.getModelViewStack();
		if (bl) {
			float f = 1.0F + 0.1F * (float) Math.sin((this.bounce / 15.0F * 3.1415927F));
			matrixStack.pushPose();
			matrixStack.translate((this.x + 8), (this.y + 12), 0.0);
			matrixStack.scale(f, f, 1.0F);
			matrixStack.translate((-(this.x + 8)), (-(this.y + 12)), 0.0);
			RenderSystem.applyModelViewMatrix();
			this.bounce -= delta;
		}
		this.blit(matrices, this.x, this.y, i, j, this.width, this.height);
		Recipe<?> recipe = this.getResult();
		int k = 4;
		minecraftClient.getItemRenderer().renderAndDecorateFakeItem(recipe.getResultItem(), this.x + k, this.y + k);
		if (bl) {
			matrixStack.popPose();
			RenderSystem.applyModelViewMatrix();
		}
	}

	private Recipe<?> getResult() {
		return this.recipe;
	}

	public boolean hasResult() {
		return this.getResult() != null;
	}

	public Recipe<?> currentRecipe() {
		return this.getResult();
	}

	@Override
	protected boolean isValidClickButton(int button) {
		return button == 0 || button == 1;
	}

	public List<Component> getTooltip(Screen screen) {
		ItemStack itemStack = this.getResult().getResultItem();
		return Lists.newArrayList(screen.getTooltipFromItem(itemStack));
	}

	@Override
	public void updateNarration(NarrationElementOutput builder) {
		ItemStack itemStack = this.getResult().getResultItem();
		builder.add(NarratedElementType.TITLE, new TranslatableComponent("narration.recipe", itemStack.getHoverName()));
		builder.add(NarratedElementType.USAGE, new TranslatableComponent("narration.button.usage.hovered"));
	}

	@Override
	public int getWidth() {
		return 25;
	}
}