package satisfyu.vinery.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.registry.VineryMaterials;

import java.util.List;

public class StrawHatItem extends CustomModelArmorItem implements WineMakerArmorItem {


    public StrawHatItem(Settings settings) {
        super(VineryMaterials.VINEMAKER_ARMOR, Type.HELMET, settings);
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
