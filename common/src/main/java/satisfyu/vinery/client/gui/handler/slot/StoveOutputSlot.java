package satisfyu.vinery.client.gui.handler.slot;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import satisfyu.vinery.block.entity.WoodFiredOvenBlockEntity;

public class StoveOutputSlot extends Slot {
	private final Player player;

	private int amount;

	public StoveOutputSlot(Player player, Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
		this.player = player;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack remove(int amount) {
		if (this.hasItem()) {
			this.amount += Math.min(amount, this.getItem().getCount());
		}
		return super.remove(amount);
	}

	@Override
	public void onTake(Player player, ItemStack stack) {
		this.checkTakeAchievements(stack);
		super.onTake(player, stack);
	}

	@Override
	protected void onQuickCraft(ItemStack stack, int amount) {
		this.amount += amount;
		this.checkTakeAchievements(stack);
	}

	@Override
	protected void checkTakeAchievements(ItemStack stack) {
		stack.onCraftedBy(this.player.level, this.player, this.amount);
		if (this.player instanceof ServerPlayer && this.container instanceof WoodFiredOvenBlockEntity
				&& player.level instanceof ServerLevel) {
			((WoodFiredOvenBlockEntity) this.container).dropExperience((ServerLevel) this.player.level,
					player.position());
		}
		this.amount = 0;
	}
}
