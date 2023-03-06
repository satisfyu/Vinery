package satisfyu.vinery.villager.activity;

import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.util.registry.Registry;

public class ModActivity{
    public static final Activity SHOP = register("shop");

    private static Activity register(String id) {
        return Registry.register(Registry.ACTIVITY, id, new Activity(id));
    }

    public static void init() {
        System.out.println("Registering Activity");//TODO Logger
    }
}
