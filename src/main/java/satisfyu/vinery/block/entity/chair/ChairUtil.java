package satisfyu.vinery.block.entity.chair;

import com.mojang.datafixers.util.Pair;
import satisfyu.vinery.registry.VineryBlockEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
/*
public class ChairUtil {
    private static final Map<Identifier, Map<BlockPos, Pair<ChairEntity,BlockPos>>> CHAIRS = new HashMap<>();

     public static ActionResult onUse(World world, PlayerEntity player, Hand hand, BlockHitResult hit, double extraHeight) {
        if(world.isClient) return ActionResult.PASS;
        if(player.isSneaking()) return ActionResult.PASS;
        if(ChairUtil.isPlayerSitting(player)) return ActionResult.PASS;
        if(hit.getSide() == Direction.DOWN) return ActionResult.PASS;
        BlockPos hitPos = hit.getBlockPos();
        if(!ChairUtil.isOccupied(world, hitPos) && player.getStackInHand(hand).isEmpty()) {
            ChairEntity chair = VineryBlockEntityTypes.CHAIR.create(world);
            chair.refreshPositionAndAngles(hitPos.getX() + 0.5D, hitPos.getY() + 0.25D + extraHeight, hitPos.getZ() + 0.5D, 0, 0);
            if(ChairUtil.addChairEntity(world, hitPos, chair, player.getBlockPos())) {
                world.spawnEntity(chair);
                player.startRiding(chair);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    public static void onStateReplaced(World world, BlockPos pos) {
        if(!world.isClient) {
            ChairEntity entity = ChairUtil.getChairEntity(world, pos);
            if(entity != null) {
                ChairUtil.removeChairEntity(world, pos);
                entity.removeAllPassengers();
            }
        }
    }
    public static boolean addChairEntity(World world, BlockPos blockPos, ChairEntity entity, BlockPos playerPos) {
        if(!world.isClient) {
            Identifier id = getDimensionTypeId(world);
            if(!CHAIRS.containsKey(id)) CHAIRS.put(id, new HashMap<>());
            CHAIRS.get(id).put(blockPos, Pair.of(entity, playerPos));
            return true;
        }
        return false;
    }

    public static boolean removeChairEntity(World world, BlockPos pos) {
        if(!world.isClient) {
            Identifier id = getDimensionTypeId(world);
            if(CHAIRS.containsKey(id)) {
                CHAIRS.get(id).remove(pos);
                return true;
            }
        }
        return false;
    }

    public static ChairEntity getChairEntity(World world, BlockPos pos) {
        if(!world.isClient) {
            Identifier id = getDimensionTypeId(world);
            if(CHAIRS.containsKey(id) && CHAIRS.get(id).containsKey(pos))
                return CHAIRS.get(id).get(pos).getFirst();
        }
        return null;
    }
    public static BlockPos getPreviousPlayerPosition(PlayerEntity player, ChairEntity chairEntity) {
        if(!player.world.isClient) {
            Identifier id = getDimensionTypeId(player.world);
            if(CHAIRS.containsKey(id)) {
                for(Pair<ChairEntity,BlockPos> pair : CHAIRS.get(id).values()) {
                    if(pair.getFirst() == chairEntity)
                        return pair.getSecond();
                }
            }
        }
        return null;
    }
    public static boolean isOccupied(World world, BlockPos pos) {
        Identifier id = getDimensionTypeId(world);
        return ChairUtil.CHAIRS.containsKey(id) && ChairUtil.CHAIRS.get(id).containsKey(pos);
    }

    public static boolean isPlayerSitting(PlayerEntity player) {
        for(Identifier i : CHAIRS.keySet()) {
            for(Pair<ChairEntity,BlockPos> pair : CHAIRS.get(i).values()) {
                if(pair.getFirst().hasPassenger(player))
                    return true;
            }
        }
        return false;
    }

    private static Identifier getDimensionTypeId(World world)
    {
        return world.getDimension().getValue();
    }
}
*/