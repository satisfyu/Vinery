package net.satisfy.vinery.core.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.satisfy.vinery.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

public class ChairEntity extends Entity {
    private BlockPos seatPos;
    private boolean seatPosInit;

    public ChairEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    public void setSeatPos(BlockPos pos) {
        this.seatPos = pos.immutable();
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        if (passenger instanceof Player p) {
            BlockPos pos = GeneralUtil.getPreviousPlayerPosition(p, this);
            if (pos != null) {
                this.discard();
                return new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            }
        }
        this.discard();
        return super.getDismountLocationForPassenger(passenger);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        GeneralUtil.removeChairEntity(this.level(), this.seatPos != null ? this.seatPos : this.blockPosition());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("spx")) {
            int x = tag.getInt("spx");
            int y = tag.getInt("spy");
            int z = tag.getInt("spz");
            this.seatPos = new BlockPos(x, y, z);
            this.seatPosInit = true;
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        BlockPos p = this.seatPos != null ? this.seatPos : this.blockPosition();
        tag.putInt("spx", p.getX());
        tag.putInt("spy", p.getY());
        tag.putInt("spz", p.getZ());
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction move) {
        move.accept(passenger, getX(), getY(), getZ());
    }

    @Override
    public boolean isControlledByLocalInstance() {
        return false;
    }

    private float computeYawFromBlock() {
        BlockPos p = this.seatPos != null ? this.seatPos : this.blockPosition();
        BlockState s = level().getBlockState(p);
        float yaw = getYRot();
        if (s.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            yaw = s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot();
        } else if (s.hasProperty(BlockStateProperties.FACING)) {
            yaw = s.getValue(BlockStateProperties.FACING).toYRot();
        } else {
            for (Property<?> prop : s.getProperties()) {
                if (prop.getName().equals("facing") && prop instanceof net.minecraft.world.level.block.state.properties.DirectionProperty dir) {
                    yaw = s.getValue(dir).toYRot();
                    break;
                }
            }
        }
        if (s.hasProperty(BlockStateProperties.BED_PART) && s.getValue(BlockStateProperties.BED_PART).toString().equals("head")) {
            yaw += 180.0F;
        } else {
            for (Property<?> prop : s.getProperties()) {
                if (prop.getName().equals("part")) {
                    Object v = s.getValue(prop);
                    if (v.toString().equals("head")) {
                        yaw += 180.0F;
                        break;
                    }
                }
            }
        }
        return yaw;
    }

    @Override
    public void onPassengerTurned(Entity passenger) {
        if (passenger instanceof LivingEntity l) {
            float yaw = getYRot();
            l.setYBodyRot(yaw);
            l.yBodyRotO = yaw;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!seatPosInit) {
            this.seatPos = this.blockPosition();
            this.seatPosInit = true;
        }
        float target = computeYawFromBlock();
        if (getYRot() != target) {
            setYRot(target);
            yRotO = target;
        }
        if (!getPassengers().isEmpty()) {
            for (Entity e : getPassengers()) {
                if (e instanceof Player p) {
                    if (!level().isClientSide) p.setDeltaMovement(Vec3.ZERO);
                    float yaw = getYRot();
                    p.setYBodyRot(yaw);
                    p.yBodyRotO = yaw;
                }
            }
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity server) {
        return new ClientboundAddEntityPacket(this, server);
    }
}
