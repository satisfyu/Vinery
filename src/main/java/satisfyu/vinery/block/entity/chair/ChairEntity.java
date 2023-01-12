package satisfyu.vinery.block.entity.chair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ChairEntity extends Entity {
    public ChairEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        if(passenger instanceof PlayerEntity p) {
            BlockPos pos = ChairUtil.getPreviousPlayerPosition(p, this);
            if(pos != null) {
                discard();
                return new Vec3d(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
            }
        }
        discard();
        return super.updatePassengerForDismount(passenger);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        ChairUtil.removeChairEntity(world, getBlockPos());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
