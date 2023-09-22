package me.quadrato.simpleplayerworlds.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class WorldCMD implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext context) throws CommandException {

        if (src instanceof Player) {
            Player player = (Player) src;

            player.sendMessage(Text.of("All world commands :"));
            player.sendMessage(Text.of(" "));
            player.sendMessage(Text.of(" - /world home :"));
            player.sendMessage(Text.of("Use this command to teleport to your own world."));
            player.sendMessage(Text.of(" "));
            player.sendMessage(Text.of(" - /world portal:"));
            player.sendMessage(Text.of("Use this command to teleport to the portal world."));
            player.sendMessage(Text.of(" "));
            player.sendMessage(Text.of(" - /world community:"));
            player.sendMessage(Text.of("Use this command to teleport to the community world."));
            player.sendMessage(Text.of(" "));
            player.sendMessage(Text.of(" - /world farm:"));
            player.sendMessage(Text.of("Use this command to teleport to the Farm World."));
            player.sendMessage(Text.of(" "));
            player.sendMessage(Text.of(" - /world sethome:"));
            player.sendMessage(Text.of("Use this command to move the home position to your current position."));
            player.sendMessage(Text.of(" "));
            player.sendMessage(Text.of(" - /world visit <Name>:"));
            player.sendMessage(Text.of("Use this command to visit the world of another player."));
            player.sendMessage(Text.of(" "));
            player.sendMessage(Text.of(" - /world pvp:"));
            player.sendMessage(Text.of("Toggle the pvp mode of the world, by default it is disabled."));

        } else {
            src.sendMessage(Text.of("Only player can use this command"));
        }

        return CommandResult.success();
    }
}