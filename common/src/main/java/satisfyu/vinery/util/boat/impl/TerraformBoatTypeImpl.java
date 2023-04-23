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
	private final ResourceLocation item;
	private final ResourceLocation chestItem;
	private final ResourceLocation planks;

	public TerraformBoatTypeImpl(boolean raft, ResourceLocation item, ResourceLocation chestItem, ResourceLocation planks) {
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
		return BuiltInRegistries.ITEM.get(this.item);
	}

	@Override
	public Item getChestItem() {
		return BuiltInRegistries.ITEM.get(this.chestItem);
	}

	@Override
	public Item getPlanks() {
		return BuiltInRegistries.ITEM.get(this.planks);
	}

	@Override
	public ResourceLocation getKey() {
		return item;
	}
}
