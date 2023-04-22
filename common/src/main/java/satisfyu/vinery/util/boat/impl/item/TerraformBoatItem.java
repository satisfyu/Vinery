package satisfyu.vinery.util.boat.impl.item;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryExpectPlatform;
import satisfyu.vinery.util.boat.api.TerraformBoatType;
import satisfyu.vinery.util.boat.impl.entity.TerraformBoatEntity;
import satisfyu.vinery.util.boat.impl.entity.TerraformChestBoatEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * An {@linkplain Item item} that spawns a {@linkplain TerraformBoatEntity boat entity} with a given {@linkplain TerraformBoatType Terraform boat type}.
 */
public class TerraformBoatItem extends Item {
	private static final Predicate<Entity> RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

	private final ResourceLocation location;
	private final boolean chest;


	public TerraformBoatItem(ResourceLocation location, boolean chest, Properties settings) {
		super(settings);

		this.location = location;
		this.chest = chest;
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack stack = user.getItemInHand(hand);

		HitResult hitResult = Item.getPlayerPOVHitResult(world, user, ClipContext.Fluid.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(stack);
		}

		Vec3 rotationVec = user.getViewVector(1f);
		List<Entity> riders = world.getEntities(user, user.getBoundingBox().expandTowards(rotationVec.scale(5d)).inflate(1d), RIDERS);

		// Prevent collision with user
		if (!riders.isEmpty()) {
			Vec3 eyePos = user.getEyePosition();
			for (Entity entity : riders) {
				AABB box = entity.getBoundingBox().inflate(entity.getPickRadius());
				if (box.contains(eyePos)) {
					return InteractionResultHolder.pass(stack);
				}
			}
		}

		// Spawn boat entity
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			double x = hitResult.getLocation().x;
			double y = hitResult.getLocation().y;
			double z = hitResult.getLocation().z;

			Set<Map.Entry<ResourceKey<TerraformBoatType>, TerraformBoatType>> set = VineryExpectPlatform.entrySet();
			if(set.isEmpty()) Vinery.LOGGER.error("isEmpty");
			set.forEach(lol -> Vinery.LOGGER.error("Check if that works: " + lol.getKey()));

			String key = this.location.getPath();
			if(this.chest) key = key.replace("_chest", "");
			TerraformBoatType boatType = VineryExpectPlatform.get(new ResourceLocation(this.location.getNamespace(), key));

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

			boatEntity.setYRot(user.getYRot());

			if (!world.noCollision(boatEntity, boatEntity.getBoundingBox().inflate(-0.1d))) {
				return InteractionResultHolder.fail(stack);
			}
			
			if (!world.isClientSide()) {
				world.addFreshEntity(boatEntity);
				world.gameEvent(user, GameEvent.ENTITY_PLACE, BlockPos.containing(hitResult.getLocation()));

				if (!user.getAbilities().instabuild) {
					stack.shrink(1);
				}
			}

			user.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
		}

		return InteractionResultHolder.pass(stack);
	}
}
