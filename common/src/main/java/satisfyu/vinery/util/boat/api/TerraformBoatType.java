package satisfyu.vinery.util.boat.api;

import net.minecraft.world.item.Item;
import satisfyu.vinery.util.boat.impl.TerraformBoatTypeImpl;
import satisfyu.vinery.util.boat.impl.entity.TerraformBoatEntity;
import satisfyu.vinery.util.boat.impl.entity.TerraformChestBoatEntity;

/**
 * An interface representing a Terraform boat.
 */
public interface TerraformBoatType {
	/**
	 * {@return whether this boat is a raft with a lower {@linkplain net.minecraft.world.entity.vehicle.Boat#getPassengersRidingOffset() mounted height offset}}
	 */
	boolean isRaft();

	/**
	 * {@return the {@linkplain net.minecraft.world.entity.vehicle.Boat#getPickResult() pick stack} and {@linkplain Item item} dropped when the {@linkplain TerraformBoatEntity boat entity} is broken}
	 */
	Item getItem();

	/**
	 * {@return the {@linkplain net.minecraft.world.entity.vehicle.Boat#getPickResult() pick stack} and {@linkplain Item item} dropped when the {@linkplain TerraformChestBoatEntity chest boat entity} is broken}
	 */
	Item getChestItem();

	/**
	 * {@return the planks {@linkplain Item item} dropped when the {@linkplain TerraformBoatEntity boat entity} or {@linkplain TerraformChestBoatEntity chest boat entity} is destroyed into planks and sticks}
	 */
	Item getPlanks();

	/**
	 * A builder for {@linkplain TerraformBoatType Terraform boat types}.
	 * 
	 * <p>To build a Terraform boat type:
	 * 
	 * <pre>{@code
	 *     TerraformBoatType boat = new TerraformBoatType.Builder()
	 *         .item(ExampleModItems.MAHOGANY_BOAT)
	 *         .build();
	 * }</pre>
	 */
	public static class Builder {
		private boolean raft;
		private Item item;
		private Item chestItem;
		private Item planks;

		public TerraformBoatType build() {
			return new TerraformBoatTypeImpl(this.raft, this.item, this.chestItem, this.planks);
		}

		/**
		 * @see TerraformBoatType#isRaft
		 */
		public Builder raft() {
			this.raft = true;
			return this;
		}

		/**
		 * @see TerraformBoatType#getItem
		 */
		public Builder item(Item item) {
			this.item = item;
			return this;
		}

		/**
		 * @see TerraformBoatType#getChestItem
		 */
		public Builder chestItem(Item chestItem) {
			this.chestItem = chestItem;
			return this;
		}

		/**
		 * @see TerraformBoatType#getPlanks
		 */
		public Builder planks(Item planks) {
			this.planks = planks;
			return this;
		}
	}
}
