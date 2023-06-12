package satisfyu.vinery.item;

import de.cristelknight.doapi.item.CustomArmorModelItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public abstract class CustomModelArmorItem extends CustomArmorModelItem {
    public CustomModelArmorItem(ArmorMaterial material, EquipmentSlot type, Properties settings) {
        super(material, type, settings);
    }

    public abstract ResourceLocation getTexture();

    public abstract Float getOffset();
}
