package satisfyu.vinery.client.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.recipebook.RecipeShownListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.client.VineryClient;
import satisfyu.vinery.client.screen.recipe.PrivateRecipeBookRecipeArea;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public abstract class  PrivateRecipeBookWidget extends GuiComponent implements PlaceRecipe<Ingredient>, Renderable, GuiEventListener, RecipeShownListener {
    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/recipe_book.png");
    private static final Component SEARCH_HINT_TEXT;
    private static final Component TOGGLE_CRAFTABLE_RECIPES_TEXT;
    private static final Component TOGGLE_ALL_RECIPES_TEXT;
    protected final PrivateRecipeBookGhostSlots ghostSlots = new PrivateRecipeBookGhostSlots();
    private final List<PrivateRecipeGroupButtonWidget> tabButtons = Lists.newArrayList();
    @Nullable
    private PrivateRecipeGroupButtonWidget currentTab;
    protected StateSwitchingButton toggleCraftableButton;
    protected AbstractPrivateRecipeScreenHandler screenHandler;
    @Nullable
    private EditBox searchField;
    private int leftOffset;
    private int parentWidth;
    private int parentHeight;

    protected Minecraft client;
    private String searchText = "";
    private final PrivateRecipeBookRecipeArea recipesArea = new PrivateRecipeBookRecipeArea();
    private final StackedContents recipeFinder = new StackedContents();
    private int cachedInvChangeCount;
    private boolean searching;
    private boolean open;
    private boolean narrow;

    public PrivateRecipeBookWidget() {}

    protected abstract RecipeType<? extends Recipe<Container>> getRecipeType();
    public abstract void insertRecipe(Recipe<?> recipe) ;
    public abstract void showGhostRecipe(Recipe<?> recipe, List<Slot> slots);

    public void initialize(int parentWidth, int parentHeight, Minecraft client, boolean narrow, AbstractPrivateRecipeScreenHandler craftingScreenHandler) {
        this.client = client;
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
        this.screenHandler = craftingScreenHandler;
        this.narrow = narrow;
        assert client.player != null;
        client.player.containerMenu = craftingScreenHandler;
        this.cachedInvChangeCount = client.player.getInventory().getTimesChanged();
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

    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        if (this.isOpen()) {
            matrices.pushPose();
            matrices.translate(0.0, 0.0, 100.0);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURE);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int i = (this.parentWidth - 147) / 2 - this.leftOffset;
            int j = (this.parentHeight - 166) / 2;
            this.blit(matrices, i, j, 1, 1, 147, 166);
            if (!this.searchField.isFocused() && this.searchField.getValue().isEmpty()) {
                drawString(matrices, this.client.font, SEARCH_HINT_TEXT, i + 25, j + 14, -1);
            } else {
                this.searchField.render(matrices, mouseX, mouseY, delta);
            }

            for (PrivateRecipeGroupButtonWidget recipeGroupButtonWidget : this.tabButtons) {
                recipeGroupButtonWidget.render(matrices, mouseX, mouseY, delta);
            }

            this.toggleCraftableButton.render(matrices, mouseX, mouseY, delta);
            this.recipesArea.draw(matrices, i, j, mouseX, mouseY, delta);
            matrices.popPose();
        }
    }

    public void drawGhostSlots(PoseStack matrices, int x, int y, boolean bl, float delta) {
        this.ghostSlots.draw(matrices, this.client, x, y, bl, delta);
    }

    public void drawTooltip(PoseStack matrices, int x, int y, int mouseX, int mouseY) {
        if (this.isOpen()) {
            this.recipesArea.drawTooltip(matrices, mouseX, mouseY);
            if (this.toggleCraftableButton.isHovered()) {
                Component text = this.getCraftableButtonText();
                if (this.client.screen != null) {
                    this.client.screen.renderTooltip(matrices, text, mouseX, mouseY);
                }
            }

            this.drawGhostSlotTooltip(matrices, x, y, mouseX, mouseY);
        }
    }

    private void drawGhostSlotTooltip(PoseStack matrices, int x, int y, int mouseX, int mouseY) {
        ItemStack itemStack = null;

        for(int i = 0; i < this.ghostSlots.getSlotCount(); ++i) {
            PrivateRecipeBookGhostSlots.PrivateGhostInputSlot ghostInputSlot = this.ghostSlots.getSlot(i);
            int j = ghostInputSlot.getX() + x;
            int k = ghostInputSlot.getY() + y;
            if (mouseX >= j && mouseY >= k && mouseX < j + 16 && mouseY < k + 16) {
                itemStack = ghostInputSlot.getCurrentItemStack();
            }
        }

        if (itemStack != null && this.client.screen != null) {
            this.client.screen.renderComponentTooltip(matrices, this.client.screen.getTooltipFromItem(itemStack), mouseX, mouseY);
        }

    }

    public void update() {
        boolean bl = this.isGuiOpen();
        if (this.isOpen() != bl) {
            this.setOpen(bl);
        }

        if (this.isOpen()) {
            if (this.cachedInvChangeCount != this.client.player.getInventory().getTimesChanged()) {
                this.refreshInputs();
                this.cachedInvChangeCount = this.client.player.getInventory().getTimesChanged();
            }

            this.searchField.tick();
        }
    }

    private void refreshResults(boolean resetCurrentPage) {
        if (this.currentTab == null) return;
        if (this.searchField == null) return;

        List<? extends Recipe<Container>> recipes = getResultsForGroup(currentTab.getGroup(), client.level.getRecipeManager().getAllRecipesFor(getRecipeType()));

        String string = this.searchField.getValue();

        if (!string.isEmpty()) {
            recipes.removeIf((recipe) -> !recipe.getResultItem(client.level.registryAccess()).getHoverName().getString().toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT)));
        }

        if (VineryClient.rememberedCraftableToggle) {
            recipes.removeIf((recipe) -> !screenHandler.hasIngredient(recipe));
        }

        this.recipesArea.setResults(recipes, resetCurrentPage);
    }

    private <T extends Recipe<Container>> List<T> getResultsForGroup(IRecipeBookGroup group, List<T> recipes) {
        List<T> results = Lists.newArrayList();
        for (T recipe : recipes) {
            if (group.fitRecipe(recipe, client.level.registryAccess())) {
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
            recipeGroupButtonWidget.setPosition(i, j + 27 * l++);
        }
    }

    private void refreshInputs() {
        this.recipeFinder.clear();
        this.client.player.getInventory().fillStackedContents(this.recipeFinder);
        this.refreshResults(false);
    }

    public void reset() {
        this.leftOffset = this.narrow ? 0 : 86;
        int i = (this.parentWidth - 147) / 2 - this.leftOffset;
        int j = (this.parentHeight - 166) / 2;
        this.recipeFinder.clear();
        this.client.player.getInventory().fillStackedContents(this.recipeFinder);
        String string = this.searchField != null ? this.searchField.getValue() : "";
        Font var10003 = this.client.font;
        int var10004 = i + 25;
        int var10005 = j + 14;
        Objects.requireNonNull(this.client.font);
        this.searchField = new EditBox(var10003, var10004, var10005, 80, 9 + 5, Component.translatable("itemGroup.search"));
        this.searchField.setMaxLength(50);
        this.searchField.setBordered(false);
        this.searchField.setVisible(true);
        this.searchField.setTextColor(16777215);
        this.searchField.setValue(string);
        this.recipesArea.initialize(this.client, i, j, this.screenHandler);
        this.toggleCraftableButton = new StateSwitchingButton(i + 110, j + 12, 26, 16, VineryClient.rememberedCraftableToggle);
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

        this.currentTab.setStateTriggered(true);
        this.refreshResults(false);
        this.refreshTabButtons();
    }

    public void slotClicked(@Nullable Slot slot) {
        if (slot != null && slot.index < this.screenHandler.getCraftingSlotCount()) {
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
                    this.toggleCraftableButton.setStateTriggered(bl);
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
                        this.currentTab.setStateTriggered(false);
                    }

                    this.currentTab = vineryRecipeGroupButtonWidget;
                    this.currentTab.setStateTriggered(true);
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
            } else if (this.client.options.keyChat.matches(keyCode, scanCode) && !this.searchField.isFocused()) {
                this.searching = true;
                this.searchField.setFocused(true);
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
        String string = this.searchField.getValue().toLowerCase(Locale.ROOT);
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
        Player playerEntity = client.player;
        assert playerEntity != null;
        playerEntity.sendSystemMessage(Component.nullToEmpty("<" + name + "> " + text));
        playerEntity.playSound(SoundEvents.FIREWORK_ROCKET_TWINKLE, 0.5f, 1f);
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
        this.toggleCraftableButton.initTextureValues(152, 41, 28, 18, TEXTURE);
    }

    protected Component getToggleCraftableButtonText() {
        return TOGGLE_CRAFTABLE_RECIPES_TEXT;
    }

    private Component getCraftableButtonText() {
        return this.toggleCraftableButton.isStateTriggered() ? this.getToggleCraftableButtonText() : TOGGLE_ALL_RECIPES_TEXT;
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
    public void addItemToSlot(Iterator<Ingredient> inputs, int slot, int amount, int gridX, int gridY) {}

    @Override
    public void recipesShown(List<Recipe<?>> recipes) {}

    static {
        SEARCH_HINT_TEXT = Component.translatable("gui.recipebook.search_hint").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);
        TOGGLE_CRAFTABLE_RECIPES_TEXT = Component.translatable("gui.recipebook.toggleRecipes.craftable");
        TOGGLE_ALL_RECIPES_TEXT = Component.translatable("gui.recipebook.toggleRecipes.all");
    }
}