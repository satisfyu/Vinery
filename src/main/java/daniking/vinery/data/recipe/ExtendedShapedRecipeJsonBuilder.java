package daniking.vinery.data.recipe;

import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.TagKey;
import org.jetbrains.annotations.Nullable;

public class ExtendedShapedRecipeJsonBuilder extends ShapedRecipeJsonBuilder {
    public ExtendedShapedRecipeJsonBuilder(ItemConvertible output, int outputCount) {
        super(output, outputCount);
    }

    public static ExtendedShapedRecipeJsonBuilder createExtended(ItemConvertible output) {
        return new ExtendedShapedRecipeJsonBuilder(output, 1);
    }

    public static ExtendedShapedRecipeJsonBuilder createExtended(ItemConvertible output, int outputCount) {
        return new ExtendedShapedRecipeJsonBuilder(output, outputCount);
    }

    public ExtendedShapedRecipeJsonBuilder input(Character c, Object obj) {
        if(obj instanceof ItemConvertible convertible) return (ExtendedShapedRecipeJsonBuilder) input(c, convertible);
        if(obj instanceof TagKey convertible) return (ExtendedShapedRecipeJsonBuilder) input(c, convertible);
        else return this;
    }

    public ExtendedShapedRecipeJsonBuilder pattern(String patternStr) {
        return (ExtendedShapedRecipeJsonBuilder) super.pattern(patternStr);
    }

    public <T> ExtendedShapedRecipeJsonBuilder criterion(String string, T criterionConditions) {
        CriterionConditions obj = getCondition(criterionConditions);
        if(obj != null) return (ExtendedShapedRecipeJsonBuilder) super.criterion(string, obj);
        return this;
    }

    private <T> CriterionConditions getCondition(T criterionConditions) {
        return criterionConditions instanceof ItemConvertible item ? RecipeProvider.conditionsFromItem(item) : criterionConditions instanceof TagKey tag ? RecipeProvider.conditionsFromTag(tag) : null;
    }

    @Override
    public ExtendedShapedRecipeJsonBuilder group(@Nullable String string) {
        return (ExtendedShapedRecipeJsonBuilder) super.group(string);
    }
}
