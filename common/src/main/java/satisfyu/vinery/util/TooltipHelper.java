package satisfyu.vinery.util;

import satisfyu.vinery.mixin.OrderedTextToolTipAccessor;

import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TooltipHelper {

    private static boolean shouldFlip = false;

    public static void newFix(List<ClientTooltipComponent> components, Font textRenderer, int x, int width) {
        shouldFlip = false;

        int forcedWidth = 0;
        for (ClientTooltipComponent component : components) {
            if (!(component instanceof ClientTextTooltip)) {
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


    public static int shouldFlip(List<ClientTooltipComponent> components, Font textRenderer, int x) {
        int maxWidth = 0;
        for (ClientTooltipComponent tooltipComponent : components) {
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


    private static void wrapLongLines(List<ClientTooltipComponent> components, Font textRenderer, int maxSize) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof ClientTextTooltip orderedTextTooltipComponent) {
                Component text = OrderedTextToTextVisitor.get(((OrderedTextToolTipAccessor) orderedTextTooltipComponent).getText());

                List<ClientTooltipComponent> wrapped = textRenderer.split(text, maxSize).stream().map(ClientTooltipComponent::create).toList();
                components.remove(i);
                components.addAll(i, wrapped);
            }
        }
    }

    private static void wrapNewLines(List<ClientTooltipComponent> components) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof ClientTextTooltip orderedTextTooltipComponent) {
                Component text = OrderedTextToTextVisitor.get(((OrderedTextToolTipAccessor) orderedTextTooltipComponent).getText());

                List<Component> children = text.getSiblings();
                for (int j = 0; j < children.size() - 1; j++) {
                    String code = children.get(j).getString() + children.get(j + 1).getString();
                    if (code.equals("\\n")) {
                        components.set(i, ClientTooltipComponent.create(textWithChildren(children, 0, j).getVisualOrderText()));
                        components.add(i + 1, ClientTooltipComponent.create(textWithChildren(children, j + 2, children.size()).getVisualOrderText()));
                        break;
                    }
                }
            }
        }
    }

    private static Component textWithChildren(List<Component> children, int from, int end) {
        MutableComponent text = Component.literal("");
        for (int i = from; i < end; i++)
            text.append(children.get(i));
        return text;
    }
}