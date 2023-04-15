package satisfyu.vinery.util.tab;

import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class TabUtil {
    private TabUtil() {}

    public static Identifier suffixIdentifier(Identifier id, String suffix) {
        return Identifier.tryParse("%s%s.png".formatted(id, suffix.isEmpty() ? "" : "_%s".formatted(suffix)));
    }

    public static Identifier suffixIdentifier(Identifier id) {
        return suffixIdentifier(id, "");
    }

    public static GUIIcon<Identifier> iconOf(Identifier id) {
        return GUIIcon.ofStatic(suffixIdentifier(id), suffixIdentifier(id, "hovered"), suffixIdentifier(id, "selected"));
    }

    public static <T> T[] indexedArray(int cap, IntFunction<T[]> generator, Function<Integer, T> action) {
        return Util.make(new ArrayList<T>(), list -> {
            for (int i = 0; i < cap; i++) list.add(action.apply(i));
        }).toArray(generator);
    }
}
