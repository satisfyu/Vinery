package net.satisfy.vinery.block;

import com.mojang.serialization.MapCodec;
import de.cristelknight.doapi.common.block.CabinetBlock;
import de.cristelknight.doapi.common.registry.DoApiSoundEventRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.BaseEntityBlock;

import java.util.function.Supplier;

public class CabinetBlockImp extends CabinetBlock {
    public static final MapCodec<CabinetBlockImp> CODEC = simpleCodec(CabinetBlockImp::new);

    public CabinetBlockImp(Properties settings, Supplier<SoundEvent> openSound, Supplier<SoundEvent> closeSound) {
        super(settings, openSound, closeSound);
    }

    public CabinetBlockImp(Properties settings) {
        super(settings, DoApiSoundEventRegistry.CABINET_OPEN, DoApiSoundEventRegistry.CABINET_CLOSE);
    }


    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }
}
