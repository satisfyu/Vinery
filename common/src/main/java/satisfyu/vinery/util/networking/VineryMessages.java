package satisfyu.vinery.util.networking;

import dev.architectury.networking.NetworkManager;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.util.networking.packet.ItemStackSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;

public class VineryMessages {

    public static final ResourceLocation ITEM_SYNC = new VineryIdentifier("item_sync");

    public static void registerS2CPackets(){
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ITEM_SYNC, new ItemStackSyncS2CPacket());
    }

}
