package net.satisfy.vinery.block.grape;

import net.minecraft.world.level.block.state.properties.Property;
import net.satisfy.vinery.registry.GrapeTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class GrapeProperty extends Property<GrapeType> {
    private final Set<GrapeType> values;

    protected GrapeProperty(String name) {
        super(name, GrapeType.class);
        this.values = GrapeTypeRegistry.GRAPE_TYPE_TYPES;
    }

    public static GrapeProperty create(String name) {
        return new GrapeProperty(name);
    }

    @Override
    public @NotNull Collection<GrapeType> getPossibleValues() {
        return this.values;
    }

    @Override
    public @NotNull String getName(GrapeType grapeType) {
        return grapeType.getSerializedName();
    }

    @Override
    public @NotNull Optional<GrapeType> getValue(String string) {
        for (GrapeType grapeType : values) {
            if (string.equals(grapeType.getSerializedName()))
                return Optional.of(grapeType);
        }
        return Optional.empty();
    }
}