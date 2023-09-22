package me.quadrato.simpleplayerworlds.commands;

import me.quadrato.simpleplayerworlds.Helpers;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class WorldPvpCmd implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if (!(src instanceof Player)) {
            return CommandResult.empty();
        }

        boolean isVoid = false;
        Player sourcePlayer = (Player) src;

        String worldName = Helpers.getPlayerWorldName(sourcePlayer.getUniqueId(), isVoid);
        
        if (sourcePlayer.getWorld().getName().equalsIgnoreCase(worldName)) {

            World world = Sponge.getServer().getWorld(worldName).get();

            boolean pvpStatus = world.getProperties().isPVPEnabled();
            if (pvpStatus) {
                world.getProperties().setPVPEnabled(false);
                sourcePlayer.sendMessage(Text.of(TextColors.WHITE, "[SPW] ", TextColors.YELLOW, "You have", TextColors.GREEN, " disabled ", TextColors.YELLOW, "the pvp mode!"));
                world.sendMessage(Text.of(TextColors.WHITE, "[SPW] ", TextColors.YELLOW, "PVP Mode has been", TextColors.GREEN, " disabled", TextColors.YELLOW, " by ", TextColors.BLUE, sourcePlayer.getName(), TextColors.YELLOW, "!"));
            } else {
                world.getProperties().setPVPEnabled(true);
                sourcePlayer.sendMessage(Text.of(TextColors.WHITE, "[SPW] ", TextColors.YELLOW, "You have", TextColors.RED, " enabled ", TextColors.YELLOW, "the pvp mode!"));
                world.sendMessage(Text.of(TextColors.WHITE, "[SPW] ", TextColors.YELLOW, "PVP Mode has been", TextColors.RED, " enabled", TextColors.YELLOW, " by ", TextColors.BLUE, sourcePlayer.getName(), TextColors.YELLOW, "!"));
            }

        } else {
            sourcePlayer.sendMessage(Text.of(TextColors.WHITE, "[SPW] ", TextColors.YELLOW, "Only the owner of this world can toggle the pvp mode!"));
        }

        return CommandResult.success();
    }
}
