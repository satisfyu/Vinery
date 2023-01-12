package daniking.vinery.compat.rei.stove;

import com.google.common.collect.Lists;
import daniking.vinery.block.entity.WoodFiredOvenBlockEntity;
import daniking.vinery.registry.ObjectRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.SimpleDisplayRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

public class WoodFiredOvenCategory implements DisplayCategory<WoodFiredOvenDisplay> {

    @Override
    public List<Widget> setupDisplay(WoodFiredOvenDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.y + 10);
        int cookingTime = WoodFiredOvenBlockEntity.TOTAL_COOKING_TIME;
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        DecimalFormat df = new DecimalFormat("###.##");
        widgets.add(Widgets.createLabel(new Point(bounds.x + bounds.width - 5, bounds.y + 5), Component.translatable("category.rei.cooking.time&xp", df.format(display.getXp()), df.format(cookingTime / 20d))).noShadow().rightAligned().color(0xFF404040, 0xFFBBBBBB));
        int move = 20;
        int moveDown = 10;
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 24 + move, startPoint.y + 8 + moveDown)).animationDurationTicks(cookingTime));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61 + move, startPoint.y + 9 + moveDown)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61 + move, startPoint.y + 9 + moveDown)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        widgets.add(Widgets.createBurningFire(new Point(startPoint.x + 1, startPoint.y + 20 + moveDown)).animationDurationMS(10000));
        addSlot(widgets, display, startPoint, 0, moveDown, 0);
        if(display.getInputEntries().size() < 2) addBackground(widgets, startPoint, 18, moveDown);
        else addSlot(widgets, display, startPoint, 18, moveDown, 1);
        if(display.getInputEntries().size() < 3) addBackground(widgets, startPoint, 36, moveDown);
        else addSlot(widgets, display, startPoint, 36, moveDown, 2);
        return widgets;
    }

    public void addSlot(List<Widget> widgets, WoodFiredOvenDisplay display, Point startPoint, int x, int y, int index){
        widgets.add(Widgets.createSlot(new Point(startPoint.x + x -17, startPoint.y + 1+ y)).entries(display.getInputEntries().get(index)).markInput());
    }

    public void addBackground(List<Widget> widgets, Point startPoint, int x, int y){
        widgets.add(Widgets.createSlotBackground(new Point(startPoint.x + x -17, startPoint.y + 1 + y)));
    }

    @Override
    public DisplayRenderer getDisplayRenderer(WoodFiredOvenDisplay display) {
        return SimpleDisplayRenderer.from(Collections.singletonList(display.getInputEntries().get(0)), display.getOutputEntries());
    }

    @Override
    public int getDisplayHeight() {
        return 65;
    }

    @Override
    public CategoryIdentifier<? extends WoodFiredOvenDisplay> getCategoryIdentifier() {
        return WoodFiredOvenDisplay.WOOD_FIRED_OVEN_DISPLAY;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ObjectRegistry.WOOD_FIRED_OVEN);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("rei.vinery.wood_fired_oven_category");
    }
}
