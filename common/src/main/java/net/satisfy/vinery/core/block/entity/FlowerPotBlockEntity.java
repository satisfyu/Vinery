package net.satisfy.vinery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;
import net.satisfy.vinery.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

public class FlowerPotBlockEntity extends BlockEntity {
    private Item flower;

    public FlowerPotBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.FLOWER_POT_ENTITY.get(), pos, state);
    }

    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt,provider);
        this.writeFlower(nbt, this.flower,provider);
    }

    public void loadAdditional(CompoundTag nbt,HolderLookup.Provider provider) {
        super.loadAdditional(nbt,provider);
        this.flower = this.readFlower(nbt,provider);
    }

    public void writeFlower(CompoundTag nbt, Item flower,HolderLookup.Provider provider) {
        CompoundTag nbtCompound = new CompoundTag();
        if (flower != null) {
            flower.getDefaultInstance().save(provider,nbtCompound);
        }

        nbt.put("flower", nbtCompound);
    }

    public Item readFlower(CompoundTag nbt,HolderLookup.Provider provider) {
        super.loadAdditional(nbt,provider);
        if (nbt.contains("flower")) {
            CompoundTag nbtCompound = nbt.getCompound("flower");
            if (!nbtCompound.isEmpty()) {
                return ItemStack.parseOptional(provider,nbtCompound).getItem();
            }
        }

        return null;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveWithoutMetadata(provider);
    }

    public void setChanged() {
        if (this.level != null && !this.level.isClientSide()) {
            Packet<ClientGamePacketListener> updatePacket = this.getUpdatePacket();

            for (ServerPlayer player : GeneralUtil.tracking((ServerLevel) this.level, this.getBlockPos())) {
                player.connection.send(updatePacket);
            }
        }

        super.setChanged();
    }
}

