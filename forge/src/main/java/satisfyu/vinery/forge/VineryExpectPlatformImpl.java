package satisfyu.vinery.forge;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.forge.extraapiutil.APIFinder;
import satisfyu.vinery.forge.registry.BurningBlockRegistry;
import satisfyu.vinery.util.VineryApi;
import satisfyu.vinery.util.boat.api.TerraformBoatType;

import java.util.*;
import java.util.function.Supplier;

public class VineryExpectPlatformImpl {


    public static Block[] getBlocksForStorage() {
        Set<Block> set = new HashSet<>();
        List<Pair<List<String>, VineryApi>> apis = APIFinder.scanForAPIs();
        for(Pair<List<String>, VineryApi> apiPair : apis){
            VineryApi api = apiPair.getSecond();
            api.registerBlocks(set);
        }
        return set.toArray(new Block[0]);
    }

    public static <T extends LivingEntity> void registerArmor(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {
        List<Pair<List<String>, VineryApi>> apis = APIFinder.scanForAPIs();
        for(Pair<List<String>, VineryApi> apiPair : apis){
            VineryApi api = apiPair.getSecond();
            api.registerArmor(models, modelLoader);
        }
    }

    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        BurningBlockRegistry.add(burnOdd, igniteOdd, blocks);
    }
}
