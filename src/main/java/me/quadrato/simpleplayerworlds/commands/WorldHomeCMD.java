package me.quadrato.simpleplayerworlds.commands;

import me.quadrato.simpleplayerworlds.Helpers;
import me.quadrato.simpleplayerworlds.functions.WorldManagerFunctions;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class WorldHomeCMD implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            return CommandResult.empty();
        }
        Player player = (Player) src;

        player.sendMessage(Text.of(TextColors.WHITE, "[SPW] ", TextColors.YELLOW, "Your world is loading ..."));

        String worldName = Helpers.getPlayerWorldName(player.getUniqueId(), false);
        Optional<World> opWorld = Sponge.getServer().getWorld(worldName);

        if (!opWorld.isPresent()) {
            opWorld = Optional.of(new WorldManagerFunctions().createAndLoadPlayerWorld(player));
        }

        Location<World> spawn = opWorld.get().getSpawnLocation();
        Location<World> safeLoc = Sponge.getTeleportHelper().getSafeLocation(spawn).orElse(spawn);

        WorldManagerFunctions.teleportToPlayerWorld(safeLoc.getX(), safeLoc.getY(), safeLoc.getZ(), player, false);
        player.sendMessage(Text.of(TextColors.WHITE, "[SPW] ", TextColors.YELLOW, "You have been teleported into your own world."));
        player.offer(Keys.GAME_MODE, GameModes.SURVIVAL);
        return CommandResult.success();
    }
}
