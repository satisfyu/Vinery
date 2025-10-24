package net.satisfy.vinery.core.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.core.components.WineYearComponent;
import net.satisfy.vinery.core.registry.DataComponentRegistry;
import net.satisfy.vinery.core.util.WineYears;

public final class WineDebugCommands {

    public static void init() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registryAccess, selection) -> {
            dispatcher.register(
                    Commands.literal("wine")
                            .requires(src -> src.hasPermission(2))
                            .then(
                                    Commands.literal("age")
                                            .then(
                                                    Commands.argument("years", IntegerArgumentType.integer(0, 100000))
                                                            .executes(ctx -> ageHeld(
                                                                    ctx.getSource(),
                                                                    IntegerArgumentType.getInteger(ctx, "years")
                                                            ))
                                            )
                            )
                            .then(
                                    Commands.literal("info")
                                            .executes(ctx -> infoHeld(ctx.getSource()))
                            )
            );
        });
    }

    private static int ageHeld(CommandSourceStack source, int years) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("Player required"));
            return 0;
        }
        ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty()) {
            source.sendFailure(Component.literal("No item in main hand"));
            return 0;
        }

        Level level = player.serverLevel();
        int targetYear = WineYears.getYear(level) - years;

        int amplifier = WineYears.getEffectLevel(stack, level);
        int duration = WineYears.getEffectDuration(stack, level);
        stack.set(DataComponentRegistry.WINE_YEAR.get(), new WineYearComponent(targetYear, amplifier, duration));

        source.sendSuccess(() -> Component.literal("Wine age set to " + years + " years"), false);
        return 1;
    }

    private static int infoHeld(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("Player required"));
            return 0;
        }
        ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty()) {
            source.sendFailure(Component.literal("No item in main hand"));
            return 0;
        }
        Level level = player.serverLevel();
        int age = WineYears.getWineAge(stack, level);
        int days = WineYears.getWineAgeDays(stack, level);
        int amplifier = WineYears.getEffectLevel(stack, level);
        int duration = WineYears.getEffectDuration(stack, level);
        source.sendSuccess(() -> Component.literal("Age: " + age + "y, " + days + "d | Amp: " + amplifier + " | Dur: " + duration + " ticks"), false);
        return 1;
    }
}
