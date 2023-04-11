package satisfyu.vinery.client.screen.recipe;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeGridAligner;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.client.VineryClient;

import java.util.Iterator;
import java.util.List;

public class PrivateRecipeAlternativesWidget extends DrawableHelper implements Drawable, Element {
    static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/recipe_book.png");
    private final List<CustomAlternativeButtonWidget> alternativeButtons = Lists.newArrayList();
    private boolean visible;
    private int buttonX;
    private int buttonY;
    private MinecraftClient client;
    private Recipe<?> recipe;
    @Nullable
    private Recipe<?> lastClickedRecipe;
    float time;

    public PrivateRecipeAlternativesWidget() {
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public Recipe<?> getResults() {
        return this.recipe;
    }

    @Nullable
    public Recipe<?> getLastClickedRecipe() {
        return this.lastClickedRecipe;
    }

    public void showAlternativesForResult(MinecraftClient client, Recipe<?> recipe, int buttonX, int buttonY, int areaCenterX, int areaCenterY, float delta) {
        this.client = client;
        this.recipe = recipe;

        boolean bl = VineryClient.rememberedCraftableToggle;

        int k = 4;
        int l = (int)Math.ceil(((float)1 / (float)k));

        this.buttonX = buttonX;
        this.buttonY = buttonY;

        float f = (float)(this.buttonX + 25);
        float g = (float)(areaCenterX + 50);
        if (f > g) {
            this.buttonX = (int)((float)this.buttonX - delta * (float)((int)((f - g) / delta)));
        }

        float h = (float)(this.buttonY + l * 25);
        float n = (float)(areaCenterY + 50);
        if (h > n) {
            this.buttonY = (int)((float)this.buttonY - delta * (float)MathHelper.ceil((h - n) / delta));
        }

        float o = (float)this.buttonY;
        float p = (float)(areaCenterY - 100);
        if (o < p) {
            this.buttonY = (int)((float)this.buttonY - delta * (float)MathHelper.ceil((o - p) / delta));
        }

        this.visible = true;
        this.alternativeButtons.clear();

        this.alternativeButtons.add(new CustomAlternativeButtonWidget(this.buttonX + 6, this.buttonY+ 6, this.recipe, bl));

        this.lastClickedRecipe = null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return false;
        } else {
            Iterator<CustomAlternativeButtonWidget> var6 = this.alternativeButtons.iterator();

            CustomAlternativeButtonWidget alternativeButtonWidget;
            do {
                if (!var6.hasNext()) {
                    return false;
                }

                alternativeButtonWidget = var6.next();
            } while(!alternativeButtonWidget.mouseClicked(mouseX, mouseY, button));

            this.lastClickedRecipe = alternativeButtonWidget.recipe;
            return true;
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            this.time += delta;
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
            matrices.push();
            matrices.translate(0.0, 0.0, 170.0);
            int i = this.alternativeButtons.size() <= 16 ? 4 : 5;
            int j = Math.min(this.alternativeButtons.size(), i);
            int k = MathHelper.ceil((float)this.alternativeButtons.size() / (float)i);
            this.renderGrid(matrices, j, k, 24, 4, 82, 208);
            RenderSystem.disableBlend();

            for (CustomAlternativeButtonWidget alternativeButtonWidget : this.alternativeButtons) {
                alternativeButtonWidget.render(matrices, mouseX, mouseY, delta);
            }

            matrices.pop();
        }
    }

    private void renderGrid(MatrixStack matrices, int i, int j, int k, int l, int m, int n) {
        this.drawTexture(matrices, this.buttonX, this.buttonY, m, n, l, l);
        this.drawTexture(matrices, this.buttonX + l * 2 + i * k, this.buttonY, m + k + l, n, l, l);
        this.drawTexture(matrices, this.buttonX, this.buttonY + l * 2 + j * k, m, n + k + l, l, l);
        this.drawTexture(matrices, this.buttonX + l * 2 + i * k, this.buttonY + l * 2 + j * k, m + k + l, n + k + l, l, l);

        for(int o = 0; o < i; ++o) {
            this.drawTexture(matrices, this.buttonX + l + o * k, this.buttonY, m + l, n, k, l);
            this.drawTexture(matrices, this.buttonX + l + (o + 1) * k, this.buttonY, m + l, n, l, l);

            for(int p = 0; p < j; ++p) {
                if (o == 0) {
                    this.drawTexture(matrices, this.buttonX, this.buttonY + l + p * k, m, n + l, l, k);
                    this.drawTexture(matrices, this.buttonX, this.buttonY + l + (p + 1) * k, m, n + l, l, l);
                }

                this.drawTexture(matrices, this.buttonX + l + o * k, this.buttonY + l + p * k, m + l, n + l, k, k);
                this.drawTexture(matrices, this.buttonX + l + (o + 1) * k, this.buttonY + l + p * k, m + l, n + l, l, k);
                this.drawTexture(matrices, this.buttonX + l + o * k, this.buttonY + l + (p + 1) * k, m + l, n + l, k, l);
                this.drawTexture(matrices, this.buttonX + l + (o + 1) * k - 1, this.buttonY + l + (p + 1) * k - 1, m + l, n + l, l + 1, l + 1);
                if (o == i - 1) {
                    this.drawTexture(matrices, this.buttonX + l * 2 + i * k, this.buttonY + l + p * k, m + k + l, n + l, l, k);
                    this.drawTexture(matrices, this.buttonX + l * 2 + i * k, this.buttonY + l + (p + 1) * k, m + k + l, n + l, l, l);
                }
            }

            this.drawTexture(matrices, this.buttonX + l + o * k, this.buttonY + l * 2 + j * k, m + l, n + k + l, k, l);
            this.drawTexture(matrices, this.buttonX + l + (o + 1) * k, this.buttonY + l * 2 + j * k, m + l, n + k + l, l, l);
        }

    }

    @Environment(EnvType.CLIENT)
    private class CustomAlternativeButtonWidget extends ClickableWidget implements RecipeGridAligner<Ingredient> {
        final Recipe<?> recipe;
        private final boolean craftable;
        protected final List<InputSlot> slots = Lists.newArrayList();

        public CustomAlternativeButtonWidget(int x, int y, Recipe<?> recipe, boolean craftable) {
            super(x, y, 200, 20, ScreenTexts.EMPTY);
            this.width = 24;
            this.height = 24;
            this.recipe = recipe;
            this.craftable = craftable;
            this.alignRecipe(recipe);
        }

        protected void alignRecipe(Recipe<?> recipe) {
            this.alignRecipeToGrid(3, 3, -1, recipe, recipe.getIngredients().iterator(), 0);
        }

        public void appendNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }

        public void acceptAlignedInput(Iterator<Ingredient> inputs, int slot, int amount, int gridX, int gridY) {
            ItemStack[] itemStacks = inputs.next().getMatchingStacks();
            if (itemStacks.length != 0) {
                this.slots.add(new InputSlot(3 + gridY * 7, 3 + gridX * 7, itemStacks));
            }

        }

        public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
            int i = 152;
            if (!this.craftable) {
                i += 26;
            }

            int j = 78;
            if (this.isHovered()) {
                j += 26;
            }

            this.drawTexture(matrices, this.x, this.y, i, j, this.width, this.height);
            MatrixStack matrixStack = RenderSystem.getModelViewStack();
            matrixStack.push();
            matrixStack.translate( (this.x + 2), (this.y + 2), 125.0);

            for (InputSlot inputSlot : this.slots) {
                matrixStack.push();
                matrixStack.translate(inputSlot.y, inputSlot.x, 0.0);
                matrixStack.scale(0.375F, 0.375F, 1.0F);
                matrixStack.translate(-8.0, -8.0, 0.0);
                RenderSystem.applyModelViewMatrix();
                PrivateRecipeAlternativesWidget.this.client.getItemRenderer().renderInGuiWithOverrides(inputSlot.stacks[MathHelper.floor(PrivateRecipeAlternativesWidget.this.time / 30.0F) % inputSlot.stacks.length], 0, 0);
                matrixStack.pop();
            }

            matrixStack.pop();
            RenderSystem.applyModelViewMatrix();
        }

        @Environment(EnvType.CLIENT)
        protected class InputSlot {
            public final ItemStack[] stacks;
            public final int y;
            public final int x;

            public InputSlot(int y, int x, ItemStack[] stacks) {
                this.y = y;
                this.x = x;
                this.stacks = stacks;
            }
        }
    }
}
