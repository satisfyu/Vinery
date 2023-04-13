package satisfyu.vinery.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.util.Identifier;

public abstract class CustomModelArmorItem extends ArmorItem {
    public CustomModelArmorItem(ArmorMaterial material, ArmorItem.Type type, Settings settings) {
        super(material, type, settings);
    }

    public abstract Identifier getTexture();

    public abstract Float getOffset();
}
