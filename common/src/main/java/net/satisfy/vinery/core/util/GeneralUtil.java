package net.satisfy.vinery.core.util;

import com.google.gson.JsonArray;
import com.mojang.datafixers.util.Pair;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import io.netty.buffer.Unpooled;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.vinery.core.entity.ChairEntity;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

public class GeneralUtil {
    public static final EnumProperty<LineConnectingType> LINE_CONNECTING_TYPE = EnumProperty.create("type", LineConnectingType.class);
    private static final Map<ResourceLocation, Map<BlockPos, Pair<ChairEntity, BlockPos>>> CHAIRS = new HashMap<>();

    public static RotatedPillarBlock logBlock() {
        return new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(DeferredRegister<Block> registerB, Registrar<Block> registrarB, DeferredRegister<Item> registerI, Registrar<Item> registrarI, ResourceLocation name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = registerWithoutItem(registerB, registrarB, name, block);
        registerItem(registerI, registrarI, name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(DeferredRegister<Block> register, Registrar<Block> registrar, ResourceLocation path, Supplier<T> block) {
        return Platform.isNeoForge() ? register.register(path.getPath(), block) : registrar.register(path, block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(DeferredRegister<Item> register, Registrar<Item> registrar, ResourceLocation path, Supplier<T> itemSupplier) {
        return Platform.isNeoForge() ? register.register(path.getPath(), itemSupplier) : registrar.register(path, itemSupplier);
    }

    public static Collection<ServerPlayer> tracking(ServerLevel world, ChunkPos pos) {
        Objects.requireNonNull(world, "The world cannot be null");
        Objects.requireNonNull(pos, "The chunk pos cannot be null");
        return world.getChunkSource().chunkMap.getPlayers(pos, false);
    }

    public static Collection<ServerPlayer> tracking(ServerLevel world, BlockPos pos) {
        Objects.requireNonNull(pos, "BlockPos cannot be null");
        return tracking(world, new ChunkPos(pos));
    }

    public static BlockPos getPreviousPlayerPosition(Player player, ChairEntity chairEntity) {
        if (!player.level().isClientSide()) {
            ResourceLocation id = getDimensionTypeId(player.level());
            if (CHAIRS.containsKey(id)) {

                for (Object object : ((Map) CHAIRS.get(id)).values()) {
                    Pair<ChairEntity, BlockPos> pair = (Pair) object;
                    if (pair.getFirst() == chairEntity) {
                        return pair.getSecond();
                    }
                }
            }
        }

        return null;
    }

    public static ItemInteractionResult onUse(Level world, Player player, InteractionHand hand, BlockHitResult hit, double extraHeight) {
        if (world.isClientSide) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (player.isShiftKeyDown()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (GeneralUtil.isPlayerSitting(player)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (hit.getDirection() == Direction.DOWN) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        BlockPos hitPos = hit.getBlockPos();
        if (!GeneralUtil.isOccupied(world, hitPos) && player.getItemInHand(hand).isEmpty()) {
            ChairEntity chair = EntityTypeRegistry.CHAIR.get().create(world);
            if (chair == null) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

            BlockState s = world.getBlockState(hitPos);
            float yaw = 0.0F;
            for (var p : s.getProperties()) {
                if (p.getName().equals("facing") && p instanceof DirectionProperty dp) {
                    yaw = s.getValue(dp).toYRot();
                    break;
                }
            }
            for (var p : s.getProperties()) {
                if (p.getName().equals("part")) {
                    Object v = s.getValue(p);
                    if (v.toString().equals("head")) {
                        yaw += 180.0F;
                    }
                    break;
                }
            }

            chair.setSeatPos(hitPos);
            chair.moveTo(hitPos.getX() + 0.5D, hitPos.getY() + 0.25D + extraHeight, hitPos.getZ() + 0.5D, 0, 0);
            chair.setYRot(yaw);
            chair.yRotO = yaw;

            if (GeneralUtil.addChairEntity(world, hitPos, chair, player.blockPosition())) {
                world.addFreshEntity(chair);
                player.startRiding(chair);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public static boolean isOccupied(Level world, BlockPos pos) {
        ResourceLocation id = getDimensionTypeId(world);
        return GeneralUtil.CHAIRS.containsKey(id) && GeneralUtil.CHAIRS.get(id).containsKey(pos);
    }

    public static boolean isPlayerSitting(Player player) {
        for (ResourceLocation i : CHAIRS.keySet()) {
            for (Pair<ChairEntity, BlockPos> pair : CHAIRS.get(i).values()) {
                if (pair.getFirst().hasPassenger(player))
                    return true;
            }
        }
        return false;
    }

    private static ResourceLocation getDimensionTypeId(Level world) {
        return world.dimension().location();
    }

    public static void onStateReplaced(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            ChairEntity entity = GeneralUtil.getChairEntity(world, pos);
            if (entity != null) {
                GeneralUtil.removeChairEntity(world, pos);
                entity.ejectPassengers();
            }
        }
    }

    public static boolean addChairEntity(Level world, BlockPos blockPos, ChairEntity entity, BlockPos playerPos) {
        if (!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if (!CHAIRS.containsKey(id)) CHAIRS.put(id, new HashMap<>());
            CHAIRS.get(id).put(blockPos, Pair.of(entity, playerPos));
            return true;
        }
        return false;
    }

    public static void removeChairEntity(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if (CHAIRS.containsKey(id)) {
                CHAIRS.get(id).remove(pos);
            }
        }
    }

    public static ChairEntity getChairEntity(Level world, BlockPos pos) {
        if (!world.isClientSide()) {
            ResourceLocation id = getDimensionTypeId(world);
            if (CHAIRS.containsKey(id) && CHAIRS.get(id).containsKey(pos))
                return CHAIRS.get(id).get(pos).getFirst();
        }
        return null;
    }

    public static FriendlyByteBuf create() {
        return new FriendlyByteBuf(Unpooled.buffer());
    }


    public static void popResourceFromFace(Level level, BlockPos blockPos, Direction side, ItemStack itemStack) {
        BlockState blockState = level.getBlockState(blockPos);
        double itemWidth = EntityType.ITEM.getWidth();
        double itemHeight = EntityType.ITEM.getHeight();
        VoxelShape shape = blockState.getCollisionShape(level, blockPos);
        double posX = (double)blockPos.getX() + 0.5;
        double posY = (double)blockPos.getY() + 0.5;
        double posZ = (double)blockPos.getZ() + 0.5;
        double offsetX = 0.0;
        double offsetY = 0.0;
        double offsetZ = 0.0;
        switch (side) {
            case DOWN:
                posY = (double)blockPos.getY() - shape.min(Direction.Axis.Y);
                offsetY = -itemHeight * 2.0;
                break;
            case UP:
                posY = (double)blockPos.getY() + shape.max(Direction.Axis.Y);
                break;
            case NORTH:
                posZ = (double)blockPos.getZ() + shape.min(Direction.Axis.Z);
                offsetZ = -itemWidth;
                break;
            case SOUTH:
                posZ = (double)blockPos.getZ() + shape.max(Direction.Axis.Z);
                offsetZ = itemWidth;
                break;
            case WEST:
                posX = (double)blockPos.getX() + shape.min(Direction.Axis.X);
                offsetX = -itemWidth;
                break;
            case EAST:
                posX = (double)blockPos.getX() + shape.max(Direction.Axis.X);
                offsetX = itemWidth;
        }

        int i = side.getStepX();
        int j = side.getStepY();
        int k = side.getStepZ();
        double deltaX = i == 0 ? Mth.nextDouble(level.random, -0.1, 0.1) : (double)i * 0.1;
        double deltaY = j == 0 ? Mth.nextDouble(level.random, 0.0, 0.1) : (double)j * 0.1 + 0.1;
        double deltaZ = k == 0 ? Mth.nextDouble(level.random, -0.1, 0.1) : (double)k * 0.1;
        popResource(level, new ItemEntity(level, posX + offsetX, posY + offsetY, posZ + offsetZ, itemStack, deltaX, deltaY, deltaZ), itemStack);
    }

    private static void popResource(Level level, ItemEntity itemEntity, ItemStack itemStack) {
        if (!level.isClientSide && !itemStack.isEmpty() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }

    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};
        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;

        for(int i = 0; i < times; ++i) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.joinUnoptimized(buffer[1], Shapes.box(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX), BooleanOp.OR));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    public static Optional<Tuple<Float, Float>> getRelativeHitCoordinatesForBlockFace(
            BlockHitResult blockHitResult,
            Direction direction,
            Direction[] unAllowedDirections) {

        Direction hitDirection = blockHitResult.getDirection();

        for (Direction unAllowed : unAllowedDirections) {
            if (unAllowed == hitDirection) {
                return Optional.empty();
            }
        }

        if (hitDirection != direction && hitDirection != Direction.UP && hitDirection != Direction.DOWN) {
            return Optional.empty();
        }

        BlockPos adjacentPos = blockHitResult.getBlockPos().relative(hitDirection);
        Vec3 hitLocation = blockHitResult.getLocation().subtract(
                adjacentPos.getX(),
                adjacentPos.getY(),
                adjacentPos.getZ()
        );

        float x = (float) hitLocation.x();
        float z = (float) hitLocation.z();
        float y = (float) hitLocation.y();

        Direction effectiveDirection = (hitDirection == Direction.UP || hitDirection == Direction.DOWN)
                ? direction
                : hitDirection;

        return switch (effectiveDirection) {
            case NORTH -> Optional.of(new Tuple<>(1.0f - x, y));
            case SOUTH -> Optional.of(new Tuple<>(x, y));
            case WEST -> Optional.of(new Tuple<>(z, y));
            case EAST -> Optional.of(new Tuple<>(1.0f - z, y));
            default -> Optional.empty();
        };
    }

//    public static NonNullList<Ingredient> deserializeIngredients(JsonArray json) {
//        NonNullList<Ingredient> ingredients = NonNullList.create();
//
//        for(int i = 0; i < json.size(); ++i) {
//            Ingredient ingredient = Ingredient.(json.get(i));
//            if (!ingredient.isEmpty()) {
//                ingredients.add(ingredient);
//            }
//        }
//
//        return ingredients;
//    }

    public static ItemStack convertStackAfterFinishUsing(LivingEntity entity, ItemStack used, Item returnItem, Item usedItem) {
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, used);
            serverPlayer.awardStat(Stats.ITEM_USED.get(usedItem));
        }

        if (used.isEmpty()) {
            return new ItemStack(returnItem);
        } else {
            if (entity instanceof Player player) {
                if (!((Player)entity).getAbilities().instabuild) {
                    ItemStack itemStack2 = new ItemStack(returnItem);
                    if (!player.getInventory().add(itemStack2)) {
                        player.drop(itemStack2, false);
                    }
                }
            }

            return used;
        }
    }

    public enum LineConnectingType implements StringRepresentable {
        NONE("none"),
        MIDDLE("middle"),
        LEFT("left"),
        RIGHT("right");

        private final String name;

        LineConnectingType(String type) {
            this.name = type;
        }

        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
