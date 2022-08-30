package daniking.vinery.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.passive.TraderLlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TraderMuleEntity extends TraderLlamaEntity implements IAnimatable {
	private final AnimationFactory animationFactory = new AnimationFactory(this);
	
	public TraderMuleEntity(EntityType<? extends TraderMuleEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new HorseBondWithPlayerGoal(this, 1.0));
		this.goalSelector.add(3, new EscapeDangerGoal(this, 1.0));
		this.goalSelector.add(4, new AnimalMateGoal(this, 1.0));
		this.goalSelector.add(5, new TemptGoal(this, 1.0, Ingredient.ofItems(Items.HAY_BLOCK), false));
		this.goalSelector.add(6, new FollowParentGoal(this, 1.0));
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
		this.goalSelector.add(9, new LookAroundGoal(this));
	}
	
	private PlayState animControllerMain(AnimationEvent<?> e) {
		if (this.getTemper() > 0) {
			e.getController().setAnimation(new AnimationBuilder().addAnimation("fight / roar", false));
		} else if (this.isEatingGrass()) {
			e.getController().setAnimation(new AnimationBuilder().addAnimation("eat", false).addAnimation("eat2", true));
		} else if (e.isMoving()) {
			e.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
		} else {
			e.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		}
		return PlayState.CONTINUE;
	}
	
	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 3F, this::animControllerMain));
	}
	
	@Override
	public AnimationFactory getFactory() {
		return animationFactory;
	}
	
	@Override
	protected LlamaEntity createChild() {
		return null;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.ENTITY_DONKEY_AMBIENT;
	}
	
	@Override
	protected SoundEvent getAngrySound() {
		super.getAngrySound();
		return SoundEvents.ENTITY_DONKEY_ANGRY;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.ENTITY_DONKEY_DEATH;
	}
	
	@Override
	@Nullable
	protected SoundEvent getEatSound() {
		return SoundEvents.ENTITY_DONKEY_EAT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		super.getHurtSound(source);
		return SoundEvents.ENTITY_DONKEY_HURT;
	}
	
	
}