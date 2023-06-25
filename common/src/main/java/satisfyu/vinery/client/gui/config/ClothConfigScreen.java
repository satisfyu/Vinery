package satisfyu.vinery.client.gui.config;

import de.cristelknight.doapi.config.cloth.CCUtil;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.config.VineryConfig;

public class ClothConfigScreen {

    public static Screen create(Screen parent) {
        VineryConfig config = VineryConfig.DEFAULT.getConfig();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setDefaultBackgroundTexture(new ResourceLocation("textures/block/dirt.png"))
                .setTitle(Component.translatable(Vinery.MODID + ".config.title").withStyle(ChatFormatting.BOLD));

        ConfigEntries entries = new ConfigEntries(builder.entryBuilder(), config, builder.getOrCreateCategory(CCUtil.categoryName("main", Vinery.MODID)));
        builder.setSavingRunnable(() -> {
            VineryConfig.DEFAULT.setInstance(entries.createConfig());
            VineryConfig.DEFAULT.getConfig(true, true);
        });
        return builder.build();
    }

    private static class ConfigEntries {
        private final ConfigEntryBuilder builder;
        private final ConfigCategory category;
        private final BooleanListEntry enableWineMakerSetBonus;
        private final IntegerListEntry wineTraderChance, yearLengthInDays;



        public ConfigEntries(ConfigEntryBuilder builder, VineryConfig config, ConfigCategory category) {
            this.builder = builder;
            this.category = category;

            enableWineMakerSetBonus = createBooleanField("enableWineMakerSetBonus", config.enableWineMakerSetBonus(), VineryConfig.DEFAULT.enableWineMakerSetBonus());


            wineTraderChance = createIntField("wineTraderChance", config.wineTraderChance(), VineryConfig.DEFAULT.wineTraderChance());
            yearLengthInDays = createIntField("yearLengthInDays", config.yearLengthInDays(), VineryConfig.DEFAULT.yearLengthInDays());
        }


        public VineryConfig createConfig() {
            return new VineryConfig(wineTraderChance.getValue(), yearLengthInDays.getValue(), enableWineMakerSetBonus.getValue());
        }


        public BooleanListEntry createBooleanField(String id, boolean value, boolean defaultValue){
            BooleanListEntry e = CCUtil.createBooleanField(Vinery.MODID, id, value, defaultValue, builder);
            category.addEntry(e);
            return e;
        }

        public IntegerListEntry createIntField(String id, int value, int defaultValue){
            IntegerListEntry e = CCUtil.createIntField(Vinery.MODID, id, value, defaultValue, builder);
            category.addEntry(e);
            return e;
        }
    }
}
