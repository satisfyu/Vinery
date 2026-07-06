package satisfyu.vinery.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfyu.vinery.config.VineryConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Drops any recipe whose result item is a disabled wine (see {@code disabled_wines} in the Vinery config),
 * regardless of recipe type. This single hook covers the fermentation barrel, the apple press, and Create
 * mixing/pressing recipes (Create's {@code ProcessingRecipe#getResultItem} returns its first rollable output,
 * and every Vinery wine recipe is single-output). JEI, REI and the vanilla recipe book inherit the filtered
 * set for free because they all read from the (client-synced) RecipeManager.
 * <p>
 * The only theoretical gap is a disabled wine appearing as a non-first output of a multi-output recipe (no
 * Vinery recipe does this); recipes whose {@link Recipe#getResultItem} throws are skipped defensively.
 * <p>
 * {@code apply} only runs on the logical server (datapack load); the client receives the already-filtered set
 * via the recipe sync packet, so this does not need to run client-side.
 */
@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @Shadow private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;

    @Shadow private Map<ResourceLocation, Recipe<?>> byName;

    private static final Logger VINERY$LOGGER = LoggerFactory.getLogger("Vinery/DisabledWines");

    @Inject(
            method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("TAIL")
    )
    private void vinery$filterDisabledWines(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        VineryConfig config = VineryConfig.DEFAULT.getConfig();
        if (config == null) {
            return;
        }
        List<String> disabled = config.disabledWines();
        if (disabled == null || disabled.isEmpty()) {
            return;
        }

        int[] removed = {0};

        Map<ResourceLocation, Recipe<?>> newByName = new HashMap<>();
        this.byName.forEach((id, recipe) -> {
            if (vinery$isDisabledResult(recipe)) {
                removed[0]++;
            } else {
                newByName.put(id, recipe);
            }
        });

        Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> newByType = new HashMap<>();
        this.recipes.forEach((type, byId) -> {
            Map<ResourceLocation, Recipe<?>> kept = new HashMap<>();
            byId.forEach((id, recipe) -> {
                if (!vinery$isDisabledResult(recipe)) {
                    kept.put(id, recipe);
                }
            });
            newByType.put(type, ImmutableMap.copyOf(kept));
        });

        this.byName = ImmutableMap.copyOf(newByName);
        this.recipes = ImmutableMap.copyOf(newByType);

        if (removed[0] > 0) {
            VINERY$LOGGER.info("Removed {} recipe(s) producing disabled wines: {}", removed[0], disabled);
        }
    }

    private boolean vinery$isDisabledResult(Recipe<?> recipe) {
        try {
            ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
            if (result == null || result.isEmpty()) {
                return false;
            }
            return VineryConfig.isDisabled(BuiltInRegistries.ITEM.getKey(result.getItem()));
        } catch (Throwable t) {
            // A misbehaving third-party recipe must never break datapack loading.
            return false;
        }
    }
}
