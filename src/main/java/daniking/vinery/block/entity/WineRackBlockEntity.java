package daniking.vinery.block.entity;

import daniking.vinery.block.WineRackStorageBlock;
import daniking.vinery.registry.VineryBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WineRackBlockEntity extends LootableContainerBlockEntity {
	private DefaultedList<ItemStack> inventory;
	private ViewerCountManager stateManager;
	
	public WineRackBlockEntity(BlockPos pos, BlockState state) {
		super(VineryBlockEntityTypes.WINE_RACK_ENTITY, pos, state);
		this.inventory = DefaultedList.ofSize(18, ItemStack.EMPTY);
		this.stateManager = new ViewerCountManager() {
			
			@Override
			protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
				WineRackBlockEntity.this.setOpen(state, true);
			}
			
			@Override
			protected void onContainerClose(World world, BlockPos pos, BlockState state) {
				WineRackBlockEntity.this.setOpen(state, false);
			}
			
			@Override
			protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
			}
			
			@Override
			protected boolean isPlayerViewing(PlayerEntity player) {
				if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
					Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
					return inventory == WineRackBlockEntity.this;
				} else {
					return false;
				}
			}
			
		};
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		if (!this.serializeLootTable(nbt)) {
			Inventories.writeNbt(nbt, this.inventory);
		}
		
	}
	
	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		if (!this.deserializeLootTable(nbt)) {
			Inventories.readNbt(nbt, this.inventory);
		}
	}
	
	@Override
	public int size() {
		return 18;
	}
	
	@Override
	protected DefaultedList<ItemStack> getInvStackList() {
		return this.inventory;
	}
	
	@Override
	protected void setInvStackList(DefaultedList<ItemStack> list) {
		this.inventory = list;
	}
	
	@Override
	protected Text getContainerName() {
		return new TranslatableText("container.wine_rack");
	}
	
	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X2, syncId, playerInventory, this, 2);
	}
	
	@Override
	public void onOpen(PlayerEntity player) {
		if (!this.removed && !player.isSpectator()) {
			this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
		}
	}
	
	@Override
	public void onClose(PlayerEntity player) {
		if (!this.removed && !player.isSpectator()) {
			this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
		}
	}
	
	public void tick() {
		if (!this.removed) {
			this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
		}
	}
	
	public void setOpen(BlockState state, boolean open) {
		if(state.getBlock() instanceof WineRackStorageBlock rack) rack.playSound(world, this.getPos(), open);
		this.world.setBlockState(this.getPos(), state.with(Properties.OPEN, open), 3);
	}
	
}