package satisfyu.vinery.util.tab;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.util.Window;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static satisfyu.vinery.util.tab.Tab.MAX_RECOMMENDED_TABS;
import static satisfyu.vinery.util.tab.TabUtil.suffixIdentifier;


public class TabbedItemGroup extends ItemGroup {
    private final Identifier id;
    private final GUIIcon<?> icon;
    private final List<Tab> tabs;
    private final String tabbedTextKey;
    private final Tab.Predicate defaultPredicate;

    private int selectedTabIndex = -1;

    protected TabbedItemGroup(Identifier id, Function<TabbedItemGroup, GUIIcon<?>> icon, List<Tab> tabs, Tab.Predicate defaultPredicate) {
        super(getNextItemGroupIndex(), "%s.%s".formatted(id.getNamespace(), id.getPath()));
        this.id = id;
        this.icon = icon.apply(this);
        this.tabs = tabs;
        this.defaultPredicate = defaultPredicate;

        String key = this.getDisplayName().getContent() instanceof TranslatableTextContent content ? content.getKey() : "missingno";
        this.tabbedTextKey = "%s.tab".formatted(key);
    }

    public Identifier getId() {
        return this.id;
    }

    public List<Tab> getTabs() {
        return this.tabs;
    }

    public String getTabbedTextKey() {
        return this.tabbedTextKey;
    }

    public int getSelectedTabIndex() {
        return this.selectedTabIndex;
    }

    public Optional<Tab> getSelectedTab() {
        int i = this.getSelectedTabIndex();
        List<Tab> tabs = this.getTabs();
        return i == -1 ? Optional.empty() : Optional.of(tabs.get(i));
    }

    @Environment(EnvType.CLIENT)
    public void setSelectedTabIndex(int selectedTabIndex) {
        this.selectedTabIndex = selectedTabIndex;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen instanceof CreativeInventoryScreen creativeScreen) {
            Window window = client.getWindow();
            creativeScreen.init(client, window.getScaledWidth(), window.getScaledHeight());
        }
    }

    @Deprecated
    @Override
    public ItemStack createIcon() {
        return ItemStack.EMPTY;
    }

    public <T> Optional<T> getIconTexture(boolean hovered, Class<T> clazz) {
        return GUIIcon.optional(this.icon, hovered, this.isSelected(), clazz);
    }

    @Environment(EnvType.CLIENT)
    public boolean isSelected() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen instanceof CreativeInventoryScreen screen) return screen.getSelectedTab() == this.getIndex();
        throw new IllegalStateException("Current sreeen is not CreativeInventoryScreen");
    }

    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        Optional<Tab> tab = this.getSelectedTab();
        Tab.Predicate predicate = tab.map(Tab::getPredicate).orElse(this.defaultPredicate);
        Stream<Item> stream = Registries.ITEM.stream().filter(i -> predicate.test(this, i));
        for (Item item : stream.toList()) stacks.add(new ItemStack(item));
    }

    public Identifier getIconTexture() {
        Identifier id = this.getId();
        return new Identifier(id.getNamespace(), "textures/vinery/tabbed_item_group/icon/%s".formatted(id.getPath()));
    }

    public Identifier iconTex(String suffix) {
        return suffixIdentifier(this.getIconTexture(), suffix);
    }

    public Identifier baseIconTex() {
        return this.iconTex("");
    }

    public Identifier hoverIconTex() {
        return this.iconTex("hovered");
    }

    public Identifier selectedIconTex() {
        return this.iconTex("selected");
    }

    public GUIIcon<Identifier> icon() {
        return GUIIcon.of(this::baseIconTex, this::hoverIconTex, this::selectedIconTex);
    }

    public static TabbedItemGroup.Builder builder() {
        return new TabbedItemGroup.Builder();
    }

    protected static int getNextItemGroupIndex() {
        ((ItemGroupExtensions) ItemGroup.BUILDING_BLOCKS).fabric_expandArray();
        return ItemGroup.GROUPS.length - 1;
    }

    @Override
    public String toString() {
        return "TabbedItemGroup{" + "id=" + id + '}';
    }

    public static class Builder {
        private static final Logger LOGGER = LoggerFactory.getLogger("vinery");

        private final List<Tab> tabs = new ArrayList<>();
        private Tab.Predicate defaultPredicate = Tab.Predicate.CONTAINS;

        protected Builder() {}

        public Builder tab(Tab tab) {
            this.tabs.add(tab);
            return this;
        }

        public Builder defaultPredicate(Tab.Predicate predicate) {
            this.defaultPredicate = predicate;
            return this;
        }

        public TabbedItemGroup build(Identifier id, Function<TabbedItemGroup, GUIIcon<?>> icon) {
            TabbedItemGroup group = new TabbedItemGroup(id, icon, this.tabs, this.defaultPredicate);

            int tabCount = this.tabs.size();
            if (tabCount > MAX_RECOMMENDED_TABS) LOGGER.warn("Tabbed item group {} registered {} tabs (recommended max {})", id, tabCount, MAX_RECOMMENDED_TABS);
            else if (tabCount == 1) LOGGER.warn("Tabbed item group {} only registered 1 item group tab?", id);
            for (Tab tab : this.tabs) tab.addToGroup(group);

            return group;
        }
    }
}