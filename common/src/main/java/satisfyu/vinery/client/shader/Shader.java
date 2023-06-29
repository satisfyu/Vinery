package satisfyu.vinery.client.shader;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;

public enum Shader {
        NONE(-1, "none"),
        NOTCH(0, "notch"),
        FXAA(1, "fxaa"),
        ART(2, "art"),
        BUMPY(3, "bumpy"),
        BLOBS2(4, "blobs2"),
        PENCIL(5, "pencil"),
        COLOR_CONVOLVE(6, "color_convolve"),
        DECONVERGE(7, "deconverge"),
        FLIP(8, "flip"),
        INVERT(9, "invert"),
        NTSC(10, "ntsc"),
        OUTLINE(11, "outline"),
        PHOSPHOR(12, "phosphor"),
        SCAN_PINCUSHION(13, "scan_pincushion"),
        SOBEL(14, "sobel"),
        BITS(15, "bits"),
        DESATURATE(16, "desaturate"),
        GREEN(17, "green"),
        BLUR(18, "blur"),
        WOBBLE(19, "wobble"),
        BLOBS(20, "blobs"),
        ANTIALIAS(21, "antialias"),
        CREEPER(22, "creeper"),
        SPIDER(23, "spider");

        private final int id;
        private final String name;

        Shader(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }


        public Component getName() {
            return Component.translatable("vinery.shader." + this.name);
        }

        public ResourceLocation getResource() {
            return new ResourceLocation("shaders/post/" + this.name + ".json");
        }
}
