package satisfyu.vinery.registry;

import de.cristelknight.doapi.client.render.feature.CustomArmorManager;
import de.cristelknight.doapi.client.render.feature.CustomArmorSet;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import satisfyu.vinery.client.VineryClient;
import satisfyu.vinery.client.model.StrawHatModel;
import satisfyu.vinery.client.model.WinemakerInner;
import satisfyu.vinery.client.model.WinemakerOuter;
import satisfyu.vinery.config.VineryConfig;
import satisfyu.vinery.item.WinemakerBoots;
import satisfyu.vinery.item.WinemakerChest;
import satisfyu.vinery.item.WinemakerHatItem;
import satisfyu.vinery.item.WinemakerLegs;
import satisfyu.vinery.util.VineryIdentifier;

import java.util.List;
import java.util.Map;

public class ArmorRegistry {
    public static void registerArmorModelLayers(){
        EntityModelLayerRegistry.register(StrawHatModel.LAYER_LOCATION, StrawHatModel::getTexturedModelData);
        EntityModelLayerRegistry.register(WinemakerInner.LAYER_LOCATION, WinemakerInner::createBodyLayer);
        EntityModelLayerRegistry.register(WinemakerOuter.LAYER_LOCATION, WinemakerOuter::createBodyLayer);
    }

    public static <T extends LivingEntity> void registerArmorModels(CustomArmorManager<T> armors, EntityModelSet modelLoader) {
        armors.addArmor(new CustomArmorSet<T>(ObjectRegistry.STRAW_HAT.get(), ObjectRegistry.WINEMAKER_APRON.get(), ObjectRegistry.WINEMAKER_LEGGINGS.get(), ObjectRegistry.WINEMAKER_BOOTS.get())
                .setTexture(new VineryIdentifier("winemaker"))
                .setOuterModel(new WinemakerOuter<>(modelLoader.bakeLayer(WinemakerOuter.LAYER_LOCATION)))
                .setInnerModel(new WinemakerInner<>(modelLoader.bakeLayer(WinemakerInner.LAYER_LOCATION)))
                .setHatModel(new StrawHatModel<>(modelLoader.bakeLayer(StrawHatModel.LAYER_LOCATION))));

    }

    public static  <T extends LivingEntity> void registerHatModels(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        models.put(ObjectRegistry.STRAW_HAT.get(), new StrawHatModel<>(modelLoader.bakeLayer(StrawHatModel.LAYER_LOCATION)));
    }

    public static void appendtooltip(List<Component> tooltip){
        if(!VineryConfig.DEFAULT.getConfig().enableWineMakerSetBonus()) return;
        Player player = VineryClient.getClientPlayer();
        if (player == null) return;
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        boolean helmetB = helmet.getItem() instanceof WinemakerHatItem;
        boolean chestplateB = chestplate.getItem() instanceof WinemakerChest;
        boolean leggingsB = leggings.getItem() instanceof WinemakerLegs;
        boolean bootsB = boots.getItem() instanceof WinemakerBoots;

        tooltip.add(Component.nullToEmpty(""));
        tooltip.add(Component.nullToEmpty(ChatFormatting.AQUA + I18n.get("tooltip.vinery.winemaker_armor")));
        tooltip.add(Component.nullToEmpty((helmetB ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.STRAW_HAT.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((chestplateB ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.WINEMAKER_APRON.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((leggingsB ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.WINEMAKER_LEGGINGS.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((bootsB ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.WINEMAKER_BOOTS.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty(""));
        tooltip.add(Component.nullToEmpty(ChatFormatting.GRAY + I18n.get("tooltip.vinery.winemaker_armor2")));
        tooltip.add(Component.nullToEmpty(((helmetB &&
                chestplateB &&
                leggingsB &&
                bootsB) ? ChatFormatting.DARK_GREEN.toString() : ChatFormatting.GRAY.toString()) + I18n.get("tooltip.vinery.winemaker_armor3")));
    }
}
