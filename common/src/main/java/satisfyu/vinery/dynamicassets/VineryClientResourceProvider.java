package satisfyu.vinery.dynamicassets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Jankson;
import net.mehvahdjukaar.moonlight.api.MoonlightRegistry;
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynClientResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicTexturePack;
import net.mehvahdjukaar.moonlight.api.resources.textures.Palette;
import net.mehvahdjukaar.moonlight.api.resources.textures.PaletteColor;
import net.mehvahdjukaar.moonlight.api.resources.textures.Respriter;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureImage;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.util.math.colors.LABColor;
import net.mehvahdjukaar.moonlight.api.util.math.colors.RGBColor;
import net.mehvahdjukaar.moonlight.core.Moonlight;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.VineryIdentifier;
import satisfyu.vinery.client.VineryClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

public class VineryClientResourceProvider {
    public static void init() {
        ClientAssetsGenerator generator = new ClientAssetsGenerator();
        generator.register();
    }

    public static class ClientAssetsGenerator extends DynClientResourcesGenerator {

        protected ClientAssetsGenerator() {
            //here you pass the dynamic texture pack instance
            super(new DynamicTexturePack(new VineryIdentifier("dynamic_resource_pack"), Pack.Position.TOP, false, false));
        }

        // generate here your assets




        @Override
        public void regenerateDynamicAssets(ResourceManager manager) {

            getLogger().info("Generating lattice models");
            BlockSetAPI.getBlockSet(WoodType.class).getValues().forEach((WoodType type) -> {

                ResourceLocation planksResourceLocation = new ResourceLocation(type.getNamespace(), String.format("block/%s_planks", type.getTexturePath()));

                getLogger().info("Found wood path, {}", planksResourceLocation);

                this.addTextureIfNotPresent(manager, String.format("vinery:block/lattice/lattice_support_a_%s", type.getTypeName()), () -> recolorTexture(manager, new VineryIdentifier("block/lattice/lattice_support_a_oak"), planksResourceLocation));
                this.addTextureIfNotPresent(manager, String.format("vinery:block/lattice/lattice_support_b_%s", type.getTypeName()), () -> recolorTexture(manager, new VineryIdentifier("block/lattice/lattice_support_b_oak"), planksResourceLocation));
                this.addTextureIfNotPresent(manager, String.format("vinery:block/lattice/lattice_support_c_%s", type.getTypeName()), () -> recolorTexture(manager, new VineryIdentifier("block/lattice/lattice_support_c_oak"), planksResourceLocation));
                this.addTextureIfNotPresent(manager, String.format("vinery:block/lattice/lattice_vine_support_%s", type.getTypeName()), () -> recolorTexture(manager, new VineryIdentifier("block/lattice/lattice_vine_support_oak"), planksResourceLocation));

                getLogger().info("Generating lattice for {} wood type", type.getTypeName());
                JsonObject json = new JsonObject();
                json.addProperty("parent", new VineryIdentifier("block/oak_lattice").toString());
                this.dynamicPack.addItemModel(new VineryIdentifier(String.format("%s_lattice", type.getTypeName())), json);

                Collection<String> json_paths = List.of("blockstates/%s", "models/item/%s", "models/block/%s","models/block/%s_no_support","models/block/%s_left","models/block/%s_left_no_support","models/block/%s_middle","models/block/%s_right","models/block/%s_right_no_support","models/block/%s_bottom","models/block/%s_stage1","models/block/%s_no_support_stage1","models/block/%s_left_stage1","models/block/%s_left_no_support_stage1","models/block/%s_middle_stage1","models/block/%s_right_stage1","models/block/%s_right_no_support_stage1","models/block/%s_bottom_stage1","models/block/%s_stage2","models/block/%s_no_support_stage2","models/block/%s_left_stage2","models/block/%s_left_no_support_stage2","models/block/%s_middle_stage2","models/block/%s_right_stage2","models/block/%s_right_no_support_stage2","models/block/%s_bottom_stage2","models/block/%s_stage3_red","models/block/%s_no_support_stage3_red","models/block/%s_left_stage3_red","models/block/%s_left_no_support_stage3_red","models/block/%s_middle_stage3_red","models/block/%s_right_stage3_red","models/block/%s_right_no_support_stage3_red","models/block/%s_bottom_stage3_red","models/block/%s_stage4_red","models/block/%s_no_support_stage4_red","models/block/%s_left_stage4_red","models/block/%s_left_no_support_stage4_red","models/block/%s_middle_stage4_red","models/block/%s_right_stage4_red","models/block/%s_right_no_support_stage4_red","models/block/%s_bottom_stage4_red","models/block/%s_stage3_white","models/block/%s_no_support_stage3_white","models/block/%s_left_stage3_white","models/block/%s_left_no_support_stage3_white","models/block/%s_middle_stage3_white","models/block/%s_right_stage3_white","models/block/%s_right_no_support_stage3_white","models/block/%s_bottom_stage3_white","models/block/%s_stage4_white","models/block/%s_no_support_stage4_white","models/block/%s_left_stage4_white","models/block/%s_left_no_support_stage4_white","models/block/%s_middle_stage4_white","models/block/%s_right_stage4_white","models/block/%s_right_no_support_stage4_white","models/block/%s_bottom_stage4_white");
                for (String jsonPath : json_paths) {
                    StaticResource currentJson = StaticResource.getOrFail(manager, new VineryIdentifier(String.format(jsonPath, "oak_lattice") + ".json"));;
                    this.addSimilarJsonResource(manager, currentJson, "oak", type.getTypeName());
                }
            });
        }


        @Override
        public void addDynamicTranslations(AfterLanguageLoadEvent languageEvent) {
            getLogger().info("Generating lattice translations");
            BlockSetAPI.getBlockSet(WoodType.class).getValues().forEach((WoodType type) -> {
                getLogger().info("Loading translation for {} wood type", type.getTypeName());
                String latticeName = String.format(languageEvent.getEntry("block.vinery.lattice"), type.getReadableName());
                String translationKey = String.format("block.vinery.%s_lattice", type.getTypeName());
                languageEvent.addEntry(translationKey, latticeName);
                getLogger().info("Loaded translation \"{}\" for id {}", latticeName, translationKey);
            });
        }

        @Override
        public Logger getLogger() {
            return Vinery.LOGGER;
        }

        @Override
        public boolean dependsOnLoadedPacks() {
            return true;
        }

        private static TextureImage recolorTexture(ResourceManager manager, ResourceLocation original, ResourceLocation recolorTo) {
            try (TextureImage originalImage = TextureImage.open(manager, original)) {
                TextureImage recolorToImage = TextureImage.open(manager, recolorTo);

                return Respriter.of(originalImage).recolor(modifyPalette(recolorToImage));

            } catch (Exception e) {
                Vinery.LOGGER.error("Failed to load texture {} for recoloring", original);
                return null;
            }
        }

        private static Palette modifyPalette(TextureImage paletteImage) {
            Palette originalPalette = Palette.fromImage(paletteImage);

            originalPalette.remove(originalPalette.getDarkest());

            if (originalPalette.getLuminanceSpan() < 0.2) {
                originalPalette.increaseUp();
            }

            originalPalette.increaseInner();
            originalPalette.reduceAndAverage();

            RGBColor darkest = originalPalette.getColorAtSlope(0.2f).rgb();
            RGBColor newColor = modifyColor(darkest);

            originalPalette.add(new PaletteColor(newColor));

            return originalPalette;
        }

        private static RGBColor modifyColor(RGBColor color){
            // we can convert colors into many color spaces depending on our needs
            LABColor lab = color.asLAB();
            LABColor pureRed = new RGBColor(1,0,0,1).asLAB();

            // we mix the color with 20% pure red. Color mixing changes depending on color space used
            lab.mixWith(pureRed, 0.2f);

            // now we set the luminance to a value we want
            lab.withLuminance(0.4f);
            return lab.asRGB();
        }
    }
}
