package satisfyu.vinery.item;

import de.cristelknight.doapi.item.CustomHatItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.registry.VineryMaterials;

import java.util.List;

public class StrawHatItem extends CustomHatItem implements WineMakerArmorItem {


    public StrawHatItem(Properties settings) {
        super(VineryMaterials.VINEMAKER_ARMOR, EquipmentSlot.HEAD, settings);
    }

    @Override
    public ResourceLocation getTexture() {
        return new VineryIdentifier("textures/item/straw_hat.png");
    }

    @Override
    public Float getOffset() {
        return -1.85f;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip(tooltip);
    }
}
