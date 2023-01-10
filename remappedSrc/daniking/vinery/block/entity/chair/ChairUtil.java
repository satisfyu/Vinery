package daniking.vinery.block.entity.chair;

import com.mojang.datafixers.util.Pair;
import daniking.vinery.registry.VineryBlockEntityTypes;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class ChairUtil {
    private static final Map<ResourceLocation, Map<BlockPos, Pair<ChairEntity,BlockPos>>> CHAIRS = new HashMap<>();

     public static InteractionResult onUse(Level world, Player player, InteractionHand hand, BlockHitResult hit, double extraHeight) {
        if(world.isClientSide) return InteractionResult.PASS;
        if(player.isShiftKeyDown()) return InteractionResult.PASS;
        if(ChairUtil.isPlayerSitting(player)) return InteractionResult.PASS;
        if(hit.getDirection() == Direction.DOWN) return InteractionResult.PASS;
        BlockPos hitPos = hit.getBlockPos();
        if(!ChairUtil.isOccupied(world, hitPos) && player.getItemInHand(hand).isEmpty()) {
            ChairEntity chair = VineryBlockEntityTypes.CHAIR.create(world);
            chair.moveTo(hitPos.getX() + 0.5D, hitPos.getY() + 0.25D + extraHeight, hitPos.getZ() + 0.5D, 0, 0);
            if(ChairUtil.addChairEntity(world, hitPos, chair, player.blockPosition())) {
                world.addFreshEntity(chair);
                player.startRiding(chair);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static void onStateReplaced(Level world, BlockPos pos) {
        if(!world.isClientSide) {
            ChairEntity entity = ChairUtil.getChairEntity(world, pos);
            if(entity != null) {
                ChairUtil.removeChairEntity(world, pos);
                entity.ejectPassengers();
            }
        }
    }
    public static boolean addChairEntity(Level world, BlockPos blockPos, ChairEntity entity, BlockPos playerPos) {
        if(!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if(!CHAIRS.containsKey(id)) CHAIRS.put(id, new HashMap<>());
            CHAIRS.get(id).put(blockPos, Pair.of(entity, playerPos));
            return true;
        }
        return false;
    }

    public static boolean removeChairEntity(Level world, BlockPos pos) {
        if(!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if(CHAIRS.containsKey(id)) {
                CHAIRS.get(id).remove(pos);
                return true;
            }
        }
        return false;
    }

    public static ChairEntity getChairEntity(Level world, BlockPos pos) {
        if(!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if(CHAIRS.containsKey(id) && CHAIRS.get(id).containsKey(pos))
                return CHAIRS.get(id).get(pos).getFirst();
        }
        return null;
    }
    public static BlockPos getPreviousPlayerPosition(Player player, ChairEntity chairEntity) {
        if(!player.level.isClientSide) {
            ResourceLocation id = getDimensionTypeId(player.level);
            if(CHAIRS.containsKey(id)) {
                for(Pair<ChairEntity,BlockPos> pair : CHAIRS.get(id).values()) {
                    if(pair.getFirst() == chairEntity)
                        return pair.getSecond();
                }
            }
        }
        return null;
    }
    public static boolean isOccupied(Level world, BlockPos pos) {
        ResourceLocation id = getDimensionTypeId(world);
        return ChairUtil.CHAIRS.containsKey(id) && ChairUtil.CHAIRS.get(id).containsKey(pos);
    }

    public static boolean isPlayerSitting(Player player) {
        for(ResourceLocation i : CHAIRS.keySet()) {
            for(Pair<ChairEntity,BlockPos> pair : CHAIRS.get(i).values()) {
                if(pair.getFirst().hasPassenger(player))
                    return true;
            }
        }
        return false;
    }

    private static ResourceLocation getDimensionTypeId(Level world)
    {
        return world.dimensionTypeId().location();
    }
}
