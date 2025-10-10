package net.satisfy.vinery.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.vinery.core.Vinery;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ArmorMaterialRegistry {
    private static final ArmorMaterial LEATHER = ArmorMaterials.LEATHER.value();
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Vinery.MOD_ID, Registries.ARMOR_MATERIAL);
    public static final RegistrySupplier<ArmorMaterial> WINEMAKER_ARMOR = ARMOR_MATERIALS.register("winemaker",
            ()-> new ArmorMaterial(LEATHER.defense(),LEATHER.enchantmentValue(),LEATHER.equipSound(), LEATHER.repairIngredient(), LEATHER.layers(),LEATHER.toughness(),LEATHER.knockbackResistance()));
}
