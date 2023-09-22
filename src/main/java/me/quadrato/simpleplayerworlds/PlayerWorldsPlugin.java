package me.quadrato.simpleplayerworlds;

import com.google.inject.Inject;

import me.quadrato.simpleplayerworlds.commands.*;
import me.quadrato.simpleplayerworlds.functions.WorldManagerFunctions;
import me.quadrato.simpleplayerworlds.listener.*;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

@Plugin(
        id = "simpleplayerworlds",
        name = "simpleplayerworlds",
        description = "Per Player World Plugin for the Unicorn MC Community",
        authors = { "JustZeroooTV" }
)
public class PlayerWorldsPlugin {

    public final PluginContainer container;
    public final Logger logger;

    public static PlayerWorldsPlugin plugin;

    @Inject
    public PlayerWorldsPlugin(PluginContainer container, Logger logger) {
        plugin = this;
        this.container = container;
        this.logger = logger;
    }

    CommandSpec worldhome = CommandSpec.builder()
            .description(Text.of("worldhome"))
            .executor(new WorldHomeCMD())
            .build();
    CommandSpec worldhomevoid = CommandSpec.builder()
            .description(Text.of("worldhomevoid"))
            .permission("simpleplayerworlds.worldhomevoid")
            .executor(new WorldHomeVoidCMD())
            .build();
    CommandSpec worldvisit = CommandSpec.builder()
            .description(Text.of("worldvisit"))
            .permission("simpleplayerworlds.worldvisit")
            .executor(new WorldVisitCMD())
            .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
            .build();

    CommandSpec worldsethome = CommandSpec.builder()
            .description(Text.of("worldsethome"))
            .executor(new WorldSetHomeCMD())
            .build();

    CommandSpec worldspawn = CommandSpec.builder()
            .description(Text.of("worldspawn"))
            .executor(new WorldSpawnCMD())
            .build();

    CommandSpec worldcommunity = CommandSpec.builder()
            .description(Text.of("worldcommunity"))
            .executor(new WorldCommunityCMD())
            .build();

    CommandSpec worldfarm = CommandSpec.builder()
            .description(Text.of("worldfarm"))
            .executor(new WorldFarmCMD())
            .build();

    CommandSpec worldinfo = CommandSpec.builder()
            .description(Text.of("worldinfo"))
            .executor(new WorldInfoCMD())
            .build();

    CommandSpec worldpvp = CommandSpec.builder()
            .description(Text.of("worldpvp"))
            .executor(new WorldPvpCmd())
            .build();

    CommandSpec myCommandSpec = CommandSpec.builder()
            .description(Text.of("world"))
            .executor(new WorldCMD())
            .child(worldhome, "home")
            .child(worldhomevoid, "void")
            .child(worldspawn, "spawn")
            .child(worldcommunity, "community")
            .child(worldfarm, "farm")
            .child(worldinfo, "info")
            .child(worldvisit, "visit")
            .child(worldsethome, "sethome")
            .child(worldpvp, "pvp")
            .build();

    @Listener
    public void onServerInitialization(final GameInitializationEvent event) {

        Sponge.getServer().getAllWorldProperties().forEach(worldProps-> {

            if (worldProps.getWorldName().startsWith("player-")) {

                worldProps.setLoadOnStartup(false);

                worldProps.setGameRule("mobGriefing", "false");
                worldProps.setGameRule("keepInventory", "true");

                Sponge.getServer().saveWorldProperties(worldProps);
            }
        });
    }

    @Listener
    public void onServerStart(final GameStartedServerEvent event) {
        this.logger.info("Simple Player Worlds started!");
        EventManager manager = Sponge.getEventManager();
        Sponge.getCommandManager().register(this, myCommandSpec, "world");
        manager.registerListeners(this, new JoinListener());
        manager.registerListeners(this, new QuitListener());
        manager.registerListeners(this, new ChatListener());
        manager.registerListeners(this, new RespawnListener());
        manager.registerListeners(this, new DeathListener());
        manager.registerListeners(this, new WorldManagerFunctions());
    }

    public static PlayerWorldsPlugin getPlugin() {
        return plugin;
    }
}
