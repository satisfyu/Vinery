package net.satisfy.vinery.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.registry.ArmorRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class WinemakerHatItem extends ArmorItem {
    private final ResourceLocation hatTexture;

    public WinemakerHatItem(ArmorMaterial armorMaterial, Type type, Properties properties, ResourceLocation hatTexture) {
        super(armorMaterial, type, properties);
        this.hatTexture = hatTexture;
    }


    public ResourceLocation getHatTexture()
    {
        return hatTexture;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if(world != null && world.isClientSide()){
            ArmorRegistry.appendtooltip(tooltip);
        }
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return this.type.getSlot();
    }
}
