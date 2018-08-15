package io.github.ititus.mymod.command;

import io.github.ititus.mymod.MyMod;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.command.CommandTreeHelp;

public class CommandMyMod extends CommandTreeBase implements IClientCommand {

    public CommandMyMod(boolean client) {
        if (client) {
            addSubcommand(new CommandDumpRecipes());
        } else {
        }
        addSubcommand(new CommandTreeHelp(this));
    }

    public static String getUsage(String name) {
        return "commands.mymod." + name + ".usage";
    }

    @Override
    public String getName() {
        return MyMod.MOD_ID;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return getUsage(getName());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }
}
