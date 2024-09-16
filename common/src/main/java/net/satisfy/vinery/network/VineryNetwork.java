package net.satisfy.vinery.network;


import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.satisfy.vinery.client.shader.Shader;
import net.satisfy.vinery.network.packet.ShaderS2CPacket;
import net.satisfy.vinery.util.ShaderUtils;

public class VineryNetwork {

    public static void registerS2CPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ShaderS2CPacket.PACKET_ID, ShaderS2CPacket.PACKET_CODEC, (payload, context) -> {
            boolean activate = payload.activate();

            Minecraft client = Minecraft.getInstance();
            client.execute(() -> {
                ShaderUtils.enabled = activate;
                ShaderUtils.load(activate ? ShaderUtils.getRandomShader() : ShaderUtils.getShader(Shader.NONE));
                if (ShaderUtils.shader != null) {
                    ShaderUtils.shader.resize(client.getWindow().getWidth(), client.getWindow().getHeight());
                }
            });
        });
    }
}
