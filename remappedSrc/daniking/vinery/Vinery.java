package daniking.vinery;

import daniking.vinery.world.VineryConfiguredFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vinery implements ModInitializer {

    public static final String MODID = "vinery";

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final CreativeModeTab CREATIVE_TAB = FabricItemGroupBuilder.build(new VineryIdentifier("creative_tab"), () -> new ItemStack(ObjectRegistry.RED_GRAPE));
    public static final TagKey<Block> ALLOWS_COOKING_ON_POT = TagKey.create(Registry.BLOCK_REGISTRY, new VineryIdentifier("allows_cooking_on_pot"));
/*
    public static final ItemGroup ITEM_GROUP_TABBED_ICON_TEXTURES =
            TabbedItemGroup.builder()
                    .defaultPredicate(ALWAYS)
                    .tab(Tab.builder().predicate((group, item) -> ObjectRegistry.getItemConvertibles().contains(item)).build("main", GUIIcon.of(() -> new ItemStack(ObjectRegistry.RED_GRAPE))))

                    .tab(Tab.builder().predicate(items(Items.STONE, Items.AXOLOTL_BUCKET)).build("one", GUIIcon.of(() -> new ItemStack(Items.GLOWSTONE))))
                    .tab(Tab.builder().predicate(items(Items.SPONGE)).build("two", GUIIcon.of(() -> new ItemStack(Items.STICK))))


                    .build(new VineryIdentifier("vinery_tab"), g -> GUIIcon.of(() -> new ItemStack(ObjectRegistry.RED_GRAPE)));
*/
    @Override
    public void onInitialize() {
        VineryEffects.init();
        ObjectRegistry.init();
        VineryBlockEntityTypes.init();
        VineryScreenHandlerTypes.init();
        VineryRecipeTypes.init();
        LootTableEvents.MODIFY.register((resourceManager, manager, id, supplier, setter) -> {
            final ResourceLocation resourceLocation = new VineryIdentifier("inject/seeds");
            if (Blocks.GRASS.getLootTable().equals(id) || Blocks.TALL_GRASS.getLootTable().equals(id) || Blocks.FERN.getLootTable().equals(id)) {
                supplier.pool(LootPool.lootPool().add(LootTableReference.lootTableReference(resourceLocation).setWeight(1)).build());
            }
        });
        VineryBoatTypes.init();
        VineryConfiguredFeatures.init();
        VinerySoundEvents.init();
        VineryVillagers.init();
        VineryEntites.init();
        VineryCompostableItems.init();

        FabricLoader.getInstance().getModContainer(MODID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("bushy_leaves"), container, ResourcePackActivationType.DEFAULT_ENABLED);
        });
    }


}

