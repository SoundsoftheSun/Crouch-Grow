package net.soundsofthesun.crouchGrow.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import static net.minecraft.server.permissions.Permissions.COMMANDS_ADMIN;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.soundsofthesun.crouchGrow.CrouchGrow;
import net.soundsofthesun.crouchGrow.attachments.DoGrow;
import net.soundsofthesun.crouchGrow.attachments.GrowChance;
import net.soundsofthesun.crouchGrow.attachments.GrowRadius;
import net.soundsofthesun.crouchGrow.attachments.SAttachments;

public class SCommands {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal(CrouchGrow.MOD_ID)
                    .requires(source -> source.permissions().hasPermission(COMMANDS_ADMIN))
                    .executes(SCommands::help)
                    .then(Commands.literal("config")
                            .executes(SCommands::help))
                    .then(Commands.literal("enable")
                            .executes(SCommands::enable))
                    .then(Commands.literal("disable")
                            .executes(SCommands::disable))
                    .then(Commands.literal("set_chance")
                            .then(Commands.argument("chance", IntegerArgumentType.integer(1))
                                    .executes(SCommands::setChance)))
                    .then(Commands.literal("set_radius")
                            .then(Commands.argument("radius", DoubleArgumentType.doubleArg(0))
                                    .executes(SCommands::setRadius)))
            );
        });
    }


    private static int help(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.literal(CrouchGrow.MOD_ID+": player radius: "+ context.getSource().getLevel().getAttachedOrElse(SAttachments.RADIUS, GrowRadius.DEFAULT).n()+ ", grow chance: "+context.getSource().getLevel().getAttachedOrElse(SAttachments.CHANCE, GrowChance.DEFAULT).n()), false);
        return 1;
    }

    private static int enable(CommandContext<CommandSourceStack> context) {
        context.getSource().getLevel().setAttached(SAttachments.DO_GROW, new DoGrow(true));
        context.getSource().sendSuccess(() -> Component.literal(CrouchGrow.MOD_ID+": enabled"), false);
        return 1;
    }

    private static int disable(CommandContext<CommandSourceStack> context) {
        context.getSource().getLevel().setAttached(SAttachments.DO_GROW, new DoGrow(false));
        context.getSource().sendSuccess(() -> Component.literal(CrouchGrow.MOD_ID+": disabled"), false);
        return 1;
    }

    private static int setChance(CommandContext<CommandSourceStack> context) {
        context.getSource().getLevel().setAttached(SAttachments.CHANCE, new GrowChance(context.getArgument("chance", Integer.class)));
        context.getSource().sendSuccess(() -> Component.literal("Set Grow Chance to "+context.getSource().getLevel().getAttachedOrElse(SAttachments.CHANCE, GrowChance.DEFAULT).n()), false);
        return 1;
    }

    private static int setRadius(CommandContext<CommandSourceStack> context) {
        context.getSource().getLevel().setAttached(SAttachments.RADIUS, new GrowRadius(context.getArgument("radius", Double.class)));
        context.getSource().sendSuccess(() -> Component.literal("Set Grow Radius to "+context.getSource().getLevel().getAttachedOrElse(SAttachments.RADIUS, GrowRadius.DEFAULT).n()), false);
        return 1;
    }



}
