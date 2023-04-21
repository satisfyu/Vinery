package satisfyu.vinery.util.boat.impl.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import satisfyu.vinery.VineryExpectPlatform;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;

public interface TerraformBoatHolder {
	static final String BOAT_KEY = "VineryBoat";

	TerraformBoatType getTerraformBoat();

	void setTerraformBoat(TerraformBoatType boat);

	default boolean hasValidTerraformBoat() {
		return this.getTerraformBoat() != null;
	}

	default void readTerraformBoatFromNbt(CompoundTag nbt) {
		ResourceLocation id = ResourceLocation.tryParse(nbt.getString(BOAT_KEY));
		if (id != null) {
			TerraformBoatType boat = VineryExpectPlatform.get(id);
			if (boat != null) {
				this.setTerraformBoat(boat);
			}
		}
	}

	default void writeTerraformBoatToNbt(CompoundTag nbt) {
		ResourceLocation boatId = VineryExpectPlatform.getId(this.getTerraformBoat());
		if (boatId != null) {
			nbt.putString(BOAT_KEY, boatId.toString());
		}
	}

	default Boat.Type getImpersonatedBoatType() {
		return this.getTerraformBoat().isRaft() ? Boat.Type.BAMBOO : Boat.Type.OAK;
	}
}
