package satisfyu.vinery.villager.memory;

import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.Registry;

import java.util.Optional;




public class ModMemoryModuleType<U> extends MemoryModuleType<U>{
    public static final MemoryModuleType<GlobalPos> SHOP = register("shop", GlobalPos.CODEC);

    public static final MemoryModuleType<Long> LAST_SHOPED = register("last_shoped", Codec.LONG);

    public ModMemoryModuleType(Optional<Codec<U>> uCodec) {
        super(uCodec);
    }

    private static <U> MemoryModuleType register(String id, Codec<U> codec) {
        return Registry.register(Registry.MEMORY_MODULE_TYPE, new Identifier(id), new MemoryModuleType(Optional.of(codec)));
    }
    private static MemoryModuleType register(String id) {
        return Registry.register(Registry.MEMORY_MODULE_TYPE, new Identifier(id), new MemoryModuleType(Optional.empty()));
    }

    public static void init() {
        System.out.println("Init MemoryModuleType"); //TODO
    }
}
