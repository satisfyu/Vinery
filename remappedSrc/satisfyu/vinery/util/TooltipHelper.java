package satisfyu.vinery.util;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import satisfyu.vinery.mixin.OrderedTextToolTipAccessor;

import java.util.List;

public class TooltipHelper {

    private static boolean shouldFlip = false;

    public static void newFix(List<TooltipComponent> components, TextRenderer textRenderer, int x, int width) {
        shouldFlip = false;

        int forcedWidth = 0;
        for (TooltipComponent component : components) {
            if (!(component instanceof OrderedTextTooltipComponent)) {
                int width2 = component.getWidth(textRenderer);
                if (width2 > forcedWidth)
                    forcedWidth = width2;
            }
        }

        int maxWidth = width - 20 - x;
        if (forcedWidth > maxWidth || maxWidth < 100) {
            shouldFlip = true;
            maxWidth = x - 28;
        }

        wrapNewLines(components);
        wrapLongLines(components, textRenderer, maxWidth);
    }


    public static int shouldFlip(List<TooltipComponent> components, TextRenderer textRenderer, int x) {
        int maxWidth = 0;
        for (TooltipComponent tooltipComponent : components) {
            int newWidth = tooltipComponent.getWidth(textRenderer);
            if (newWidth > maxWidth) {
                maxWidth = newWidth;
            }
        }
        int renderX = x + 12;

        if (shouldFlip)
            renderX -= 28 + maxWidth;

        return renderX;
    }


    private static void wrapLongLines(List<TooltipComponent> components, TextRenderer textRenderer, int maxSize) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof OrderedTextTooltipComponent orderedTextTooltipComponent) {
                Text text = OrderedTextToTextVisitor.get(((OrderedTextToolTipAccessor) orderedTextTooltipComponent).getText());

                List<TooltipComponent> wrapped = textRenderer.wrapLines(text, maxSize).stream().map(TooltipComponent::of).toList();
                components.remove(i);
                components.addAll(i, wrapped);
            }
        }
    }

    private static void wrapNewLines(List<TooltipComponent> components) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof OrderedTextTooltipComponent orderedTextTooltipComponent) {
                Text text = OrderedTextToTextVisitor.get(((OrderedTextToolTipAccessor) orderedTextTooltipComponent).getText());

                List<Text> children = text.getSiblings();
                for (int j = 0; j < children.size() - 1; j++) {
                    String code = children.get(j).getString() + children.get(j + 1).getString();
                    if (code.equals("\\n")) {
                        components.set(i, TooltipComponent.of(textWithChildren(children, 0, j).asOrderedText()));
                        components.add(i + 1, TooltipComponent.of(textWithChildren(children, j + 2, children.size()).asOrderedText()));
                        break;
                    }
                }
            }
        }
    }

    private static Text textWithChildren(List<Text> children, int from, int end) {
        MutableText text = Text.literal("");
        for (int i = from; i < end; i++)
            text.append(children.get(i));
        return text;
    }
}