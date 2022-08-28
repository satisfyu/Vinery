package daniking.vinery.data.family;

import daniking.vinery.registry.ObjectRegistry;
import net.minecraft.data.family.BlockFamily;

public class BlockFamilies {
    public static BlockFamily LOAM = new BlockFamily.Builder(ObjectRegistry.LOAM).stairs(ObjectRegistry.LOAM_STAIRS).slab(ObjectRegistry.LOAM_SLAB).build();
}
