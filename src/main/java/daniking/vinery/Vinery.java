package daniking.vinery;

import daniking.vinery.registry.*;
import daniking.vinery.world.VineryConfiguredFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
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
import net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.TabbedItemGroup;
import net.moddingplayground.frame.api.util.GUIIcon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.Predicate.ALWAYS;
import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.Predicate.items;

public class Vinery implements ModInitializer {

    public static final String MODID = "vinery";

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final ItemGroup CREATIVE_TAB = FabricItemGroupBuilder.build(new VineryIdentifier("creative_tab"), () -> new ItemStack(ObjectRegistry.RED_GRAPE));
    public static final TagKey<Block> ALLOWS_COOKING_ON_POT = TagKey.of(Registry.BLOCK_KEY, new VineryIdentifier("allows_cooking_on_pot"));
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


        FabricLoader.getInstance().getModContainer(MODID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("bushy_leaves"), container, ResourcePackActivationType.DEFAULT_ENABLED);
        });
    }
}

