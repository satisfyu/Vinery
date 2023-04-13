package satisfyu.vinery.block.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import satisfyu.vinery.registry.VineryBlockEntityTypes;

import java.util.ArrayList;
import java.util.List;

public class FlowerBoxBlockEntity extends BlockEntity {

	private DefaultedList<ItemStack> flowers;

	public FlowerBoxBlockEntity(BlockPos pos, BlockState state) {
		super(VineryBlockEntityTypes.FLOWER_BOX_ENTITY, pos, state);
		this.flowers = DefaultedList.ofSize(2, ItemStack.EMPTY);
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		Inventories.writeNbt(nbt, this.flowers);
		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.flowers = DefaultedList.ofSize(3, ItemStack.EMPTY);
		Inventories.readNbt(nbt, this.flowers);
	}

	public void addFlower(ItemStack stack, int slot){
		flowers.set(slot, stack);
		markDirty();
	}

	public ItemStack removeFlower(int slot){
		ItemStack stack = flowers.set(slot, ItemStack.EMPTY);
		markDirty();
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