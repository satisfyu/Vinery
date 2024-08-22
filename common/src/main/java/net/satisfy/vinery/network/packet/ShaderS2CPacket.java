package net.satisfy.vinery.network.packet;


import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.vinery.util.VineryIdentifier;
import org.jetbrains.annotations.NotNull;

public record ShaderS2CPacket(boolean activate) implements CustomPacketPayload {
    public static final ResourceLocation PACKET_RESOURCE_LOCATION = VineryIdentifier.of("shader_s2c");
    public static final CustomPacketPayload.Type<ShaderS2CPacket> PACKET_ID = new CustomPacketPayload.Type<>(PACKET_RESOURCE_LOCATION);

    public static final StreamCodec<RegistryFriendlyByteBuf, ShaderS2CPacket> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ShaderS2CPacket::activate,
            ShaderS2CPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}

