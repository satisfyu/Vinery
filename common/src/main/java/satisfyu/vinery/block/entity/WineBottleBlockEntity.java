package satisfyu.vinery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.vinery.registry.VineryBlockEntityTypes;
import satisfyu.vinery.util.GeneralUtil;

public class WineBottleBlockEntity extends BlockEntity {
    private  int count;
    private int maxCount;

    public WineBottleBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 3);
    }

    public WineBottleBlockEntity(BlockPos pos, BlockState state, int maxCount) {
        super(VineryBlockEntityTypes.WINE_BOTTLE_ENTITY.get(), pos, state);
        this.count = 1;
        this.maxCount = maxCount;
    }

    public void addWine(){
        count++;
        setChanged();
    }

    public void removeWine(){
        count--;
        setChanged();
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
    public void saveAdditional(CompoundTag nbt) {
        nbt.putInt("count", this.count);
        nbt.putInt("max_count", this.maxCount);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.count = nbt.getInt("count");
        this.maxCount = nbt.getInt("max_count");
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void setChanged() {
        if(level != null && !level.isClientSide()) {
            Packet<ClientGamePacketListener> updatePacket = getUpdatePacket();

            for (ServerPlayer player : GeneralUtil.tracking((ServerLevel) level, getBlockPos())) {
                player.connection.send(updatePacket);
            }
        }
        super.setChanged();
    }

}
