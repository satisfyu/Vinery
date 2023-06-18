package satisfyu.vinery.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.cristelknight.doapi.config.jankson.config.CommentedConfig;
import net.minecraft.Util;
import satisfyu.vinery.Vinery;

import java.util.HashMap;


public record VineryConfig(int wineTraderChance, int yearLengthInDays, boolean enableWineMakerSetBonus)
        implements CommentedConfig<VineryConfig> {

    private static VineryConfig INSTANCE = null;

    public static final VineryConfig DEFAULT = new VineryConfig(50, 16, true);

    public static final Codec<VineryConfig> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.intRange(0, 100).fieldOf("wine_trader_chance").orElse(DEFAULT.wineTraderChance).forGetter(c -> c.wineTraderChance),
                    Codec.intRange(1, 100).fieldOf("year_length_in_days").orElse(DEFAULT.yearLengthInDays).forGetter(c -> c.yearLengthInDays),
                    Codec.BOOL.fieldOf("enable_wine_maker_set_bonus").orElse(DEFAULT.enableWineMakerSetBonus).forGetter(c -> c.enableWineMakerSetBonus)
            ).apply(builder, VineryConfig::new)
    );

    @Override
    public HashMap<String, String> getComments() {
        return Util.make(new HashMap<>(), map -> {
            map.put("wine_trader_chance", """
                    Chance
                    idk
                    help.""");
            map.put("year_length_in_days", """
                    How long should a year be (in days).""");
                }
        );
    }

    @Override
    public String getHeader() {
        return """
               Vinery Config
               With links:
               dc:
               cf:""";
    }

    @Override
    public String getSubPath() {
        return "vinery/config";
    }

    @Override
    public VineryConfig getInstance() {

        return INSTANCE;
    }

    @Override
    public VineryConfig getDefault() {
        return DEFAULT;
    }

    @Override
    public Codec<VineryConfig> getCodec() {
        return CODEC;
    }

    @Override
    public boolean isSorted() {
        return false;
    }

    @Override
    public void setInstance(VineryConfig instance) {
        INSTANCE = instance;
    }
}
