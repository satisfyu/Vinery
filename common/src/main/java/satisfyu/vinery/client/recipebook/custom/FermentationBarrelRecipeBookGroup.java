package satisfyu.vinery.client.recipebook.custom;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import satisfyu.vinery.client.recipebook.IRecipeBookGroup;
import satisfyu.vinery.recipe.FermentationBarrelRecipe;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.util.VineryTags;

import java.util.List;

@Environment(EnvType.CLIENT)
public enum FermentationBarrelRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    WINE(new ItemStack(ObjectRegistry.WINE_BOTTLE)),
    MISC(new ItemStack(ObjectRegistry.APPLE_JUICE));

    public static final List<IRecipeBookGroup> FERMENTATION_GROUPS = ImmutableList.of(SEARCH, WINE, MISC);

    private final List<ItemStack> icons;

    FermentationBarrelRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }

    public boolean fitRecipe(Recipe<?> recipe, RegistryAccess registryManager) {
        System.out.println("test");
        System.out.println(recipe instanceof FermentationBarrelRecipe fermentationBarrelRecipe);
        if (recipe instanceof FermentationBarrelRecipe fermentationBarrelRecipe) {
            switch (this) {
                case SEARCH -> {
                    return true;
                }
                case WINE -> {
                    if (fermentationBarrelRecipe.getResultItem(registryManager).is(VineryTags.WINE)) {
                        return true;
                    }
                }
                case MISC -> {
                    if (!fermentationBarrelRecipe.getResultItem(registryManager).is(VineryTags.WINE)) {
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
