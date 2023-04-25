package satisfyu.vinery.util.boat.impl;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import satisfyu.vinery.util.boat.api.TerraformBoatType;

/**
 * A simple implementation of {@link TerraformBoatType}.
 */
public class TerraformBoatTypeImpl implements TerraformBoatType {
	private final boolean raft;
	private final Item item;
	private final Item chestItem;
	private final Item planks;

	public TerraformBoatTypeImpl(boolean raft, Item item, Item chestItem, Item planks) {
		this.raft = raft;
		this.item = item;
		this.chestItem = chestItem;
		this.planks = planks;
	}

	@Override
	public boolean isRaft() {
		return this.raft;
	}

	@Override
	public Item getItem() {
		return this.item;
	}

	@Override
	public Item getChestItem() {
		return this.chestItem;
	}

	@Override
	public Item getPlanks() {
		return this.planks;
	}
}
