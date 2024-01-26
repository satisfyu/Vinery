package satisfyu.vinery.event;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyInputHandler {
    public static final String KEY_CATEGORY = "key.category.vinery";
    public static final String KEY_CHANGE_SHADER = "key.vinery.change_shader";

    public static final KeyMapping changeShader = new KeyMapping(
            KEY_CATEGORY,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            KEY_CHANGE_SHADER
    );

    public static void register() {
        KeyMappingRegistry.register(changeShader);
        registerKeyInputs();
    }

    public static void registerKeyInputs() {

    }
}
