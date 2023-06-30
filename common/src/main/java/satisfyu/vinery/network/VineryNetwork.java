package satisfyu.vinery.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.network.packet.ShaderS2CPacket;

public class VineryNetwork {
    public static final ResourceLocation SHADER_S2C = new VineryIdentifier("shader");

    public static void registerC2SPackets() {

    }

    public static void registerS2CPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, SHADER_S2C, new ShaderS2CPacket());
    }
}
