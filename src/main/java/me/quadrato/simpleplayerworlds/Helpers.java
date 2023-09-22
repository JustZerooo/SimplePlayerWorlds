package me.quadrato.simpleplayerworlds;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;
import java.util.UUID;

public class Helpers {

    public static String PLUGIN_PREFIX = String.valueOf(Text.of(TextColors.WHITE, "[" + TextColors.BLUE, "SPW", TextColors.WHITE, "] "));
    public static String COMMUNITY_WORLD = "community";
    public static String FARM_WORLD = "farmworld";
    public static String SPAWN_WORLD = "world";

    public static String getPlayerWorldName(UUID uuid, boolean isVoid) {
        return "player-" + uuid.toString() + (isVoid ? "-void" : "");
    }

    public static boolean isPlayerOwnWorld(Player player) {
        return player.getWorld().getName().startsWith("player-" + player.getUniqueId());
    }

    public static boolean isInVoidWorld(Player player) {
        return isInPlayerWorld(player, true);
    }

    public static boolean isInPlayerWorld(Player player) {
        return isInPlayerWorld(player, false);
    }

    private static boolean isInPlayerWorld(Player player, boolean isVoid) {
        return player.getWorld().getName().equals(Helpers.getPlayerWorldName(player.getUniqueId(), isVoid));
    }

    public Optional<String> getNameForUuid(UUID uuid) {
        UserStorageService uss = Sponge.getServiceManager().provideUnchecked(UserStorageService.class);
        Optional<User> oUser = uss.get(uuid);

        if (oUser.isPresent()) {
            // the name with which that player has been online the last time
            String name = oUser.get().getName();
            return Optional.of(name);
        } else {
            // a player with that uuid has never been on your server
            return Optional.empty();
        }
    }
}