package satisfyu.vinery.client.recipebook.custom;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import satisfyu.vinery.client.recipebook.IRecipeBookGroup;
import satisfyu.vinery.recipe.CookingPotRecipe;
import satisfyu.vinery.registry.ObjectRegistry;

import java.util.List;

@Environment(EnvType.CLIENT)
public enum CookingPotRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    JAM(new ItemStack(ObjectRegistry.CHERRY_JAM)),
    MISC(new ItemStack(Items.SUGAR));

    public static final List<IRecipeBookGroup> POT_GROUPS = ImmutableList.of(SEARCH, JAM, MISC);

    private final List<ItemStack> icons;

    CookingPotRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }

    public boolean fitRecipe(Recipe<?> recipe, RegistryAccess registryManager) {
        if (recipe instanceof CookingPotRecipe cookingPotRecipe) {
            switch (this) {
                case SEARCH -> {
                    return true;
                }
                case JAM -> {
                    if (cookingPotRecipe.getContainer().getItem() == ObjectRegistry.CHERRY_JAR.asItem()) {
                        return true;
                    }
                }
                case MISC -> {
                    if (cookingPotRecipe.getContainer().getItem() != ObjectRegistry.CHERRY_JAR.asItem()) {
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
