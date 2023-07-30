package satisfyu.vinery.network.packet;


import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import satisfyu.vinery.client.shader.Shader;
import satisfyu.vinery.util.ShaderUtils;

public class ShaderS2CPacket implements NetworkManager.NetworkReceiver {
    @Override
    public void receive(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        boolean activate = buf.readBoolean();
        Minecraft client = Minecraft.getInstance();
        client.execute(() -> {
            ShaderUtils.enabled = activate;
            ShaderUtils.load(activate ? ShaderUtils.getRandomShader() : ShaderUtils.getShader(Shader.NONE));
            if (ShaderUtils.shader != null) {
                ShaderUtils.shader.resize(client.getWindow().getWidth(), client.getWindow().getHeight());
            }
        });
    }
}

