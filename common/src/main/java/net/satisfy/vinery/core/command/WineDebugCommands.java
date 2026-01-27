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
import net.satisfy.vinery.platform.PlatformHelper;

public final class WineDebugCommands {

    public static void init() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registryAccess, selection) -> dispatcher.register(
                Commands.literal("wine")
                        .requires(source -> source.hasPermission(2) && source.getEntity() instanceof ServerPlayer)
                        .then(Commands.literal("age")
                                .then(Commands.argument("years", IntegerArgumentType.integer(0, 100000))
                                        .executes(ctx -> ageHeld(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "years")))))
                        .then(Commands.literal("info").executes(ctx -> infoHeld(ctx.getSource())))
        ));
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
        WineYearComponent component = getOrCreateComponent(stack, level);

        int currentDay = WineYears.getDays(level);
        int safeDaysPerYear = Math.max(1, component.daysPerYear());
        int targetBrewedDay = Math.max(0, currentDay - (years * safeDaysPerYear));

        stack.set(DataComponentRegistry.WINE_YEAR.get(), new WineYearComponent(
                targetBrewedDay,
                component.daysPerYear(),
                component.yearsPerEffectLevel(),
                component.startDuration(),
                component.durationPerYear(),
                component.maxDuration(),
                component.maxLevel()
        ));

        String message = "Wine age set to " + years + " years";
        source.sendSuccess(() -> Component.literal(message), false);
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
        WineYearComponent component = getOrCreateComponent(stack, level);

        int ageYears = WineYears.getWineAgeYears(stack, level);
        int ageDays = WineYears.getWineAgeDays(stack, level);
        int amplifier = WineYears.getEffectLevel(stack, level);
        int durationTicks = WineYears.getEffectDuration(stack, level);

        String message = "Age: " + ageYears + "y, " + ageDays + "d | Amp: " + amplifier + " | Dur: " + durationTicks + " ticks"
                + " | brewedDay: " + component.brewedDay()
                + " | daysPerYear: " + component.daysPerYear()
                + " | yearsPerEffectLevel: " + component.yearsPerEffectLevel()
                + " | startDuration: " + component.startDuration()
                + " | durationPerYear: " + component.durationPerYear()
                + " | maxDuration: " + component.maxDuration()
                + " | maxLevel: " + component.maxLevel();

        source.sendSuccess(() -> Component.literal(message), false);
        return 1;
    }

    private static WineYearComponent getOrCreateComponent(ItemStack stack, Level level) {
        WineYearComponent existing = stack.get(DataComponentRegistry.WINE_YEAR.get());
        if (existing != null) {
            return existing;
        }

        int brewedDay = WineYears.getDays(level);

        WineYearComponent created = new WineYearComponent(
                brewedDay,
                Math.max(1, PlatformHelper.getWineDaysPerYear()),
                Math.max(1, PlatformHelper.getWineYearsPerEffectLevel()),
                Math.max(0, PlatformHelper.getWineStartDuration()),
                Math.max(0, PlatformHelper.getWineDurationPerYear()),
                Math.max(0, PlatformHelper.getWineMaxDuration()),
                Math.max(0, PlatformHelper.getWineMaxLevel())
        );

        stack.set(DataComponentRegistry.WINE_YEAR.get(), created);
        return created;
    }
}