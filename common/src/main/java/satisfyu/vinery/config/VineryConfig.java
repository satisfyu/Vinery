package satisfyu.vinery.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.cristelknight.doapi.config.jankson.config.CommentedConfig;
import net.minecraft.Util;

import java.util.HashMap;


public record VineryConfig(int wineTraderChance, int yearLengthInDays, int yearsPerEffectLevel,
                           boolean enableWineMakerSetBonus, int damagePerUse, int probabilityForDamage, int probabilityToKeepBoneMeal, int fermentationBarrelTime, int grapeGrowthSpeed)
        implements CommentedConfig<VineryConfig> {

    private static VineryConfig INSTANCE = null;

    public static final VineryConfig DEFAULT = new VineryConfig(50, 16, 4, true, 1, 30, 100, 50, 100);

    public static final Codec<VineryConfig> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.intRange(0, 100).fieldOf("wine_trader_chance").orElse(DEFAULT.wineTraderChance).forGetter(c -> c.wineTraderChance),
                    Codec.intRange(1, 1000).fieldOf("year_length_in_days").orElse(DEFAULT.yearLengthInDays).forGetter(c -> c.yearLengthInDays),
                    Codec.intRange(1, 1000).fieldOf("years_per_effect_level").orElse(DEFAULT.yearsPerEffectLevel).forGetter(c -> c.yearsPerEffectLevel),
                    Codec.BOOL.fieldOf("enable_wine_maker_set_bonus").orElse(DEFAULT.enableWineMakerSetBonus).forGetter(c -> c.enableWineMakerSetBonus),
                    Codec.intRange(1, 1000).fieldOf("damage_per_use").orElse(DEFAULT.damagePerUse).forGetter(c -> c.damagePerUse),
                    Codec.intRange(0, 100).fieldOf("probability_for_damage").orElse(DEFAULT.probabilityForDamage).forGetter(c -> c.probabilityForDamage),
                    Codec.intRange(1, 100).fieldOf("probability_to_keep_bone_meal").orElse(DEFAULT.probabilityToKeepBoneMeal).forGetter(c -> c.probabilityToKeepBoneMeal),
                    Codec.intRange(1, 10000).fieldOf("fermentation_barrel_time").orElse(DEFAULT.fermentationBarrelTime).forGetter(c -> c.fermentationBarrelTime),
                    Codec.intRange(0, 100).fieldOf("grape_growth_speed").orElse(DEFAULT.grapeGrowthSpeed).forGetter(c -> c.grapeGrowthSpeed)
            ).apply(builder, VineryConfig::new)
    );

    @Override
    public HashMap<String, String> getComments() {
        return Util.make(new HashMap<>(), map -> {
            map.put("enable_wine_maker_set_bonus", """
                    Whether the winemaker armor should give a set bonus, which can prevent your bone meal from being used""");
            map.put("probability_to_keep_bone_meal", """
                    Probability for the set bonus to work (in %)""");
            map.put("probability_for_damage", """
                    Probability for damaging armor on using the set bonus (in %)""");
            map.put("damage_per_use", """
                    Amount of damage when the armor gets damaged because of the set bonus""");
            map.put("grape_growth_speed", """
                    Percentage multiplier for the growth speed of grapes""");
            map.put("wine_trader_chance", """
                    How many % of the normal wandering traders should be a wandering wine trader?""");
            map.put("year_length_in_days", """
                    Length of a year (in days).""");
            map.put("years_per_effect_level", """
                    Years per effect level""");
            map.put("fermentation_barrel_time", """
                    Ticks it takes to ferment a bottle""");
        });
    }

    @Override
    public String getHeader() {
        return """
               Vinery Config
               
               ===========
               Discord: https://discord.gg/Vqu6wYZwdZ
               Modrinth: https://modrinth.com/mod/vinery
               CurseForge: https://www.curseforge.com/minecraft/mc-mods/lets-do-wine""";
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
