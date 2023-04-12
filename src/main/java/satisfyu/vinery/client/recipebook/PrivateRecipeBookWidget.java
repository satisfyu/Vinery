package satisfyu.vinery.client.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.recipebook.RecipeDisplayListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.client.VineryClient;
import satisfyu.vinery.client.screen.recipe.PrivateRecipeBookRecipeArea;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public abstract class  PrivateRecipeBookWidget extends DrawableHelper implements RecipeGridAligner<Ingredient>, Drawable, Element, RecipeDisplayListener {
    public static final Identifier TEXTURE = new Identifier("textures/gui/recipe_book.png");
    private static final Text SEARCH_HINT_TEXT;
    private static final Text TOGGLE_CRAFTABLE_RECIPES_TEXT;
    private static final Text TOGGLE_ALL_RECIPES_TEXT;
    protected final PrivateRecipeBookGhostSlots ghostSlots = new PrivateRecipeBookGhostSlots();
    private final List<PrivateRecipeGroupButtonWidget> tabButtons = Lists.newArrayList();
    @Nullable
    private PrivateRecipeGroupButtonWidget currentTab;
    protected ToggleButtonWidget toggleCraftableButton;
    protected AbstractPrivateRecipeScreenHandler screenHandler;
    @Nullable
    private TextFieldWidget searchField;
    private int leftOffset;
    private int parentWidth;
    private int parentHeight;

    protected MinecraftClient client;
    private String searchText = "";
    private final PrivateRecipeBookRecipeArea recipesArea = new PrivateRecipeBookRecipeArea();
    private final RecipeMatcher recipeFinder = new RecipeMatcher();
    private int cachedInvChangeCount;
    private boolean searching;
    private boolean open;
    private boolean narrow;

    public PrivateRecipeBookWidget() {}

    protected abstract RecipeType<? extends Recipe<Inventory>> getRecipeType();
    public abstract void insertRecipe(Recipe<?> recipe) ;
    public abstract void showGhostRecipe(Recipe<?> recipe, List<Slot> slots);

    public void initialize(int parentWidth, int parentHeight, MinecraftClient client, boolean narrow, AbstractPrivateRecipeScreenHandler craftingScreenHandler) {
        this.client = client;
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
        this.screenHandler = craftingScreenHandler;
        this.narrow = narrow;
        assert client.player != null;
        client.player.currentScreenHandler = craftingScreenHandler;
        this.cachedInvChangeCount = client.player.getInventory().getChangeCount();
        this.open = this.isGuiOpen();
        if (this.open) {
            this.reset();
        }
        //client.keyboard.setRepeatEvents(true);
    }

    protected void setOpen(boolean opened) {
        if (opened) {
            this.reset();
        }

        this.open = opened;
        VineryClient.rememberedRecipeBookOpen = opened;
        if (!opened) {
            this.recipesArea.hideAlternates();
        }
    }
    public boolean isOpen() {
        return this.open;
    }
    private boolean isGuiOpen() {
        return VineryClient.rememberedRecipeBookOpen;
    }
    public void toggleOpen() {
        this.setOpen(!this.isOpen());
    }
    /*
    public void close() {
        this.client.keyboard.setRepeatEvents(false);
    }

     */
    private boolean toggleFilteringCraftable() {
        boolean bl = !VineryClient.rememberedCraftableToggle;
        VineryClient.rememberedCraftableToggle = bl;
        return bl;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.isOpen()) {
            matrices.push();
            matrices.translate(0.0, 0.0, 100.0);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, TEXTURE);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int i = (this.parentWidth - 147) / 2 - this.leftOffset;
            int j = (this.parentHeight - 166) / 2;
            this.drawTexture(matrices, i, j, 1, 1, 147, 166);
            if (!this.searchField.isFocused() && this.searchField.getText().isEmpty()) {
                drawTextWithShadow(matrices, this.client.textRenderer, SEARCH_HINT_TEXT, i + 25, j + 14, -1);
            } else {
                this.searchField.render(matrices, mouseX, mouseY, delta);
            }

            for (PrivateRecipeGroupButtonWidget recipeGroupButtonWidget : this.tabButtons) {
                recipeGroupButtonWidget.render(matrices, mouseX, mouseY, delta);
            }

            this.toggleCraftableButton.render(matrices, mouseX, mouseY, delta);
            this.recipesArea.draw(matrices, i, j, mouseX, mouseY, delta);
            matrices.pop();
        }
    }

    public void drawGhostSlots(MatrixStack matrices, int x, int y, boolean bl, float delta) {
        this.ghostSlots.draw(matrices, this.client, x, y, bl, delta);
    }

    public void drawTooltip(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        if (this.isOpen()) {
            this.recipesArea.drawTooltip(matrices, mouseX, mouseY);
            if (this.toggleCraftableButton.isHovered()) {
                Text text = this.getCraftableButtonText();
                if (this.client.currentScreen != null) {
                    this.client.currentScreen.renderTooltip(matrices, text, mouseX, mouseY);
                }
            }

            this.drawGhostSlotTooltip(matrices, x, y, mouseX, mouseY);
        }
    }

    private void drawGhostSlotTooltip(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        ItemStack itemStack = null;

        for(int i = 0; i < this.ghostSlots.getSlotCount(); ++i) {
            PrivateRecipeBookGhostSlots.PrivateGhostInputSlot ghostInputSlot = this.ghostSlots.getSlot(i);
            int j = ghostInputSlot.getX() + x;
            int k = ghostInputSlot.getY() + y;
            if (mouseX >= j && mouseY >= k && mouseX < j + 16 && mouseY < k + 16) {
                itemStack = ghostInputSlot.getCurrentItemStack();
            }
        }

        if (itemStack != null && this.client.currentScreen != null) {
            this.client.currentScreen.renderTooltip(matrices, this.client.currentScreen.getTooltipFromItem(itemStack), mouseX, mouseY);
        }

    }

    public void update() {
        boolean bl = this.isGuiOpen();
        if (this.isOpen() != bl) {
            this.setOpen(bl);
        }

        if (this.isOpen()) {
            if (this.cachedInvChangeCount != this.client.player.getInventory().getChangeCount()) {
                this.refreshInputs();
                this.cachedInvChangeCount = this.client.player.getInventory().getChangeCount();
            }

            this.searchField.tick();
        }
    }

    private void refreshResults(boolean resetCurrentPage) {
        if (this.currentTab == null) return;
        if (this.searchField == null) return;

        List<? extends Recipe<Inventory>> recipes = getResultsForGroup(currentTab.getGroup(), client.world.getRecipeManager().listAllOfType(getRecipeType()));

        String string = this.searchField.getText();

        if (!string.isEmpty()) {
            recipes.removeIf((recipe) -> !recipe.getOutput().getName().getString().toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT)));
        }

        if (VineryClient.rememberedCraftableToggle) {
            recipes.removeIf((recipe) -> !screenHandler.hasIngredient(recipe));
        }

        this.recipesArea.setResults(recipes, resetCurrentPage);
    }

    private <T extends Recipe<Inventory>> List<T> getResultsForGroup(IRecipeBookGroup group, List<T> recipes) {
        List<T> results = Lists.newArrayList();
        for (T recipe : recipes) {
            if (group.fitRecipe(recipe)) {
                results.add(recipe);
            }
        }
        return results;
    }


    private void refreshTabButtons() {
        int i = (this.parentWidth - 147) / 2 - this.leftOffset - 30;
        int j = (this.parentHeight - 166) / 2 + 3;
        
        int l = 0;
        for (PrivateRecipeGroupButtonWidget recipeGroupButtonWidget : this.tabButtons) {
            recipeGroupButtonWidget.visible = true;
            recipeGroupButtonWidget.setPos(i, j + 27 * l++);
        }
    }

    private void refreshInputs() {
        this.recipeFinder.clear();
        this.client.player.getInventory().populateRecipeFinder(this.recipeFinder);
        this.refreshResults(false);
    }

    public void reset() {
        this.leftOffset = this.narrow ? 0 : 86;
        int i = (this.parentWidth - 147) / 2 - this.leftOffset;
        int j = (this.parentHeight - 166) / 2;
        this.recipeFinder.clear();
        this.client.player.getInventory().populateRecipeFinder(this.recipeFinder);
        String string = this.searchField != null ? this.searchField.getText() : "";
        TextRenderer var10003 = this.client.textRenderer;
        int var10004 = i + 25;
        int var10005 = j + 14;
        Objects.requireNonNull(this.client.textRenderer);
        this.searchField = new TextFieldWidget(var10003, var10004, var10005, 80, 9 + 5, Text.translatable("itemGroup.search"));
        this.searchField.setMaxLength(50);
        this.searchField.setDrawsBackground(false);
        this.searchField.setVisible(true);
        this.searchField.setEditableColor(16777215);
        this.searchField.setText(string);
        this.recipesArea.initialize(this.client, i, j, this.screenHandler);
        this.toggleCraftableButton = new ToggleButtonWidget(i + 110, j + 12, 26, 16, VineryClient.rememberedCraftableToggle);
        this.setCraftableButtonTexture();
        this.tabButtons.clear();

        for (IRecipeBookGroup recipeBookGroup : screenHandler.getGroups()) {
            this.tabButtons.add(new PrivateRecipeGroupButtonWidget(recipeBookGroup));
        }

        if (this.currentTab != null) {
            this.currentTab = this.tabButtons.stream().filter((button) -> button.getGroup().equals(this.currentTab.getGroup())
            ).findFirst().orElse(null);
        }

        if (this.currentTab == null) {
            this.currentTab = this.tabButtons.get(0);
        }

        this.currentTab.setToggled(true);
        this.refreshResults(false);
        this.refreshTabButtons();
    }

    public void slotClicked(@Nullable Slot slot) {
        if (slot != null && slot.id < this.screenHandler.getCraftingSlotCount()) {
            this.ghostSlots.reset();
            if (this.isOpen()) {
                this.refreshInputs();
            }
        }

    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.open && !this.client.player.isSpectator()) {
            if (this.recipesArea.mouseClicked(mouseX, mouseY, button, (this.parentWidth - 147) / 2 - this.leftOffset, (this.parentHeight - 166) / 2, 147, 166)) {
                Recipe<?> recipe = this.recipesArea.getLastClickedRecipe();
                Recipe<?> recipeBookRecipe = this.recipesArea.getLastClickedRecipe();
                if (recipe != null) {
                    if (this.currentTab == null) return false;
                    this.ghostSlots.reset();

                    assert recipeBookRecipe != null;
                    if (!screenHandler.hasIngredient(recipe)) {
                        showGhostRecipe(recipe, screenHandler.slots);
                        return false;
                    }

                    this.ghostSlots.reset();
                    insertRecipe(recipe);

                    this.refreshResults(false);
                }

                return true;
            } else {
                assert this.searchField != null;
                if (this.searchField.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                } else if (this.toggleCraftableButton.mouseClicked(mouseX, mouseY, button)) {
                    boolean bl = this.toggleFilteringCraftable();
                    this.toggleCraftableButton.setToggled(bl);
                    VineryClient.rememberedCraftableToggle = bl;
                    this.refreshResults(false);
                    return true;
                }

                Iterator<PrivateRecipeGroupButtonWidget> var6 = this.tabButtons.iterator();

                PrivateRecipeGroupButtonWidget vineryRecipeGroupButtonWidget;
                do {
                    if (!var6.hasNext()) {
                        return false;
                    }

                    vineryRecipeGroupButtonWidget = var6.next();
                } while (!vineryRecipeGroupButtonWidget.mouseClicked(mouseX, mouseY, button));

                if (this.currentTab != vineryRecipeGroupButtonWidget) {
                    if (this.currentTab != null) {
                        this.currentTab.setToggled(false);
                    }

                    this.currentTab = vineryRecipeGroupButtonWidget;
                    this.currentTab.setToggled(true);
                    this.refreshResults(true);
                }
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.searching = false;
        if (this.isOpen() && !this.client.player.isSpectator()) {
            if (keyCode == 256 && !this.isWide()) {
                this.setOpen(false);
                return true;
            } else if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
                this.refreshSearchResults();
                return true;
            } else if (this.searchField.isFocused() && this.searchField.isVisible() && keyCode != 256) {
                return true;
            } else if (this.client.options.chatKey.matchesKey(keyCode, scanCode) && !this.searchField.isFocused()) {
                this.searching = true;
                this.searchField.setTextFieldFocused(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return this.searching = false;
    }

    public boolean charTyped(char chr, int modifiers) {
        if (this.searching) {
            return false;
        } else if (this.isOpen() && !this.client.player.isSpectator()) {
            if (this.searchField.charTyped(chr, modifiers)) {
                this.refreshSearchResults();
                return true;
            }
        }
        return false;
    }

    private void refreshSearchResults() {
        assert this.searchField != null;
        String string = this.searchField.getText().toLowerCase(Locale.ROOT);
        this.triggerPrivateEasterEgg(string);
        if (!string.equals(this.searchText)) {
            this.refreshResults(false);
            this.searchText = string;
        }
    }

    private void triggerPrivateEasterEgg(String search) {
        String name;
        String text;
        switch (search) {
            case "misslilitu" -> {
                name = "MissLilitu";
                text = "Boo!";
            }
            case "satisfy" -> {
                name = "satisfy";
                text = "Boo!";
            }
            case "crystalknight" -> {
                name = "CrystalKnight";
                text = "Boo!";
            }
            case "bmjo" -> {
                name = "BaumeisterJO";
                text = "42";
            }
            default -> {
                return;
            }
        }
        PlayerEntity playerEntity = client.player;
        assert playerEntity != null;
        playerEntity.sendMessage(Text.of("<" + name + "> " + text));
        playerEntity.playSound(SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE, 0.5f, 1f);
    }

    public boolean isClickOutsideBounds(double mouseX, double mouseY, int x, int y, int backgroundWidth, int backgroundHeight) {
        if (!this.isOpen()) {
            return true;
        } else {
            boolean bl = mouseX < (double)x || mouseY < (double)y || mouseX >= (double)(x + backgroundWidth) || mouseY >= (double)(y + backgroundHeight);
            boolean bl2 = (double)(x - 147) < mouseX && mouseX < (double)x && (double)y < mouseY && mouseY < (double)(y + backgroundHeight);
            return bl && !bl2 && !this.currentTab.isHovered();
        }
    }

    protected void setCraftableButtonTexture() {
        this.toggleCraftableButton.setTextureUV(152, 41, 28, 18, TEXTURE);
    }

    protected Text getToggleCraftableButtonText() {
        return TOGGLE_CRAFTABLE_RECIPES_TEXT;
    }

    private Text getCraftableButtonText() {
        return this.toggleCraftableButton.isToggled() ? this.getToggleCraftableButtonText() : TOGGLE_ALL_RECIPES_TEXT;
    }

    public int findLeftEdge(int width, int backgroundWidth) {
        int i;
        if (this.isOpen() && !this.narrow) {
            i = 177 + (width - backgroundWidth - 200) / 2;
        } else {
            i = (width - backgroundWidth) / 2;
        }

        return i;
    }

    private boolean isWide() {
        return this.leftOffset == 86;
    }

    @Override
    public void acceptAlignedInput(Iterator<Ingredient> inputs, int slot, int amount, int gridX, int gridY) {}

    @Override
    public void onRecipesDisplayed(List<Recipe<?>> recipes) {}

    static {
        SEARCH_HINT_TEXT = Text.translatable("gui.recipebook.search_hint").formatted(Formatting.ITALIC).formatted(Formatting.GRAY);
        TOGGLE_CRAFTABLE_RECIPES_TEXT = Text.translatable("gui.recipebook.toggleRecipes.craftable");
        TOGGLE_ALL_RECIPES_TEXT = Text.translatable("gui.recipebook.toggleRecipes.all");
    }
}