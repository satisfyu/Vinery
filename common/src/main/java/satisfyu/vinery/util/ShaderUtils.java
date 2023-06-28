package satisfyu.vinery.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import satisfyu.vinery.client.shader.ShaderList;

import java.io.IOException;

public class ShaderUtils {
    public static Minecraft client = Minecraft.getInstance();
    public static PostChain shader;
    public static boolean enabled = false;

    public static void load(boolean d) {
        if(shader != null)
            shader.close();
        shader = getCurrent(d);
        if(shader != null) {
            shader.resize(client.getWindow().getWidth(), client.getWindow().getHeight());
            enabled = true;
            return;
        }
        enabled = false;
    }

    private static PostChain getCurrent(boolean d) {
        ShaderList s;
        if(d)
            s = ShaderList.next();
        else
            s = ShaderList.previous();
        if(s.getId() == -1)
            return null;
        else {
            try {
                return new PostChain(client.getTextureManager(), client.getResourceManager(), client.getMainRenderTarget(), s.getResource());
            } catch (IOException e) {
                return null;
            }
        }
    }
}
