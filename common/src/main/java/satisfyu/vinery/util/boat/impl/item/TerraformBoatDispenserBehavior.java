package satisfyu.vinery.util.boat.impl.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.api.TerraformBoatTypeRegistry;
import satisfyu.vinery.util.boat.impl.entity.TerraformBoatEntity;
import satisfyu.vinery.util.boat.impl.entity.TerraformChestBoatEntity;

/**
 * A {@linkplain DispenseItemBehavior dispenser behavior} that spawns a {@linkplain TerraformBoatEntity boat entity} with a given {@linkplain TerraformBoatType Terraform boat type}.
 */
public class TerraformBoatDispenserBehavior extends DefaultDispenseItemBehavior {
	private static final DispenseItemBehavior FALLBACK_BEHAVIOR = new DefaultDispenseItemBehavior();
	private static final float OFFSET_MULTIPLIER = 1.125F;

	private final ResourceKey<TerraformBoatType> boatKey;
	private final boolean chest;

	/**
	 * @param boatKey a {@linkplain ResourceKey registry key} for the {@linkplain TerraformBoatType Terraform boat type} that should be spawned by this dispenser behavior
	 * @param chest whether the boat contains a chest
	 */
	public TerraformBoatDispenserBehavior(ResourceKey<TerraformBoatType> boatKey, boolean chest) {
		this.boatKey = boatKey;
		this.chest = chest;
	}

	@Override
	public ItemStack execute(BlockSource pointer, ItemStack stack) {
		Direction facing = pointer.getBlockState().getValue(DispenserBlock.FACING);

		double x = pointer.x() + facing.getStepX() * OFFSET_MULTIPLIER;
		double y = pointer.y() + facing.getStepY() * OFFSET_MULTIPLIER;
		double z = pointer.z() + facing.getStepZ() * OFFSET_MULTIPLIER;

		Level world = pointer.getLevel();
		BlockPos pos = pointer.getPos().relative(facing);

		if (world.getFluidState(pos).is(FluidTags.WATER)) {
			y += 1;
		} else if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.below()).is(FluidTags.WATER)) {
			return FALLBACK_BEHAVIOR.dispense(pointer, stack);
		}

		TerraformBoatType boatType = TerraformBoatTypeRegistry.INSTANCE.get(this.boatKey.location());
		Boat boatEntity;

		if (this.chest) {
			TerraformChestBoatEntity chestBoat = new TerraformChestBoatEntity(world, x, y, z);
			chestBoat.setTerraformBoat(boatType);
			boatEntity = chestBoat;
		} else {
			TerraformBoatEntity boat = new TerraformBoatEntity(world, x, y, z);
			boat.setTerraformBoat(boatType);
			boatEntity = boat;
		}

		boatEntity.setYRot(facing.toYRot());

		world.addFreshEntity(boatEntity);

		stack.shrink(1);
		return stack;
	}
}
