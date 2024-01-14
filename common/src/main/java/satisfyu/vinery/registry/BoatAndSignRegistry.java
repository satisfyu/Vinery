package satisfyu.vinery.registry;


import de.cristelknight.doapi.DoApiExpectPlatform;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import de.cristelknight.doapi.terraform.boat.item.TerraformBoatItemHelper;
import de.cristelknight.doapi.terraform.sign.TerraformSignHelper;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.VineryIdentifier;

public class BoatAndSignRegistry {

    public static ResourceLocation CHERRY_BOAT_TYPE = new VineryIdentifier("cherry");

    public static final ResourceLocation CHERRY_SIGN_TEXTURE = new VineryIdentifier("entity/signs/cherry");
    public static final RegistrySupplier<Block> CHERRY_SIGN = ObjectRegistry.registerWithoutItem("cherry_sign", () -> TerraformSignHelper.getSign(CHERRY_SIGN_TEXTURE));
    public static final RegistrySupplier<Block> CHERRY_WALL_SIGN = ObjectRegistry.registerWithoutItem("cherry_wall_sign", () -> TerraformSignHelper.getWallSign(CHERRY_SIGN_TEXTURE));
    public static final RegistrySupplier<Item> CHERRY_SIGN_ITEM = ObjectRegistry.registerItem("cherry_sign", () -> new SignItem(ObjectRegistry.getSettings().stacksTo(16), CHERRY_SIGN.get(), CHERRY_WALL_SIGN.get()));
    public static final ResourceLocation CHERRY_HANGING_SIGN_TEXTURE = new VineryIdentifier("entity/signs/hanging/cherry");
    public static final ResourceLocation CHERRY_HANGING_SIGN_GUI_TEXTURE = new VineryIdentifier("textures/gui/hanging_signs/cherry");

    public static final RegistrySupplier<Block> CHERRY_HANGING_SIGN = ObjectRegistry.registerWithoutItem("cherry_hanging_sign", () -> TerraformSignHelper.getHangingSign(CHERRY_HANGING_SIGN_TEXTURE, CHERRY_HANGING_SIGN_GUI_TEXTURE));
    public static final RegistrySupplier<Block> CHERRY_WALL_HANGING_SIGN = ObjectRegistry.registerWithoutItem("cherry_wall_hanging_sign", () -> TerraformSignHelper.getWallHangingSign(CHERRY_HANGING_SIGN_TEXTURE, CHERRY_HANGING_SIGN_GUI_TEXTURE));
    public static final RegistrySupplier<Item> CHERRY_HANGING_SIGN_ITEM = ObjectRegistry.registerItem("cherry_hanging_sign", () -> new HangingSignItem(CHERRY_HANGING_SIGN.get(), CHERRY_WALL_HANGING_SIGN.get(), ObjectRegistry.getSettings().stacksTo(16)));

    public static RegistrySupplier<Item> CHERRY_BOAT = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS, "cherry_boat", CHERRY_BOAT_TYPE, false);
    public static RegistrySupplier<Item> CHERRY_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS, "cherry_chest_boat", CHERRY_BOAT_TYPE, true);
    public static void init() {
        DoApiExpectPlatform.registerBoatType(CHERRY_BOAT_TYPE, new TerraformBoatType.Builder().item(CHERRY_BOAT).chestItem(CHERRY_CHEST_BOAT).build());
    }
}
