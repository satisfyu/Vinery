package satisfyu.vinery.block.grape;

import com.google.common.collect.Maps;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;
import satisfyu.vinery.registry.GrapeTypes;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GrapeProperty extends Property<GrapeType> {
    private final Set<GrapeType> values;
    private final Map<String, GrapeType> names = Maps.newHashMap();

    protected GrapeProperty(String name) {
        super(name, GrapeType.class);
        this.values = GrapeTypes.GRAPE_TYPE_TYPES;

        for (GrapeType grapeType : this.values) {
            String id = grapeType.getSerializedName();
            if (this.names.containsKey(id)) {
                throw new IllegalArgumentException("Multiple values have the same name '" + id + "'");
            }
            this.names.put(id, grapeType);
        }
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
        return Optional.ofNullable(this.names.get(string));
    }
}