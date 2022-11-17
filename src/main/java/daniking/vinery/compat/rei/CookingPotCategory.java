package daniking.vinery.compat.rei;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.List;

public class CookingPotCategory implements DisplayCategory<CookingPotDisplay> {

    @Override
    public CategoryIdentifier<CookingPotDisplay> getCategoryIdentifier() {
        return CookingPotDisplay.MY_DISPLAY;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("rei.vinery.cooking_pot_category");
    }

    @Override
    public Renderer getIcon() {
        return EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(Items.ACACIA_LEAVES));
    }

    @Override
    public List<Widget> setupDisplay(CookingPotDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();

        // The base background of the display
        // Please try to not remove this to preserve an uniform style to REI
        widgets.add(Widgets.createRecipeBase(bounds));

        // The gray arrow
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));

        // We create a result slot background AND
        // disable the actual background of the slots, so the result slot can look bigger
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5))
                .entries(display.getInputEntries().get(0)) // Get the first output ingredient
                .disableBackground() // Disable the background because we have our bigger background
                .markOutput()); // Mark this as the output for REI to identify

        // We add the input slot
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5))
                .entries(display.getInputEntries().get(0)) // Get the first input ingredient
                .markInput()); // Mark this as the input for REI to identify

        // We return the list of widgets for REI to display
        return widgets;
    }
}
