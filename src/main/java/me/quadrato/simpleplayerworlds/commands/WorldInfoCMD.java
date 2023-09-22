package me.quadrato.simpleplayerworlds.commands;

import me.quadrato.simpleplayerworlds.Helpers;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class WorldInfoCMD implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {

            Player p = (Player) src;

            String playerWorld = Helpers.getPlayerWorldName(p.getUniqueId(), false);
            String playerVoidWorld = Helpers.getPlayerWorldName(p.getUniqueId(), true);

            Optional<World> optionalWorld = Sponge.getServer().getWorld(playerWorld);
            Optional<World> optionalWorld2 = Sponge.getServer().getWorld(playerVoidWorld);

            World world = null;
            if (optionalWorld.isPresent()) {
                world = optionalWorld.get();
                String rawVoidDimensionID = Sponge.getServer().getWorld(playerWorld).get().getProperties().getAdditionalProperties().getInt(DataQuery.of("SpongeData", "dimensionId")).toString();
                String rawVoidDimensionID2 = rawVoidDimensionID.replace("Optional[", "");
                String dimensionID = rawVoidDimensionID2.replace("]", "");
                Text text = Text.join(Text.of("Your World Number : "), Text.of(TextColors.BLUE, dimensionID));
                p.sendMessage(text);
            } else {
                p.sendMessage(Text.of("Your World Number : N/A"));
            }

            World world2 = null;
            if (optionalWorld2.isPresent()) {
                world2 = optionalWorld2.get();
                String rawVoidDimensionID = Sponge.getServer().getWorld(playerVoidWorld).get().getProperties().getAdditionalProperties().getInt(DataQuery.of("SpongeData", "dimensionId")).toString();
                String rawVoidDimensionID2 = rawVoidDimensionID.replace("Optional[", "");
                String dimensionID = rawVoidDimensionID2.replace("]", "");
                Text text = Text.join(Text.of("Your Void World Number : "), Text.of(TextColors.BLUE, dimensionID));
                p.sendMessage(text);
            } else {
                p.sendMessage(Text.of("Your Void World Number : N/A"));
            }
        }
        return CommandResult.success();
    }
}
