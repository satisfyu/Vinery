package satisfyu.vinery.util.tab;

import java.util.Optional;
import java.util.function.Supplier;

public record GUIIcon<T>(Factory<T> factory) {
    public static <T> GUIIcon<T> of(Supplier<T> base, Supplier<T> hovered, Supplier<T> selected) {
        return new GUIIcon<>((h, s) -> {
            if (s) {
                return selected.get();
            }
            if (h) {
                return hovered.get();
            }
            return base.get();
        });
    }

    public static <T> GUIIcon<T> of(Supplier<T> base, Supplier<T> hovered, boolean delegateSelected) {
        return of(base, hovered, delegateSelected ? hovered : base);
    }

    public static <T> GUIIcon<T> of(Supplier<T> base, Supplier<T> hovered) {
        return of(base, hovered, true);
    }

    public static <T> GUIIcon<T> of(Supplier<T> base) {
        return of(base, base);
    }

    public static <T> GUIIcon<T> ofStatic(T base, T hovered, T selected) {
        return of(() -> base, () -> hovered, () -> selected);
    }

    public static <T> GUIIcon<T> ofStatic(T base, T hovered, boolean delegateSelected) {
        return ofStatic(base, hovered, delegateSelected ? hovered : base);
    }

    public static <T> GUIIcon<T> ofStatic(T base, T hovered) {
        return ofStatic(base, hovered, true);
    }

    public static <T> GUIIcon<T> ofStatic(T base) {
        return ofStatic(base, base);
    }

    public T getIcon(boolean hovered, boolean selected) {
        return this.factory().create(hovered, selected);
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> optional(GUIIcon<?> icon, boolean hovered, boolean selected, Class<T> clazz) {
        Object ico = icon.getIcon(hovered, selected);
        return ico.getClass() == clazz ? Optional.of((T) ico) : Optional.empty();
    }

    @FunctionalInterface public interface Factory<T> { T create(boolean hovered, boolean selected); }
}
