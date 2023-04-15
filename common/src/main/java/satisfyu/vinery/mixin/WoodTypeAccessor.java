package satisfyu.vinery.mixin;

import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WoodType.class)
public interface WoodTypeAccessor {
    @Invoker
    static WoodType callRegister(WoodType type) {
        throw new UnsupportedOperationException();
    }

    @Invoker("")
    static WoodType callCreate(String name) {
        throw new Error("Mixin did not apply!");
    }
}
