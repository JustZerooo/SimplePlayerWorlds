package me.quadrato.simpleplayerworlds.listener;

import me.quadrato.simpleplayerworlds.commands.*;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandListener {

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
}
