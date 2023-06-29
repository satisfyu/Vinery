package satisfyu.vinery.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.client.shader.Shader;

import java.io.IOException;

public class ShaderUtils {
    public static Minecraft client = Minecraft.getInstance();
    public static PostChain shader;
    public static boolean enabled = false;

    public static void load(@Nullable PostChain postChain) {
        if (shader != null)
            shader.close();
        shader = postChain;
        if (shader != null) {
            shader.resize(client.getWindow().getWidth(), client.getWindow().getHeight());
            enabled = true;
            return;
        }
        enabled = false;
    }

    public static PostChain getShader(Shader shader) {
        if (shader.getId() == -1)
            return null;
        else {
            try {
                return new PostChain(client.getTextureManager(), client.getResourceManager(), client.getMainRenderTarget(), shader.getResource());
            } catch (IOException e) {
                return null;
            }
        }
    }

    public static PostChain getRandomShader() {
        Shader shader = Shader.values()[RandomSource.create().nextInt(Shader.values().length)];
        try {
            return new PostChain(client.getTextureManager(), client.getResourceManager(), client.getMainRenderTarget(), shader.getResource());
        } catch (IOException e) {
            return null;
        }
    }
}
