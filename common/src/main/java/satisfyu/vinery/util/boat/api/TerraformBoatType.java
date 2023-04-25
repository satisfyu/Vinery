package satisfyu.vinery.util.boat.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import satisfyu.vinery.util.boat.impl.TerraformBoatTypeImpl;
import satisfyu.vinery.util.boat.impl.entity.TerraformBoatEntity;
import satisfyu.vinery.util.boat.impl.entity.TerraformChestBoatEntity;

public interface TerraformBoatType {
	boolean isRaft();
	Item getItem();
	Item getChestItem();
	Item getPlanks();

	public static class Builder {
		private boolean raft;
		private Item item;
		private Item chestItem;
		private Item planks;

		public TerraformBoatType build() {
			return new TerraformBoatTypeImpl(this.raft, this.item, this.chestItem, this.planks);
		}
		public Builder raft() {
			this.raft = true;
			return this;
		}

		public Builder item(Item item) {
			this.item = item;
			return this;
		}

		public Builder chestItem(Item chestItem) {
			this.chestItem = chestItem;
			return this;
		}

		public Builder planks(Item planks) {
			this.planks = planks;
			return this;
		}
	}
}
