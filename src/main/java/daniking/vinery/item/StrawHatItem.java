package daniking.vinery.item;

import daniking.vinery.VineryIdentifier;
import daniking.vinery.registry.VineryMaterials;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StrawHatItem extends CustomModelArmorItem implements WineMakerArmorItem {


    public StrawHatItem(Settings settings) {
        super(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.HEAD, settings);
    }

    @Override
    public Identifier getTexture() {
        return new VineryIdentifier("textures/item/straw_hat.png");
    }

    @Override
    public Float getOffset() {
        return -1.8f;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip(tooltip);
    }
}
