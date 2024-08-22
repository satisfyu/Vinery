package net.satisfy.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.vinery.Vinery;

import java.util.EnumMap;
import java.util.List;

public class ArmorMaterialRegistry {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Vinery.MOD_ID, Registries.ARMOR_MATERIAL);

    public static final Holder<ArmorMaterial> WINEMAKER_ARMOR = createMaterialNoOverlay();

    private static Holder<ArmorMaterial> createMaterialNoOverlay() {
        return ARMOR_MATERIALS.register("winemaker", () -> new ArmorMaterial(
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    map.put(ArmorItem.Type.BOOTS, ArmorMaterials.LEATHER.value().getDefense(ArmorItem.Type.BOOTS));
                    map.put(ArmorItem.Type.LEGGINGS, ArmorMaterials.LEATHER.value().getDefense(ArmorItem.Type.LEGGINGS));
                    map.put(ArmorItem.Type.CHESTPLATE, ArmorMaterials.LEATHER.value().getDefense(ArmorItem.Type.CHESTPLATE));
                    map.put(ArmorItem.Type.HELMET, ArmorMaterials.LEATHER.value().getDefense(ArmorItem.Type.HELMET));
                    map.put(ArmorItem.Type.BODY, ArmorMaterials.LEATHER.value().getDefense(ArmorItem.Type.BODY));
                }),

                ArmorMaterials.LEATHER.value().enchantmentValue(),

                ArmorMaterials.LEATHER.value().equipSound(),

                () -> Ingredient.of(ItemTags.WOOL),

                List.of(
                        new ArmorMaterial.Layer(
                                ResourceLocation.fromNamespaceAndPath(Vinery.MOD_ID, "winemaker")
                        )
                ),

                ArmorMaterials.LEATHER.value().toughness(),

                ArmorMaterials.LEATHER.value().knockbackResistance()
        ));
    }

    public static void init() {
        ARMOR_MATERIALS.register();
    }
}
