package satisfyu.vinery.block.lattice;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.api.EnvType;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.core.Moonlight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.WineRackStorageBlock;
import satisfyu.vinery.block.stem.NewLatticeBlock;
import satisfyu.vinery.client.VineryClient;
import satisfyu.vinery.config.VineryConfig;
import satisfyu.vinery.item.StandardItem;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.SoundEventRegistry;

import java.util.Collection;
import java.util.Objects;

import static satisfyu.vinery.registry.ObjectRegistry.registerWithItem;

public class DynamicHandler {

    public static final Class<WoodType> woodTypeClass = WoodType.class;

    public static void registerLatticeBlocks(Registrator<Block> event, Collection<WoodType> woodTypes) {
        boolean enableNetherLattices = VineryConfig.DEFAULT.getConfig().enableNetherLattices();
        Vinery.LOGGER.info("Nether lattices were set to: {}", enableNetherLattices);
        for (WoodType woodType : woodTypes) {
            if (!VineryConfig.DEFAULT.getConfig().enableNetherLattices()) {
                if (woodType.toVanilla() == net.minecraft.world.level.block.state.properties.WoodType.WARPED ||
                        woodType.toVanilla() == net.minecraft.world.level.block.state.properties.WoodType.CRIMSON) {
                    continue;
                }
            }
            String latticeId = woodType.getVariantId("%s_lattice");
            Block block = new NewLatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(woodType.getSound()).noOcclusion());
            woodType.addChild(new VineryIdentifier("lattice").toString(), block);

            RegistrySupplier<Block> currentRegistrySupplier = registerWithItem(latticeId, () -> block);

            ObjectRegistry.LATTICE_BLOCKS.add(currentRegistrySupplier);
            if (Platform.getEnvironment().toPlatform() == EnvType.CLIENT) {
                Vinery.LOGGER.debug("Adding {} lattice block to client list", woodType.getReadableName());
                VineryClient.LATTICE_BLOCKS.add(currentRegistrySupplier.get());
                VineryClient.registerAsCutout(currentRegistrySupplier.get());
                Vinery.LOGGER.debug("Added {} lattice block to cutout render list", woodType.getReadableName());
            }
        }
    }
}
