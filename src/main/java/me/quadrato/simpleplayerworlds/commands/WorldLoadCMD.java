package me.quadrato.simpleplayerworlds.commands;

import me.quadrato.simpleplayerworlds.Helpers;
import me.quadrato.simpleplayerworlds.functions.WorldManagerFunctions;
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

public class WorldLoadCMD implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            return CommandResult.empty();
        }

        Player player = (Player) src;

        boolean isVoid = false;
        String worldName = Helpers.getPlayerWorldName(player.getUniqueId(), isVoid);

        Optional<World> opWorld = Sponge.getServer().getWorld(worldName);

        if (opWorld.isPresent()) {
            WorldManagerFunctions.loadPlayerWorld(worldName);
        }

        return CommandResult.success();
    }
}
