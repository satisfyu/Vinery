package daniking.vinery.item;

import daniking.vinery.VineryIdentifier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.util.Identifier;

public class StrawHatItem extends CustomModelArmorItem{


    public StrawHatItem(Settings settings) {
        super(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, settings);
    }

    @Override
    public Identifier getTexture() {
        return new VineryIdentifier("textures/item/straw_hat.png");
    }

    @Override
    public Float getOffset() {
        return -1.8f;
    }
}
