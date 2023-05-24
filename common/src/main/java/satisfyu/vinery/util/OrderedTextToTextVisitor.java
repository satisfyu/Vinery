package satisfyu.vinery.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;

public class OrderedTextToTextVisitor implements FormattedCharSink {
	private final MutableComponent text = Component.nullToEmpty("").copy();

	public static Component get(FormattedCharSequence text) {
		OrderedTextToTextVisitor visitor = new OrderedTextToTextVisitor();
		text.accept(visitor);
		return visitor.getText();
	}

	@Override
	public boolean accept(int index, Style style, int codePoint) {
		String car = new String(Character.toChars(codePoint));
		text.append(Component.nullToEmpty(car).copy().withStyle(style));
		return true;
	}

	public Component getText() {
		return text;
	}
}