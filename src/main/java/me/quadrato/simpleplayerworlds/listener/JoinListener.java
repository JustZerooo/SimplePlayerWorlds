package me.quadrato.simpleplayerworlds.listener;

import me.quadrato.simpleplayerworlds.functions.WorldManagerFunctions;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

public class JoinListener {
    @Listener
    public void onSomeEvent(ClientConnectionEvent.Join event) {

        event.setMessageCancelled(true);
        Player player = (Player) event.getSource();

        if (player.hasPermission("simpleplayerworlds.owner")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.GREEN, " + ", TextColors.GRAY, "] ", TextColors.DARK_RED, player.getName()));
        } else if (player.hasPermission("simpleplayerworlds.admin")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.GREEN, " + ", TextColors.GRAY, "] ", TextColors.RED, player.getName()));
        } else if (player.hasPermission("simpleplayerworlds.supporter")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.GREEN, " + ", TextColors.GRAY, "] ", TextColors.BLUE, player.getName()));
        } else if (player.hasPermission("simpleplayerworlds.mod")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.GREEN, " + ", TextColors.GRAY, "] ", TextColors.DARK_GREEN, player.getName()));
        } else if (player.hasPermission("simpleplayerworlds.vip")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.GREEN, " + ", TextColors.GRAY, "] ", TextColors.GOLD, player.getName()));
        } else if (player.hasPermission("simpleplayerworlds.booster")) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.GREEN, " + ", TextColors.GRAY, "] ", TextColors.LIGHT_PURPLE, player.getName()));
        }else {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GRAY, "[", TextColors.GREEN, " + ", TextColors.GRAY, "] ", TextColors.AQUA, player.getName()));
        }

        player.sendMessage(Text.of(TextColors.YELLOW, "Hey ", TextColors.WHITE, player.getName(), TextColors.YELLOW,"!"));

        player.sendMessage(Text.of(TextColors.YELLOW, "Welcome at Unicorn Network Minecraft Server"));
        player.sendMessage(Text.of(TextColors.YELLOW, "To create your own world type: ", TextColors.BLUE, "/world home", TextColors.YELLOW, "!"));
    }
}
