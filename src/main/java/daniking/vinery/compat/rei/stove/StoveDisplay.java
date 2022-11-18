package daniking.vinery.compat.rei.stove;


import daniking.vinery.Vinery;
import daniking.vinery.recipe.CookingPotRecipe;
import daniking.vinery.recipe.StoveCookingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.registry.RecipeManagerContext;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.cooking.DefaultCookingDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StoveDisplay extends BasicDisplay implements SimpleGridMenuDisplay {

    public static final CategoryIdentifier<StoveDisplay> MY_STOVE_DISPLAY = CategoryIdentifier.of(Vinery.MODID, "stove_display");


    private final float xp;

    public StoveDisplay(StoveCookingRecipe recipe) {
        this(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput())),
                recipe, recipe.getExperience());
    }

    public StoveDisplay(List<EntryIngredient> input, List<EntryIngredient> output, NbtCompound tag) {
        this(input, output, RecipeManagerContext.getInstance().byId(tag, "location"),
                tag.getFloat("experience"));
    }

    public StoveDisplay(List<EntryIngredient> input, List<EntryIngredient> output, Recipe<?> recipe, float xp) {
        super(input, output, Optional.ofNullable(recipe).map(Recipe::getId));
        this.xp = xp;
    }

    public float getXp() {
        return xp;
    }


    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }

    public static <R extends StoveDisplay> BasicDisplay.Serializer<R> serializer(BasicDisplay.Serializer.RecipeLessConstructor<R> constructor) {
        return BasicDisplay.Serializer.ofRecipeLess(constructor, (display, tag) -> {
            tag.putFloat("experience", display.getXp());
        });
    }



    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return new StoveCategory().getCategoryIdentifier();
    }
}
