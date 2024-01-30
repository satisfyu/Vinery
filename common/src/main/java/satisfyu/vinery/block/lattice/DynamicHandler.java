package satisfyu.vinery.block.lattice;

import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.core.Moonlight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.block.WineRackStorageBlock;
import satisfyu.vinery.block.stem.NewLatticeBlock;
import satisfyu.vinery.item.StandardItem;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.registry.SoundEventRegistry;

import java.util.Collection;

import static satisfyu.vinery.registry.ObjectRegistry.registerWithItem;

public class DynamicHandler {

    public static final Class<WoodType> woodTypeClass = WoodType.class;

    public static void registerLatticeBlocks(Registrator<Block> event, Collection<WoodType> woodTypes) {
        for (WoodType woodType : woodTypes) {
            String latticeId = woodType.getVariantId("%s_lattice");
            Block block = new NewLatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(woodType.getSound()).noOcclusion());
            woodType.addChild(new VineryIdentifier("lattice").toString(), block);

            ObjectRegistry.LATTICE_BLOCKS.add(registerWithItem(latticeId, () -> block));
        }
    }
}
