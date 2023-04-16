package satisfyu.vinery.util.networking.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import satisfyu.vinery.block.entity.StorageBlockEntity;

public class ItemStackSyncS2CPacket implements NetworkManager.NetworkReceiver {


    @Override
    public void receive(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        int size = buf.readInt();
        NonNullList<ItemStack> list = NonNullList.withSize(size, ItemStack.EMPTY);
        for(int i = 0; i < size; i++) {
            list.set(i, buf.readItem());
        }
        BlockPos position = buf.readBlockPos();


        if(context.getPlayer().level.getBlockEntity(position) instanceof StorageBlockEntity blockEntity) {
            blockEntity.setInventory(list);
        }
    }
}
