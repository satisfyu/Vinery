package net.satisfy.vinery.neoforge.core.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.core.registry.MobEffectRegistry;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Vinery.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    private static boolean hasDoubleJumped = false;
    private static boolean wasOnGround = true;
    private static boolean wasSpacePressed = false;

    @SubscribeEvent
    public static void onInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null || mc.level == null) {
            return;
        }

        if (!player.hasEffect(MobEffectRegistry.getHolder(MobEffectRegistry.IMPROVED_JUMP_BOOST))) {
            return;
        }

        if (!canJump(player)) {
            return;
        }

        boolean spacePressed = event.getKey() == GLFW.GLFW_KEY_SPACE && event.getAction() == GLFW.GLFW_PRESS;

        if (player.onGround()) {
            hasDoubleJumped = false;
            wasOnGround = true;
        } else if (wasOnGround) {
            wasOnGround = false;
        }

        if (spacePressed && !wasSpacePressed && !player.onGround() && !hasDoubleJumped && !wasOnGround) {
            performDoubleJump(player);
            hasDoubleJumped = true;
        }

        wasSpacePressed = spacePressed;
    }

    private static void performDoubleJump(Player player) {
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x, 0.42, motion.z);
        player.hasImpulse = true;
    }

    private static boolean canJump(LocalPlayer player) {
        return !wearingUsableElytra(player) && !player.isFallFlying() && !player.isPassenger()
                && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION);
    }

    private static boolean wearingUsableElytra(LocalPlayer player) {
        ItemStack chestItemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isFlyEnabled(chestItemStack);
    }
}