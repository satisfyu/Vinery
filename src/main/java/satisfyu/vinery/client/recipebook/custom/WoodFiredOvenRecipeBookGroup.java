package satisfyu.vinery.client.recipebook.custom;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import satisfyu.vinery.client.recipebook.IRecipeBookGroup;
import satisfyu.vinery.recipe.WoodFiredOvenRecipe;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;

@Environment(EnvType.CLIENT)
public enum WoodFiredOvenRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    BAKE(new ItemStack(ObjectRegistry.DOUGH)),
    MISC(new ItemStack(Items.SUGAR));

    public static final List<IRecipeBookGroup> WOOD_FIRED_OVEN_GROUPS = ImmutableList.of(SEARCH, BAKE, MISC);

    private final List<ItemStack> icons;

    WoodFiredOvenRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }

    public boolean fitRecipe(Recipe<?> recipe, DynamicRegistryManager registryManager) {
        if (recipe instanceof WoodFiredOvenRecipe woodFiredOvenRecipe) {
            switch (this) {
                case SEARCH -> {
                    return true;
                }
                case BAKE -> {
                    if (woodFiredOvenRecipe.getIngredients().stream().anyMatch((ingredient -> ingredient.test(ObjectRegistry.DOUGH.getDefaultStack())))) {
                        return true;
                    }
                }
                case MISC -> {
                    if (woodFiredOvenRecipe.getIngredients().stream().noneMatch((ingredient -> ingredient.test(ObjectRegistry.DOUGH.getDefaultStack())))) {
                        return true;
                    }
                }
                default -> {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public List<ItemStack> getIcons() {
        return this.icons;
    }

}
