package net.satisfy.vinery.core.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.satisfy.vinery.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

public class ChairEntity extends Entity {
    public ChairEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        if (passenger instanceof Player p) {
            BlockPos pos = GeneralUtil.getPreviousPlayerPosition(p, this);
            if (pos != null) {
                discard();
                return new Vec3(pos.getX() + 0.5D, pos.getY()+3.5D, pos.getZ() + 0.5D);
            }
        }


        discard();
        return super.getDismountLocationForPassenger(passenger);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        GeneralUtil.removeChairEntity(level(), blockPosition());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
        return new ClientboundAddEntityPacket(this,serverEntity);
    }
}
