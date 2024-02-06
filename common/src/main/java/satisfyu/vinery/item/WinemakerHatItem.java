package satisfyu.vinery.item;

import de.cristelknight.doapi.common.item.CustomHatItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.registry.ArmorMaterialRegistry;
import satisfyu.vinery.registry.ArmorRegistry;

import java.util.List;

public class WinemakerHatItem extends CustomHatItem {


    public WinemakerHatItem(Properties settings) {
        super(ArmorMaterialRegistry.WINEMAKER_ARMOR, Type.HELMET, settings);
    }

    @Override
    public ResourceLocation getTexture() {
        return new VineryIdentifier("textures/entity/straw_hat.png");
    }

    @Override
    public Float getOffset() {
        return -1.85f;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if(world != null && world.isClientSide()){
            ArmorRegistry.appendtooltip(tooltip);
        }
    }
}
