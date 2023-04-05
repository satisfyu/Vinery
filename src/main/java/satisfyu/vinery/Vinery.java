package satisfyu.vinery;

import satisfyu.vinery.util.tab.GUIIcon;
import satisfyu.vinery.util.tab.TabbedItemGroup;
import satisfyu.vinery.world.VineryConfiguredFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.registry.*;

public class Vinery implements ModInitializer {
    public static final String MODID = "vinery";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final ItemGroup CREATIVE_TAB = TabbedItemGroup.builder().build(new VineryIdentifier("vinery_tab"), g -> GUIIcon.of(() -> new ItemStack(ObjectRegistry.JUNGLE_RED_GRAPE)));
    public static final TagKey<Block> ALLOWS_COOKING_ON_POT = TagKey.of(Registry.BLOCK_KEY, new VineryIdentifier("allows_cooking_on_pot"));

    public void onInitialize() {
        VineryEffects.init();
        ObjectRegistry.init();
        VineryBlockEntityTypes.init();
        VineryScreenHandlerTypes.init();
        VineryRecipeTypes.init();
        LootTableEvents.MODIFY.register((resourceManager, manager, id, supplier, setter) -> {
            final Identifier resourceLocation = new VineryIdentifier("inject/seeds");
            if (Blocks.GRASS.getLootTableId().equals(id) || Blocks.TALL_GRASS.getLootTableId().equals(id) || Blocks.FERN.getLootTableId().equals(id)) {
                supplier.pool(LootPool.builder().with(LootTableEntry.builder(resourceLocation).weight(1)).build());
            }
        });
        VineryBoatTypes.init();
        VineryConfiguredFeatures.init();
        VinerySoundEvents.init();
        VineryVillagers.init();
        VineryEntites.init();
        VineryCompostableItems.init();

        FabricLoader.getInstance().getModContainer(MODID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("bushy_leaves"), container, ResourcePackActivationType.NORMAL);
        });
    }


}

