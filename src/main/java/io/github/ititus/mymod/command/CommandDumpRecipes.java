package io.github.ititus.mymod.command;

import io.github.ititus.mymod.recipe.dumper.RecipeDumper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Loader;

public class CommandDumpRecipes extends CommandBase {

    public static final String NAME = "dump_recipes";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return CommandMyMod.getUsage(getName());
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (Loader.isModLoaded("jei")) {
            RecipeDumper.dumpAll(server, sender, args);
        } else {
            sender.sendMessage(new TextComponentString("JEI is not installed, cannot dump recipes!"));
        }
    }
}
