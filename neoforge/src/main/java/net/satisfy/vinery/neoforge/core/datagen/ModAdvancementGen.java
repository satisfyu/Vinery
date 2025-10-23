package net.satisfy.vinery.neoforge.core.datagen;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.satisfy.vinery.core.registry.TagRegistry;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementGen extends AdvancementProvider {

    public ModAdvancementGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper, List<AdvancementGenerator> subProviders) {
        super(output, registries, existingFileHelper, subProviders);
    }

    public static final class MyAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {

            // Root advancement
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            item("vinery:wine_bottle"),
                            Component.translatable("advancement.vinery.root"),
                            Component.translatable("advancement.vinery.root.desc"),
                            ResourceLocation.parse("vinery:textures/block/dark_cherry_planks.png"),
                            AdvancementType.TASK,
                            false, false, false
                    )
                    .addCriterion("seeds", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(TagRegistry.GRAPE_SEEDS)))
                    .save(saver, loc("vinery:main/root"), existingFileHelper);

            // Fruits of the Field
            AdvancementHolder fruitsOfField = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            item("vinery:apple_tree_sapling"),
                            Component.translatable("advancement.vinery.fruits_of_the_field"),
                            Component.translatable("advancement.vinery.fruits_of_the_field.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("fruits_of_the_field", PlayerTrigger.TriggerInstance.located(
                            LocationPredicate.Builder.inBiome(registries.holderOrThrow(Biomes.PLAINS))
                    ))
                    .rewards(AdvancementRewards.Builder.experience(10))
                    .save(saver, loc("vinery:main/fruits_of_the_field"), existingFileHelper);

            // Cherry Picker
            Advancement.Builder.advancement()
                    .parent(fruitsOfField)
                    .display(
                            item("vinery:cherry"),
                            Component.translatable("advancement.vinery.cherry_picker"),
                            Component.translatable("advancement.vinery.cherry_picker.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("cherry", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(itemLike("vinery:cherry")).build()
                    ))
                    .save(saver, loc("vinery:main/cherry_picker"), existingFileHelper);

            // Forbidden Fruit
            AdvancementHolder forbiddenFruit = Advancement.Builder.advancement()
                    .parent(fruitsOfField)
                    .display(
                            new ItemStack(Items.APPLE),
                            Component.translatable("advancement.vinery.forbidden_fruit"),
                            Component.translatable("advancement.vinery.forbidden_fruit.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("apple", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(Items.APPLE).build()
                    ))
                    .save(saver, loc("vinery:main/forbidden_fruit"), existingFileHelper);

            // The First Press
            AdvancementHolder firstPress = Advancement.Builder.advancement()
                    .parent(forbiddenFruit)
                    .display(
                            item("vinery:apple_press"),
                            Component.translatable("advancement.vinery.the_first_press"),
                            Component.translatable("advancement.vinery.the_first_press.desc"),
                            null, AdvancementType.GOAL, true, true, false
                    )
                    .addCriterion("apple_press", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(
                            blockLike("vinery:apple_press")
                    ))
                    .save(saver, loc("vinery:main/the_first_press"), existingFileHelper);

            // Mashy Success
            Advancement.Builder.advancement()
                    .parent(firstPress)
                    .display(
                            item("vinery:apple_mash"),
                            Component.translatable("advancement.vinery.mashy_success"),
                            Component.translatable("advancement.vinery.mashy_success.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("apple_mash", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(itemLike("vinery:apple_mash")).build()
                    ))
                    .save(saver, loc("vinery:main/mashy_success"), existingFileHelper);

            // Purely Apple
            Advancement.Builder.advancement()
                    .parent(firstPress)
                    .display(
                            item("vinery:apple_juice"),
                            Component.translatable("advancement.vinery.purely_apple"),
                            Component.translatable("advancement.vinery.purely_apple.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("apple_juice", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(itemLike("vinery:apple_juice")).build()
                    ))
                    .save(saver, loc("vinery:main/purely_apple"), existingFileHelper);

            // Wild Harvest
            AdvancementHolder wildHarvest = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            item("vinery:jungle_grape_seeds_red"),
                            Component.translatable("advancement.vinery.wild_harvest"),
                            Component.translatable("advancement.vinery.wild_harvest.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("any_seed", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(
                                    itemLike("vinery:red_grape_seeds"),
                                    itemLike("vinery:white_grape_seeds"),
                                    itemLike("vinery:savanna_grape_seeds_red"),
                                    itemLike("vinery:savanna_grape_seeds_white"),
                                    itemLike("vinery:taiga_grape_seeds_white"),
                                    itemLike("vinery:taiga_grape_seeds_red"),
                                    itemLike("vinery:jungle_grape_seeds_red"),
                                    itemLike("vinery:jungle_grape_seeds_white")
                            ).build()
                    ))
                    .save(saver, loc("vinery:main/wild_harvest"), existingFileHelper);

            // Sowing the Future
            AdvancementHolder sowingFuture = Advancement.Builder.advancement()
                    .parent(wildHarvest)
                    .display(
                            item("vinery:grapevine_stem"),
                            Component.translatable("advancement.vinery.sowing_the_future"),
                            Component.translatable("advancement.vinery.sowing_the_future.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("use_seed_on_stem", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(
                                    BlockPredicate.Builder.block().of(blockLike("vinery:grapevine_stem"))
                            ),
                            ItemPredicate.Builder.item().of(tag("vinery:grape_seeds"))
                    ))
                    .save(saver, loc("vinery:main/sowing_the_future"), existingFileHelper);

            // Grape Picker
            Advancement.Builder.advancement()
                    .parent(sowingFuture)
                    .display(
                            item("vinery:taiga_grapes_red"),
                            Component.translatable("advancement.vinery.grape_picker"),
                            Component.translatable("advancement.vinery.grape_picker.desc"),
                            null, AdvancementType.CHALLENGE, true, true, false
                    )
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .addCriterion("has_the_items", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(itemLike("vinery:red_grape")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:white_grape")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:taiga_grapes_red")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:taiga_grapes_white")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:savanna_grapes_red")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:savanna_grapes_white")).build()
                    ))
                    .save(saver, loc("vinery:main/grape_picker"), existingFileHelper);

            // Overgrown Lattices
            AdvancementHolder overgrownLattices = Advancement.Builder.advancement()
                    .parent(wildHarvest)
                    .display(
                            item("vinery:oak_lattice"),
                            Component.translatable("advancement.vinery.overgrown_lattices"),
                            Component.translatable("advancement.vinery.overgrown_lattices.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("use_seed_on_lattice", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(
                                    BlockPredicate.Builder.block().of(blockLike("vinery:oak_lattice"))
                            ),
                            ItemPredicate.Builder.item().of(tag("vinery:jungle_grape_seeds"))
                    ))
                    .save(saver, loc("vinery:main/overgrown_lattices"), existingFileHelper);

            // Budding Grapes
            Advancement.Builder.advancement()
                    .parent(overgrownLattices)
                    .display(
                            item("vinery:jungle_grapes_red"),
                            Component.translatable("advancement.vinery.budding_grapes"),
                            Component.translatable("advancement.vinery.budding_grapes.desc"),
                            null, AdvancementType.CHALLENGE, true, true, false
                    )
                    .rewards(AdvancementRewards.Builder.experience(25))
                    .addCriterion("has_the_items", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(itemLike("vinery:jungle_grapes_red")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:jungle_grapes_white")).build()
                    ))
                    .save(saver, loc("vinery:main/budding_grapes"), existingFileHelper);

            // Vineyard Visionary
            Advancement.Builder.advancement()
                    .parent(wildHarvest)
                    .display(
                            item("vinery:savanna_grapes_white"),
                            Component.translatable("advancement.vinery.vineyard_visionary"),
                            Component.translatable("advancement.vinery.vineyard_visionary.desc"),
                            null, AdvancementType.CHALLENGE, true, true, false
                    )
                    .rewards(AdvancementRewards.Builder.experience(150))
                    .addCriterion("has_the_items", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(itemLike("vinery:red_grape")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:white_grape")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:taiga_grapes_red")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:taiga_grapes_white")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:savanna_grapes_red")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:savanna_grapes_white")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:jungle_grapes_red")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:jungle_grapes_white")).build()
                    ))
                    .save(saver, loc("vinery:main/vineyard_visionary"), existingFileHelper);

            // Juice It Up
            AdvancementHolder juiceItUp = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            item("vinery:grapevine_pot"),
                            Component.translatable("advancement.vinery.juice_it_up"),
                            Component.translatable("advancement.vinery.juice_it_up.desc"),
                            null, AdvancementType.GOAL, true, true, false
                    )
                    .addCriterion("grapevine_pot", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(
                            blockLike("vinery:grapevine_pot")
                    ))
                    .save(saver, loc("vinery:main/juice_it_up"), existingFileHelper);

            // Juicy Success
            AdvancementHolder juicySuccess = Advancement.Builder.advancement()
                    .parent(juiceItUp)
                    .display(
                            item("vinery:red_taiga_grapejuice"),
                            Component.translatable("advancement.vinery.juicy_success"),
                            Component.translatable("advancement.vinery.juicy_success.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("have_any_juice", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(
                                    itemLike("vinery:red_grapejuice"),
                                    itemLike("vinery:white_grapejuice"),
                                    itemLike("vinery:white_jungle_grapejuice"),
                                    itemLike("vinery:red_jungle_grapejuice"),
                                    itemLike("vinery:white_savanna_grapejuice"),
                                    itemLike("vinery:red_savanna_grapejuice"),
                                    itemLike("vinery:white_taiga_grapejuice"),
                                    itemLike("vinery:red_taiga_grapejuice")
                            ).build()
                    ))
                    .save(saver, loc("vinery:main/juicy_success"), existingFileHelper);

            // Nectar of Life
            Advancement.Builder.advancement()
                    .parent(juicySuccess)
                    .display(
                            item("vinery:white_taiga_grapejuice"),
                            Component.translatable("advancement.vinery.nectar_of_life"),
                            Component.translatable("advancement.vinery.nectar_of_life.desc"),
                            null, AdvancementType.CHALLENGE, true, true, false
                    )
                    .rewards(AdvancementRewards.Builder.experience(200))
                    .addCriterion("has_the_items", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(itemLike("vinery:red_grapejuice")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:white_grapejuice")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:white_jungle_grapejuice")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:red_jungle_grapejuice")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:white_savanna_grapejuice")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:red_savanna_grapejuice")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:white_taiga_grapejuice")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:red_taiga_grapejuice")).build()
                    ))
                    .save(saver, loc("vinery:main/nectar_of_life"), existingFileHelper);

            // The Magic of the Barrel
            AdvancementHolder magicBarrel = Advancement.Builder.advancement()
                    .parent(juicySuccess)
                    .display(
                            item("vinery:fermentation_barrel"),
                            Component.translatable("advancement.vinery.the_magic_of_the_barrel"),
                            Component.translatable("advancement.vinery.the_magic_of_the_barrel.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("fermentation_barrel", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(
                            blockLike("vinery:fermentation_barrel")
                    ))
                    .save(saver, loc("vinery:main/the_magic_of_the_barrel"), existingFileHelper);

            // The Noble Drop
            AdvancementHolder nobleDrop = Advancement.Builder.advancement()
                    .parent(magicBarrel)
                    .display(
                            item("vinery:red_wine"),
                            Component.translatable("advancement.vinery.the_noble_drop"),
                            Component.translatable("advancement.vinery.the_noble_drop.desc"),
                            null, AdvancementType.TASK, true, true, false
                    )
                    .addCriterion("have_any_wine", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(
                                    itemLike("vinery:stal_wine"), itemLike("vinery:kelp_cider"),
                                    itemLike("vinery:strad_wine"), itemLike("vinery:magnetic_wine"),
                                    itemLike("vinery:chorus_wine"), itemLike("vinery:aegis_wine"),
                                    itemLike("vinery:solaris_wine"), itemLike("vinery:noir_wine"),
                                    itemLike("vinery:bolvar_wine"), itemLike("vinery:cherry_wine"),
                                    itemLike("vinery:clark_wine"), itemLike("vinery:jellie_wine"),
                                    itemLike("vinery:apple_cider"), itemLike("vinery:red_wine"),
                                    itemLike("vinery:chenet_wine"), itemLike("vinery:lilitu_wine"),
                                    itemLike("vinery:mellohi_wine"), itemLike("vinery:jo_special_mixture"),
                                    itemLike("vinery:cristel_wine"), itemLike("vinery:glowing_wine"),
                                    itemLike("vinery:creepers_crush"), itemLike("vinery:bottle_mojang_noir"),
                                    itemLike("vinery:villagers_fright"), itemLike("vinery:mead"),
                                    itemLike("vinery:eiswein")
                            ).build()
                    ))
                    .save(saver, loc("vinery:main/the_noble_drop"), existingFileHelper);

            // Wine Sommelier
            Advancement.Builder.advancement()
                    .parent(nobleDrop)
                    .display(
                            item("vinery:jellie_wine"),
                            Component.translatable("advancement.vinery.wine_somelier"),
                            Component.translatable("advancement.vinery.wine_somelier.desc"),
                            null, AdvancementType.CHALLENGE, true, true, false
                    )
                    .rewards(AdvancementRewards.Builder.experience(800).addLootTable(
                            ResourceKey.create(Registries.LOOT_TABLE,ResourceLocation.parse("vinery:advancements/completionist"))
                    ))
                    .addCriterion("has_the_items", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(itemLike("vinery:stal_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:kelp_cider")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:strad_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:magnetic_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:chorus_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:aegis_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:solaris_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:noir_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:bolvar_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:cherry_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:clark_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:jellie_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:apple_cider")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:red_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:chenet_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:lilitu_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:mellohi_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:jo_special_mixture")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:cristel_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:glowing_wine")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:creepers_crush")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:bottle_mojang_noir")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:villagers_fright")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:mead")).build(),
                            ItemPredicate.Builder.item().of(itemLike("vinery:eiswein")).build()
                    ))
                    .save(saver, loc("vinery:main/wine_somelier"), existingFileHelper);

            // Vintage Perfection (hidden)
            CompoundTag nbtTag = new CompoundTag();
            nbtTag.putInt("Year", 15);
            Advancement.Builder.advancement()
                    .parent(nobleDrop)
                    .display(
                            item("vinery:lilitu_wine"),
                            Component.translatable("advancement.vinery.vintage_perfection"),
                            Component.translatable("advancement.vinery.vintage_perfection.desc"),
                            null, AdvancementType.TASK, true, true, true
                    )
                    .addCriterion("have_aged_wine", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item()
                                    .of(itemLike("vinery:lilitu_wine"))
                                    .hasComponents(DataComponentPredicate.builder().expect(DataComponents.CUSTOM_DATA, CustomData.of(nbtTag)).build())
                                    .build()
                    ))
                    .save(saver, loc("vinery:main/vintage_perfection"), existingFileHelper);
        }

        private static ItemStack item(String id) {
            return new ItemStack(itemLike(id));
        }

        private static ItemLike itemLike(String id) {
            return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
        }

        private static net.minecraft.world.level.block.Block blockLike(String id) {
            return BuiltInRegistries.BLOCK.get(ResourceLocation.parse(id));
        }

        private static net.minecraft.tags.TagKey<net.minecraft.world.item.Item> tag(String id) {
            return TagKey.create(Registries.ITEM,ResourceLocation.parse(id));
        }

        private static ResourceLocation loc(String path) {
            return ResourceLocation.parse(path);
        }
    }
}