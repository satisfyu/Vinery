package net.satisfy.vinery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;
import net.satisfy.vinery.core.registry.GrapeTypeRegistry;
import net.satisfy.vinery.core.util.GrapeType;

public class LatticeBlockEntity extends BlockEntity {
    private int age = 0;
    private GrapeType grape = GrapeTypeRegistry.NONE;

    public LatticeBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.LATTICE.get(), pos, state);
    }

    public void setAge(int age) {
        this.age = age;
        setChanged();
        sync();
    }

    public int getAge() {
        return age;
    }

    public void setGrapeType(GrapeType grape) {
        this.grape = grape;
        setChanged();
        sync();
    }

    public GrapeType getGrapeType() {
        return grape;
    }

    public void toggleAge() {
        this.age = (age + 1) % 5;
        setChanged();
        sync();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.age = tag.getInt("Age");
        this.grape = GrapeType.fromString(tag.getString("Grape"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("Age", age);
        tag.putString("Grape", grape.getSerializedName());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void sync() {
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
}
