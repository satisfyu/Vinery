package daniking.vinery.item;

import daniking.vinery.client.ClientSetup;
import daniking.vinery.registry.ObjectRegistry;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public interface WineMakerArmorItem {

    default void tooltip(List<Text> tooltip){
        PlayerEntity player = ClientSetup.getClientPlayer();
        if (player == null) return;
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        boolean helmetB = helmet != null && helmet.getItem() instanceof WineMakerArmorItem;
        boolean chestplateB = chestplate != null && chestplate.getItem() instanceof WineMakerArmorItem;
        boolean leggingsB = leggings != null && leggings.getItem() instanceof WineMakerArmorItem;
        boolean bootsB = boots != null && boots.getItem() instanceof WineMakerArmorItem;

        tooltip.add(Text.of(""));
        tooltip.add(Text.of(Formatting.AQUA + I18n.translate("vinery.tooltip.winemaker_armor")));
        tooltip.add(Text.of((helmetB ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ObjectRegistry.STRAW_HAT.getName().getString() + "]"));
        tooltip.add(Text.of((chestplateB ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ObjectRegistry.VINEMAKER_APRON.getName().getString() + "]"));
        tooltip.add(Text.of((leggingsB ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ObjectRegistry.VINEMAKER_LEGGINGS.getName().getString() + "]"));
        tooltip.add(Text.of((bootsB ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ObjectRegistry.VINEMAKER_BOOTS.getName().getString() + "]"));
        tooltip.add(Text.of(""));
        tooltip.add(Text.of(Formatting.GRAY + I18n.translate("vinery.tooltip.winemaker_armor2")));
        tooltip.add(Text.of(((helmetB &&
                chestplateB &&
                leggingsB &&
                bootsB) ? Formatting.DARK_GREEN.toString() : Formatting.GRAY.toString()) + I18n.translate("vinery.tooltip.winemaker_armor3")));
    }

}
