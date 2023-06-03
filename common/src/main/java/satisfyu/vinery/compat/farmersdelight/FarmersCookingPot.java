package satisfyu.vinery.compat.farmersdelight;

public class FarmersCookingPot {
    /*
    public static Recipe<?> getRecipe(World world, Inventory inventory){
        return world.getRecipeManager().getFirstMatch((RecipeType<? extends Recipe<Inventory>>) Registries.RECIPE_TYPE.get(new Identifier("farmersdelight", "cooking")), inventory, world).orElse(null);

    }


    public static boolean canCraft(Recipe<?> recipe, CookingPotEntity entity){

        if(recipe instanceof CookingPotRecipe r){
            if (!entity.getStack(CookingPotEntity.BOTTLE_INPUT_SLOT).isOf(r.getContainer().getItem())) {
                return false;
            } else if (entity.getStack(CookingPotEntity.OUTPUT_SLOT).isEmpty()) {
                return true;
            } else {
                final ItemStack recipeOutput = r.getOutput();
                final ItemStack outputSlotStack = entity.getStack(CookingPotEntity.OUTPUT_SLOT);
                final int outputSlotCount = outputSlotStack.getCount();
                if (!outputSlotStack.isItemEqual(recipeOutput)) {
                    return false;
                } else if (outputSlotCount < entity.getMaxCountPerStack() && outputSlotCount < outputSlotStack.getMaxCount()) {
                    return true;
                } else {
                    return outputSlotCount < recipeOutput.getMaxCount();
                }
            }
        }
        return false;
    }

    public static ItemStack getContainer(Recipe<Inventory> recipe){
        if(recipe instanceof CookingPotRecipe c){
            return c.getContainer();
        }
        else return ItemStack.EMPTY;
    }

    public static Class<CookingPotRecipe> getRecipeClass(){
        return CookingPotRecipe.class;
    }

    public static boolean isItemIngredient(ItemStack stack, World world) {
        return recipeStream(world).anyMatch(cookingPotRecipe -> cookingPotRecipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stack)));
    }

    public static boolean isItemContainer(ItemStack stack, World world) {
        return recipeStream(world).anyMatch(cookingPotRecipe -> cookingPotRecipe.getContainer().isOf(stack.getItem()));
    }

    private static Stream<CookingPotRecipe> recipeStream(World world) {
        return world.getRecipeManager().listAllOfType((RecipeType<CookingPotRecipe>) Registries.RECIPE_TYPE.get(new Identifier("farmersdelight", "cooking"))).stream();
    }

     */
}
