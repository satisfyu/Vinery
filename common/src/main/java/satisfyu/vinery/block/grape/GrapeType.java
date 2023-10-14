package satisfyu.vinery.block.grape;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class GrapeType implements Comparable<GrapeType>, StringRepresentable {
    private final String id;
    private final boolean lattice;
    private Supplier<Item> fruit;
    private Supplier<Item> seeds;
    private Supplier<Item> bottle;

    public GrapeType(String id) {
        this(id, false);
    }

    public GrapeType(String id, boolean lattice) {
        this(id,  () -> Items.AIR, () -> Items.AIR, () -> Items.AIR, lattice);
    }

    private GrapeType(String id, Supplier<Item> fruit, Supplier<Item> seeds, Supplier<Item> bottle, boolean lattice) {
        this.id = id;
        this.fruit = fruit;
        this.seeds = seeds;
        this.bottle = bottle;
        this.lattice = lattice;
    }

    @Override
    public @NotNull String getSerializedName() {
        return id;
    }

    public Item getFruit() {
        return this.fruit.get();
    }

    public Item getSeeds() {
        return this.seeds.get();
    }

    public Item getBottle() {
        return bottle.get();
    }

    public boolean isLattice() {
        return lattice;
    }

    public void setItems(Supplier<Item> fruit, Supplier<Item> seeds, Supplier<Item> bottle) {
        this.fruit = fruit;
        this.seeds = seeds;
        this.bottle = bottle;
    }

    @Override
    public int compareTo(@NotNull GrapeType grapeType) {
        return 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrapeType grapeType)) return false;
        return Objects.equals(id, grapeType.id);
    }
}
