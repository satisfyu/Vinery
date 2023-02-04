package satisfyu.vinery.util;

import com.google.gson.JsonArray;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class VineryUtils {

    public static boolean matchesRecipe(Inventory inventory, DefaultedList<Ingredient> recipe, int startIndex, int endIndex) {
        final List<ItemStack> validStacks = new ArrayList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            final ItemStack stackInSlot = inventory.getStack(i);
            if (!stackInSlot.isEmpty()) validStacks.add(stackInSlot);
        }
        for (Ingredient entry : recipe) {
            boolean matches = false;
            for (ItemStack item : validStacks) {
                if (entry.test(item)) {
                    matches = true;
                    validStacks.remove(item);
                    break;
                }
            }
            if (!matches) {
                return false;
            }
        }
        return true;
    }



    public static DefaultedList<Ingredient> deserializeIngredients(JsonArray json) {
        DefaultedList<Ingredient> ingredients = DefaultedList.of();
        for (int i = 0; i < json.size(); i++) {
            Ingredient ingredient = Ingredient.fromJson(json.get(i));
            if (!ingredient.isEmpty()) {
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }

    public static boolean isIndexInRange(int index, int startInclusive, int endInclusive) {
        return index >= startInclusive && index <= endInclusive;
    }

    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{ shape, VoxelShapes.empty() };
        int times = (to.getHorizontal() - from.getHorizontal() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.combine(buffer[1], VoxelShapes.cuboid(1-maxZ, minY, minX, 1-minZ, maxY, maxX), BooleanBiFunction.OR));
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }
        return buffer[0];
    }

    public static Optional<Pair<Float, Float>> getRelativeHitCoordinatesForBlockFace(BlockHitResult blockHitResult, Direction direction, Direction[] unAllowedDirections) {
        Direction direction2 = blockHitResult.getSide();
        if(Arrays.stream(unAllowedDirections).toList().contains(direction2)) return Optional.empty();
        if (direction != direction2 && direction2 != Direction.UP  && direction2 != Direction.DOWN) {
            return Optional.empty();
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos().offset(direction2);
            Vec3d vec3 = blockHitResult.getPos().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            float d = (float) vec3.getX();
            float f = (float) vec3.getZ();

            float y = (float) vec3.getY();

            if(direction2 == Direction.UP || direction2 == Direction.DOWN) direction2 = direction;
            return switch (direction2) {
                case NORTH -> Optional.of(new Pair<>((float) (1.0 - d), y));
                case SOUTH -> Optional.of(new Pair<>(d, y));
                case WEST -> Optional.of(new Pair<>(f, y));
                case EAST -> Optional.of(new Pair<>((float) (1.0 - f), y));
                case DOWN, UP -> Optional.empty();
            };
        }
    }

    public static boolean isFDLoaded(){
        return FabricLoader.getInstance().isModLoaded("farmersdelight");
    }
}
