package net.satisfy.vinery.network;


import dev.architectury.networking.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.vinery.network.packet.ShaderS2CPacket;
import net.satisfy.vinery.util.VineryIdentifier;

public class VineryNetwork {
    public static final ResourceLocation SHADER_S2C = new VineryIdentifier("shader");

    public static void registerS2CPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, SHADER_S2C, new ShaderS2CPacket());
    }
}
