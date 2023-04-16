package satisfyu.vinery.block.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.vinery.registry.VineryBlockEntityTypes;
import satisfyu.vinery.util.GeneralUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class FlowerBoxBlockEntity extends BlockEntity {

	private NonNullList<ItemStack> flowers;

	public FlowerBoxBlockEntity(BlockPos pos, BlockState state) {
		super(VineryBlockEntityTypes.FLOWER_BOX_ENTITY.get(), pos, state);
		this.flowers = NonNullList.withSize(2, ItemStack.EMPTY);
	}

	@Override
	public void saveAdditional(CompoundTag nbt) {
		ContainerHelper.saveAllItems(nbt, this.flowers);
		super.saveAdditional(nbt);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.flowers = NonNullList.withSize(3, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(nbt, this.flowers);
	}

	public void addFlower(ItemStack stack, int slot){
		flowers.set(slot, stack);
		setChanged();
	}

	public ItemStack removeFlower(int slot){
		ItemStack stack = flowers.set(slot, ItemStack.EMPTY);
		setChanged();
		return stack;
	}

	public ItemStack getFlower(int slot) {
		return flowers.get(slot);
	}

	public boolean isSlotEmpty(int slot) {
		return slot < flowers.size() && flowers.get(slot).isEmpty();
	}

	public Item[] getFlowers() {
		List<Item> items = new ArrayList<>();
		for (ItemStack stack : flowers) {
			if (!stack.isEmpty()) {
				items.add(stack.getItem());
			}
		}
		return items.toArray(new Item[0]);
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