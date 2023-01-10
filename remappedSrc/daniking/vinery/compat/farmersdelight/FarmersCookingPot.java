package daniking.vinery.compat.farmersdelight;

import com.nhoryzon.mc.farmersdelight.recipe.CookingPotRecipe;
import daniking.vinery.block.entity.CookingPotEntity;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class FarmersCookingPot {
    public static Recipe<?> getRecipe(Level world, Container inventory){
        return world.getRecipeManager().getRecipeFor((RecipeType<? extends Recipe<Container>>) Registry.RECIPE_TYPE.get(new ResourceLocation("farmersdelight", "cooking")), inventory, world).orElse(null);

    }

    public static boolean canCraft(Recipe<?> recipe, CookingPotEntity entity){
        if(recipe instanceof CookingPotRecipe r){
            if (!entity.getItem(CookingPotEntity.BOTTLE_INPUT_SLOT).is(r.getContainer().getItem())) {
                return false;
            } else if (entity.getItem(CookingPotEntity.OUTPUT_SLOT).isEmpty()) {
                return true;
            } else {
                final ItemStack recipeOutput = r.getResultItem();
                final ItemStack outputSlotStack = entity.getItem(CookingPotEntity.OUTPUT_SLOT);
                final int outputSlotCount = outputSlotStack.getCount();
                if (!outputSlotStack.sameItem(recipeOutput)) {
                    return false;
                } else if (outputSlotCount < entity.getMaxStackSize() && outputSlotCount < outputSlotStack.getMaxStackSize()) {
                    return true;
                } else {
                    return outputSlotCount < recipeOutput.getMaxStackSize();
                }
            }
        }
        return false;
    }

    public static ItemStack getContainer(Recipe<Container> recipe){
        if(recipe instanceof CookingPotRecipe c){
            return c.getContainer();
        }
        else return ItemStack.EMPTY;
    }

    public static Class<CookingPotRecipe> getRecipeClass(){
        return CookingPotRecipe.class;
    }

    public static boolean isItemIngredient(ItemStack stack, Level world) {
        return recipeStream(world).anyMatch(cookingPotRecipe -> cookingPotRecipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stack)));
    }

    public static boolean isItemContainer(ItemStack stack, Level world) {
        return recipeStream(world).anyMatch(cookingPotRecipe -> cookingPotRecipe.getContainer().is(stack.getItem()));
    }

    private static Stream<CookingPotRecipe> recipeStream(Level world) {
        return world.getRecipeManager().getAllRecipesFor((RecipeType<CookingPotRecipe>) Registry.RECIPE_TYPE.get(new ResourceLocation("farmersdelight", "cooking"))).stream();
    }
}
