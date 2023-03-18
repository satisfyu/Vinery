package satisfyu.vinery.block.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import satisfyu.vinery.registry.VineryBlockEntityTypes;

public class WineBottleBlockEntity extends BlockEntity {
    private  int count;
    private int maxCount;

    public WineBottleBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 3);
    }

    public WineBottleBlockEntity(BlockPos pos, BlockState state, int maxCount) {
        super(VineryBlockEntityTypes.WINE_BOTTLE_ENTITY, pos, state);
        this.count = 1;
        this.maxCount = maxCount;
    }

    public void addWine(){
        count++;
        markDirty();
    }

    public void removeWine(){
        count--;
        markDirty();
    }

    public boolean isFull() {
        return count >= maxCount;
    }

    public int getCount() {
        return count;
    }

    public int getMaxCount() {
        return maxCount;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("count", this.count);
        nbt.putInt("max_count", this.maxCount);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.count = nbt.getInt("count");
        this.maxCount = nbt.getInt("max_count");
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Override
    public void markDirty() {
        if(world != null && !world.isClient()) {
            Packet<ClientPlayPacketListener> updatePacket = toUpdatePacket();

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                player.networkHandler.sendPacket(updatePacket);
            }
        }
        super.markDirty();
    }

}
