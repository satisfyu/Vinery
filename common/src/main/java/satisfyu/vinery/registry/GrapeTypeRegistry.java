package satisfyu.vinery.registry;

import satisfyu.vinery.block.grape.GrapeType;

import java.util.HashSet;
import java.util.Set;

public class GrapeTypeRegistry {
    public static final Set<GrapeType> GRAPE_TYPE_TYPES = new HashSet<>();
    public static final GrapeType NONE = registerGrapeType("none");
    public static final GrapeType RED = registerGrapeType("red");
    public static final GrapeType WHITE = registerGrapeType("white");
    public static final GrapeType SAVANNA_RED = registerGrapeType("savanna_red");
    public static final GrapeType SAVANNA_WHITE = registerGrapeType("savanna_white");
    public static final GrapeType TAIGA_RED = registerGrapeType("taiga_red");
    public static final GrapeType TAIGA_WHITE = registerGrapeType("taiga_white");
    public static final GrapeType JUNGLE_RED = registerGrapeType("jungle_red", true);
    public static final GrapeType JUNGLE_WHITE = registerGrapeType("jungle_white", true);

    public static void register() {
    }

    public static void addGrapeAttributes() {
        RED.setItems(ObjectRegistry.RED_GRAPE, ObjectRegistry.RED_GRAPE_SEEDS, ObjectRegistry.red_grapejuice);
        WHITE.setItems(ObjectRegistry.WHITE_GRAPE, ObjectRegistry.WHITE_GRAPE_SEEDS, ObjectRegistry.white_grapejuice);
        SAVANNA_RED.setItems(ObjectRegistry.SAVANNA_RED_GRAPE, ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS, ObjectRegistry.red_savanna_grapejuice);
        SAVANNA_WHITE.setItems(ObjectRegistry.SAVANNA_WHITE_GRAPE, ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS, ObjectRegistry.white_savanna_grapejuice);
        TAIGA_RED.setItems(ObjectRegistry.TAIGA_RED_GRAPE, ObjectRegistry.TAIGA_RED_GRAPE_SEEDS, ObjectRegistry.red_taiga_grapejuice);
        TAIGA_WHITE.setItems(ObjectRegistry.TAIGA_WHITE_GRAPE, ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS, ObjectRegistry.white_taiga_grapejuice);
        JUNGLE_RED.setItems(ObjectRegistry.JUNGLE_RED_GRAPE, ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS, ObjectRegistry.red_jungle_grapejuice);
        JUNGLE_WHITE.setItems(ObjectRegistry.JUNGLE_WHITE_GRAPE, ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS, ObjectRegistry.white_jungle_grapejuice);
    }

    public static GrapeType registerGrapeType(String id) {
        return registerGrapeType(id, false);
    }

    public static GrapeType registerGrapeType(String id, boolean lattice) {
        GrapeType grapeType = new GrapeType(id, lattice);
        GRAPE_TYPE_TYPES.add(grapeType);
        return grapeType;
    }
}