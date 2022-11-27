package daniking.vinery.compat.rei.press;


import daniking.vinery.Vinery;
import daniking.vinery.registry.ObjectRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Items;

import java.util.Collections;
import java.util.List;

public class WinePressDisplay implements Display {

    public static final CategoryIdentifier<WinePressDisplay> WINE_PRESS_DISPLAY = CategoryIdentifier.of(Vinery.MODID, "wine_press_display");


    @Override
    public List<EntryIngredient> getInputEntries() {
        return Collections.singletonList(EntryIngredients.of(Items.APPLE));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(EntryIngredients.of(ObjectRegistry.APPLE_MASH));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return WINE_PRESS_DISPLAY;
    }


}
