package net.satisfy.vinery.core.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.satisfy.vinery.client.model.StrawHatModel;
import net.satisfy.vinery.client.model.WinemakerBootsModel;
import net.satisfy.vinery.client.model.WinemakerChestplateModel;
import net.satisfy.vinery.client.model.WinemakerLeggingsModel;
import net.satisfy.vinery.core.item.WinemakerBootsItem;
import net.satisfy.vinery.core.item.WinemakerChestItem;
import net.satisfy.vinery.core.item.WinemakerHelmetItem;
import net.satisfy.vinery.core.item.WinemakerLegsItem;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ArmorRegistryClient {
    private static final Map<Item, StrawHatModel<?>> models = new HashMap<>();
    private static final Map<Item, WinemakerChestplateModel<?>> chestplateModels = new HashMap<>();
    private static final Map<Item, WinemakerLeggingsModel<?>> leggingsModels = new HashMap<>();
    private static final Map<Item, WinemakerBootsModel<?>> bootsModels = new HashMap<>();

    public static EntityModel<?> getHatModel(Item item, ModelPart baseHead) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        StrawHatModel<?> model = models.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.STRAW_HAT.get()) {
                return new StrawHatModel<>(modelSet.bakeLayer(StrawHatModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyHead(baseHead);
        }

        return model;
    }

    public static EntityModel<?> getChestplateModel(Item item, ModelPart body, ModelPart leftArm, ModelPart rightArm, ModelPart leftLeg, ModelPart rightLeg) {
        WinemakerChestplateModel<?> model = chestplateModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.WINEMAKER_APRON.get()) {
                return new WinemakerChestplateModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(WinemakerChestplateModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyBody(body, leftArm, rightArm, leftLeg, rightLeg);
        }

        return model;
    }

    public static EntityModel<?> getLeggingsModel(Item item, ModelPart rightLeg, ModelPart leftLeg) {
        WinemakerLeggingsModel<?> model = leggingsModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.WINEMAKER_LEGGINGS.get()) {
                return new WinemakerLeggingsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(WinemakerLeggingsModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyLegs(rightLeg, leftLeg);
        }

        return model;
    }

    public static EntityModel<?> getBootsModel(Item item, ModelPart rightLeg, ModelPart leftLeg) {
        WinemakerBootsModel<?> model = bootsModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.WINEMAKER_BOOTS.get()) {
                return new WinemakerBootsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(WinemakerBootsModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyLegs(rightLeg, leftLeg);
        }

        return model;
    }


    public static void appendToolTip(@NotNull List<Component> tooltip) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        boolean hasFullSet = helmet.getItem() instanceof WinemakerHelmetItem &&
                chestplate.getItem() instanceof WinemakerChestItem &&
                leggings.getItem() instanceof WinemakerLegsItem &&
                boots.getItem() instanceof WinemakerBootsItem;

        ArmorRegistry.setBonusActive = hasFullSet;

        tooltip.add(Component.nullToEmpty(""));
        tooltip.add(Component.nullToEmpty(ChatFormatting.DARK_GREEN + I18n.get("tooltip.vinery.armor.winemaker_armor0")));
        tooltip.add(Component.nullToEmpty((helmet.getItem() instanceof WinemakerHelmetItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.STRAW_HAT.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((chestplate.getItem() instanceof WinemakerChestItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.WINEMAKER_APRON.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((leggings.getItem() instanceof WinemakerLegsItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.WINEMAKER_LEGGINGS.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((boots.getItem() instanceof WinemakerBootsItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.WINEMAKER_BOOTS.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty(""));

        ChatFormatting color = hasFullSet ? ChatFormatting.GREEN : ChatFormatting.GRAY;
        tooltip.add(Component.nullToEmpty(color + I18n.get("tooltip.vinery.armor.winemaker_armor1")));
        tooltip.add(Component.nullToEmpty(color + I18n.get("tooltip.vinery.armor.winemaker_armor2")));
    }
}
