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
import satisfyu.vinery.util.VineryApi;
import satisfyu.vinery.util.boat.api.TerraformBoatType;

import java.util.*;
import java.util.function.Supplier;

public class VineryExpectPlatformImpl {

    public static Map<ResourceLocation, Supplier<TerraformBoatType>> INSTANCE = new HashMap<>();

    public static void register(ResourceLocation location, Supplier<TerraformBoatType> boatTypeSupplier) {
        INSTANCE.put(location, boatTypeSupplier);
    }

    public static ResourceKey<TerraformBoatType> createKey(ResourceLocation id) {
        return ResourceKey.create(VineryForge.terraformBoatTypeDeferredRegister.getRegistryKey(), id);
    }

    public static ResourceLocation getId(TerraformBoatType type) {
        return VineryForge.terraformBoatTypeDeferredRegister.getKey(type);
        /*
        if(INSTANCE.entrySet().isEmpty()) Vinery.LOGGER.error("GET ID: is empty");

        for(Map.Entry<ResourceKey<TerraformBoatType>, TerraformBoatType> entry : entrySet()){
            Vinery.LOGGER.error("IN GET ID: " + entry.getValue() + " " + type);
            if(entry.getValue().equals(type)){
                return entry.getKey().location();
            }
            else {
                Vinery.LOGGER.error("IN GET ID: NO equal");
            }
        }
        throw new NullPointerException("Couldn't find boat type in registry");

         */
    }

    public static TerraformBoatType get(ResourceLocation location) {
        return VineryForge.terraformBoatTypeDeferredRegister.getValue(location);
        /*
        for(Map.Entry<ResourceKey<TerraformBoatType>, TerraformBoatType> entry : entrySet()){
            Vinery.LOGGER.error("IN GET ID: get: " + entry.getKey().location() + " from: " + location);
            if(entry.getKey().location().equals(location)){
                return entry.getValue();
            }
            else {
                Vinery.LOGGER.error("IN GET ID: NO equal");
            }
        }
        throw new NullPointerException("Couldn't find boat location in registry");

         */
    }

    public static Set<Map.Entry<ResourceKey<TerraformBoatType>, TerraformBoatType>> entrySet() {
        return VineryForge.terraformBoatTypeDeferredRegister.getEntries();
        /*
        Set<Map.Entry<ResourceKey<TerraformBoatType>, TerraformBoatType>> set = new HashSet<>();
        for(RegistryObject<TerraformBoatType> b : VineryForge.terraformBoatTypeDeferredRegister.getEntries()){
            set.add(new Map.Entry<>() {
                @Override
                public ResourceKey<TerraformBoatType> getKey() {
                    return b.getKey();
                }

                @Override
                public TerraformBoatType getValue() {
                    Vinery.LOGGER.error("Get Value: " + b.getId());
                    return b.orElse(VineryBoatTypes.CHERRY.get());
                }

                @Override
                public TerraformBoatType setValue(TerraformBoatType value) {
                    return value;
                }
            });
        }
        return set;

         */
    }


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
        Set<Block> set = new HashSet<>();
        List<Pair<List<String>, VineryApi>> apis = APIFinder.scanForAPIs();
        for(Pair<List<String>, VineryApi> apiPair : apis){
            VineryApi api = apiPair.getSecond();
            api.registerArmor(models, modelLoader);
        }
    }

    public static void loadInstance() {
        IForgeRegistry<TerraformBoatType> register = VineryForge.terraformBoatTypeDeferredRegister;
    }

}
