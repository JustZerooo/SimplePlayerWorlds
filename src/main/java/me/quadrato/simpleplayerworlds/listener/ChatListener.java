package me.quadrato.simpleplayerworlds.listener;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ChatListener {

    @Listener
    public void onChat(MessageChannelEvent.Chat e) {

        Player player = (Player) e.getSource();
        Text msg = e.getRawMessage();

        if (player.hasPermission("simpleplayerworlds.owner")) {
            Text prefix = Text.of(TextColors.WHITE, "[", TextColors.DARK_RED, "Owner", TextColors.WHITE, "] ", TextColors.DARK_RED, player.getName(), TextColors.WHITE, " : ");
            Text cmsg = Text.of(TextColors.WHITE, e.getRawMessage());
            e.setMessage(Text.join(prefix, cmsg));
        } else if (player.hasPermission("simpleplayerworlds.admin")) {
            Text prefix = Text.of(TextColors.WHITE, "[", TextColors.RED, "Admin", TextColors.WHITE, "] ", TextColors.RED, player.getName(), TextColors.WHITE, " : ");
            Text cmsg = Text.of(TextColors.WHITE, e.getRawMessage());
            e.setMessage(Text.join(prefix, cmsg));
        } else if (player.hasPermission("simpleplayerworlds.supporter")) {
            Text prefix = Text.of(TextColors.WHITE, "[", TextColors.BLUE, "Supporter", TextColors.WHITE, "] ", TextColors.BLUE, player.getName(), TextColors.WHITE, " : ");
            Text cmsg = Text.of(TextColors.WHITE, e.getRawMessage());
            e.setMessage(Text.join(prefix, cmsg));
        } else if (player.hasPermission("simpleplayerworlds.mod")) {
            Text prefix = Text.of(TextColors.WHITE, "[", TextColors.DARK_GREEN, "Moderator", TextColors.WHITE, "] ", TextColors.DARK_GREEN, player.getName(), TextColors.WHITE, " : ");
            Text cmsg = Text.of(TextColors.WHITE, e.getRawMessage());
            e.setMessage(Text.join(prefix, cmsg));
        } else if (player.hasPermission("simpleplayerworlds.vip")) {
            Text prefix = Text.of(TextColors.WHITE, "[", TextColors.GOLD, "VIP", TextColors.WHITE, "] ", TextColors.GOLD, player.getName(), TextColors.WHITE, " : ");
            Text cmsg = Text.of(TextColors.WHITE, e.getRawMessage());
            e.setMessage(Text.join(prefix, cmsg));
        } else if (player.hasPermission("simpleplayerworlds.booster")) {
            Text prefix = Text.of(TextColors.WHITE, "[", TextColors.LIGHT_PURPLE, "Booster", TextColors.WHITE, "] ", TextColors.LIGHT_PURPLE, player.getName(), TextColors.WHITE, " : ");
            Text cmsg = Text.of(TextColors.WHITE, e.getRawMessage());
            e.setMessage(Text.join(prefix, cmsg));
        } else {
            Text prefix = Text.of(TextColors.WHITE, "[", TextColors.AQUA, "Player", TextColors.WHITE, "] ", TextColors.AQUA, player.getName(), TextColors.WHITE, " : ");
            Text cmsg = Text.of(TextColors.WHITE, e.getRawMessage());
            e.setMessage(Text.join(prefix, cmsg));
        }
    }
}
