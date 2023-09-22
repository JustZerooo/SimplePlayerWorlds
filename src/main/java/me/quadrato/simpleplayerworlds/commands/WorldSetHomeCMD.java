package me.quadrato.simpleplayerworlds.commands;

import me.quadrato.simpleplayerworlds.Helpers;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class WorldSetHomeCMD implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            return CommandResult.empty();
        }
        Player p = (Player) src;
        if (!Helpers.isPlayerOwnWorld(p)) {
            p.sendMessage(Text.of(TextColors.WHITE, "[SPW] ", TextColors.YELLOW, "You can use this command only in ", TextColors.BLUE, "YOUR", TextColors.YELLOW, " world."));
            return CommandResult.empty();
        }

        Location<World> loc = p.getLocation();

        World world = loc.getExtent();
        world.getProperties().setSpawnPosition(loc.getBlockPosition());
        p.sendMessage(Text.of(TextColors.WHITE, "[SPW] ", TextColors.YELLOW, "You have set a new ", TextColors.BLUE, "Spawn Point", TextColors.YELLOW, " for your world."));
        return CommandResult.success();
    }
}
