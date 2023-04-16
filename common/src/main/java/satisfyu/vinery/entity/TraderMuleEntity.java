package satisfyu.vinery.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.registry.VineryEntites;

public class TraderMuleEntity extends TraderLlama {
	
	public TraderMuleEntity(EntityType<? extends TraderMuleEntity> entityType, Level world) {
		super(entityType, world);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.0));
		this.goalSelector.addGoal(3, new PanicGoal(this, 1.0));
		this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
		this.goalSelector.addGoal(5, new TemptGoal(this, 1.0, Ingredient.of(Items.HAY_BLOCK), false));
		this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	@Override
	public TraderMuleEntity getBreedOffspring(ServerLevel serverWorld, AgeableMob passiveEntity) {
		return VineryEntites.MULE.get().create(this.level);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.DONKEY_AMBIENT;
	}
	
	@Override
	protected SoundEvent getAngrySound() {
		super.getAngrySound();
		return SoundEvents.DONKEY_ANGRY;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.DONKEY_DEATH;
	}
	
	@Override
	@Nullable
	protected SoundEvent getEatingSound() {
		return SoundEvents.DONKEY_EAT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		super.getHurtSound(source);
		return SoundEvents.DONKEY_HURT;
	}
	
	
}