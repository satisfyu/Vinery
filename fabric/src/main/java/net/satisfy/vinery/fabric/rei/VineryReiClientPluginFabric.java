package net.satisfy.vinery.fabric.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import net.satisfy.vinery.compat.rei.VineryReiClientPlugin;


public class VineryReiClientPluginFabric implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        VineryReiClientPlugin.registerCategories(registry);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        VineryReiClientPlugin.registerDisplays(registry);
    }
}
