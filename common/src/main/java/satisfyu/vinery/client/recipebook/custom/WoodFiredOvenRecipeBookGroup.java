package satisfyu.vinery.client.recipebook.custom;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import satisfyu.vinery.client.recipebook.IRecipeBookGroup;
import satisfyu.vinery.recipe.WoodFiredOvenRecipe;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;

@Environment(EnvType.CLIENT)
public enum WoodFiredOvenRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    BAKE(new ItemStack(ObjectRegistry.DOUGH.get())),
    MISC(new ItemStack(Items.SUGAR));

    public static final List<IRecipeBookGroup> WOOD_FIRED_OVEN_GROUPS = ImmutableList.of(SEARCH, BAKE, MISC);

    private final List<ItemStack> icons;

    WoodFiredOvenRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }


    @Override
    public boolean fitRecipe(Recipe<? extends Container> recipe) {
        if (recipe instanceof WoodFiredOvenRecipe woodFiredOvenRecipe) {
            switch (this) {
                case SEARCH -> {
                    return true;
                }
                case BAKE -> {
                    if (woodFiredOvenRecipe.getIngredients().stream().anyMatch((ingredient -> ingredient.test(ObjectRegistry.DOUGH.get().getDefaultInstance())))) {
                        return true;
                    }
                }
                case MISC -> {
                    if (woodFiredOvenRecipe.getIngredients().stream().noneMatch((ingredient -> ingredient.test(ObjectRegistry.DOUGH.get().getDefaultInstance())))) {
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
