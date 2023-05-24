package satisfyu.vinery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RottenCherryItem extends SnowballItem {
	public RottenCherryItem(Item.Properties settings) {
		super(settings);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		world.playSound((Player) null, user.getX(), user.getY(), user.getZ(), SoundEvents.SNOWBALL_THROW,
				SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!world.isClientSide) {
			Snowball snowballEntity = new Snowball(world, user);
			snowballEntity.setItem(itemStack);
			snowballEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
			world.addFreshEntity(snowballEntity);
		}
		user.awardStat(Stats.ITEM_USED.get(this));
		if (!user.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
	}

	protected void onEntityHit(Snowball snowball, EntityHitResult entityHitResult) {
		if (entityHitResult.getEntity() instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entityHitResult.getEntity();
			livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 50, 1));
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
		tooltip.add(new TranslatableComponent("item.vinery.rottencherry.tooltip").withStyle(ChatFormatting.ITALIC,
				ChatFormatting.GRAY));
	}
}
