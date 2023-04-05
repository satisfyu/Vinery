package satisfyu.vinery.client.screen.sideTip;

import net.minecraft.text.Text;

public class SideToolTip {
    private final int x; //pos in GUI
    private final int y;
    private final int width;
    private final int height;
    private final Text text;

    public SideToolTip(int x, int y, int width, int height, Text text) {
        this.x = -SideTip.WIDTH + x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width
                && mouseY >= y && mouseY < y + height;
    }

    public Text getText() {
        return this.text;
    }

}
