package satisfyu.vinery.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ArmorMaterialRegistry {

    public static final ArmorMaterial WINEMAKER_ARMOR = new ArmorMaterial() {

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return ArmorMaterials.LEATHER.getDurabilityForType(type);
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return ArmorMaterials.LEATHER.getDefenseForType(type);
        }

        @Override
        public int getEnchantmentValue() {
            return ArmorMaterials.LEATHER.getEnchantmentValue();
        }

        @Override
        public SoundEvent getEquipSound() {
            return ArmorMaterials.LEATHER.getEquipSound();
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ItemTags.WOOL);
        }

        @Override
        public String getName() {
            return "winemaker";
        }

        @Override
        public float getToughness() {
            return ArmorMaterials.LEATHER.getToughness();
        }

        @Override
        public float getKnockbackResistance() {
            return ArmorMaterials.LEATHER.getKnockbackResistance();
        }
    };
}
