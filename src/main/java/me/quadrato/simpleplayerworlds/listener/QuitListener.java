package me.quadrato.simpleplayerworlds.listener;

import me.quadrato.simpleplayerworlds.Helpers;
import me.quadrato.simpleplayerworlds.functions.WorldManagerFunctions;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class QuitListener {

    @Listener
    public void onSomeEvent(ClientConnectionEvent.Disconnect event) {
        event.setMessageCancelled(true);

        Player player = (Player) event.getSource();

        if (player.hasPermission("simpleplayerworlds.owner")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.RED, " - ", TextColors.GRAY, "] ", TextColors.DARK_RED, player.getName()));
        } else if (player.hasPermission("simpleplayerworlds.admin")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.RED, " - ", TextColors.GRAY, "] ", TextColors.RED, player.getName()));
        } else if (player.hasPermission("simpleplayerworlds.supporter")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.RED, " - ", TextColors.GRAY, "] ", TextColors.BLUE, player.getName()));
        } else if (player.hasPermission("simpleplayerworlds.mod")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.RED, " - ", TextColors.GRAY, "] ", TextColors.DARK_GREEN, player.getName()));
        } else if (player.hasPermission("simpleplayerworlds.vip")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.RED, " - ", TextColors.GRAY, "] ", TextColors.GOLD, player.getName()));
        } else if (player.hasPermission("simpleplayerworlds.booster")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.RED, " - ", TextColors.GRAY, "] ", TextColors.LIGHT_PURPLE, player.getName()));
        }else {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.RED, " - ", TextColors.GRAY, "] ", TextColors.AQUA, player.getName()));
        }

        boolean isVoid = false;
        String worldName = Helpers.getPlayerWorldName(player.getUniqueId(), isVoid);

        Optional<World> opWorld = Sponge.getServer().getWorld(worldName);

        if (opWorld.isPresent()) {
            WorldManagerFunctions.unloadPlayerWorld(worldName);
        }
    }
}
