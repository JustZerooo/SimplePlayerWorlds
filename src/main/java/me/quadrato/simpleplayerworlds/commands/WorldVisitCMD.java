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
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class WorldVisitCMD implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            return CommandResult.empty();
        }
        Player sourcePlayer = (Player) src;
        Player argumentPlayer = args.requireOne("player");
        if (sourcePlayer.equals(argumentPlayer)) {
            sourcePlayer.sendMessage(Text.of("[SPW] §7Du kannst deine §2eigene §7Welt nicht besichtigen!"));
            return CommandResult.empty();
        }
        String sourcePlayerWorld = Helpers.getPlayerWorldName(sourcePlayer.getUniqueId(), false);
        Optional<World> opSourcePlayerWorld = Sponge.getServer().getWorld(sourcePlayerWorld);
        sourcePlayer.sendMessage(Text.of("[SPW] §7Die Welt von §2" + argumentPlayer.getName() + " §7wird geladen..."));

        if (!opSourcePlayerWorld.isPresent()) {
            opSourcePlayerWorld = Optional.of(new WorldManagerFunctions().createAndLoadPlayerWorld(argumentPlayer, !Helpers.isInPlayerWorld(argumentPlayer)));
        }

        Location<World> spawn = opSourcePlayerWorld.get().getSpawnLocation();
        WorldManagerFunctions.teleportAsVisitor(spawn.getX(), spawn.getY(), spawn.getZ(), sourcePlayer, argumentPlayer);
        sourcePlayer.sendMessage(Text.of("[SPW] §7Du wurdest in die Welt von §2 " + argumentPlayer.getName() + " §7teleportiert"));
        sourcePlayer.offer(Keys.GAME_MODE, GameModes.SPECTATOR);
        return CommandResult.success();
    }
}
