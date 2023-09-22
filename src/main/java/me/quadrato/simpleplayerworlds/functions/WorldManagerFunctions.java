package me.quadrato.simpleplayerworlds.functions;

import com.flowpowered.math.vector.Vector3d;

import me.quadrato.simpleplayerworlds.Helpers;
import me.quadrato.simpleplayerworlds.PlayerWorldsPlugin;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldArchetype;
import org.spongepowered.api.world.WorldArchetypes;
import org.spongepowered.api.world.storage.WorldProperties;

import java.io.IOException;
import java.util.Optional;

public class WorldManagerFunctions {

    public World createAndLoadPlayerWorld(Player player, boolean isVoid) {
        String worldName = Helpers.getPlayerWorldName(player.getUniqueId(), isVoid);
        WorldArchetype archetype = isVoid ? WorldArchetypes.THE_VOID : WorldArchetypes.OVERWORLD;
        Optional<World> opWorld = Sponge.getServer().getWorld(worldName);
        if (opWorld.isPresent()) {
            return opWorld.get();
        }
        opWorld = Sponge.getServer().loadWorld(worldName);
        if (opWorld.isPresent()) {
            return opWorld.get();
        }
        try {
            WorldProperties properties = Sponge.getServer().createWorldProperties(worldName, archetype);
            return Sponge.getServer().loadWorld(properties).orElseThrow(() -> new IllegalStateException("[SPW] Could not load world that was just created for some reason"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public World createAndLoadPlayerWorld(Player player) {
        return createAndLoadPlayerWorld(player, false);
    }

    public World createAndLoadPlayerVoidWorld(Player player) {
        return createAndLoadPlayerWorld(player, true);
    }

    public World createAndLoadCustomWorld(String wName, boolean isVoid) {

        WorldArchetype archetype = isVoid ? WorldArchetypes.THE_VOID : WorldArchetypes.OVERWORLD;
        Optional<World> opWorld = Sponge.getServer().getWorld(wName);
        if (opWorld.isPresent()) {
            return opWorld.get();
        }

        opWorld = Sponge.getServer().loadWorld(wName);
        if (opWorld.isPresent()) {
            return opWorld.get();
        }
        try {
            WorldProperties properties = Sponge.getServer().createWorldProperties(wName, archetype);
            return Sponge.getServer().loadWorld(properties).orElseThrow(() -> new IllegalStateException("[SPW] Could not load world that was just created for some reason"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void spawnSafeAreaAt(Location<World> loc) {
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                Location<World> blockPos = loc.add(x - 1, loc.getY(), z - 1);
                if (blockPos.getBlockType().equals(BlockTypes.AIR)) {
                    continue;
                }
                blockPos.setBlockType(BlockTypes.STONE);
            }
        }
    }

    public static void teleportToPlayerWorld(double x, double y, double z, Player p, boolean isVoid) {
        String worldName = Helpers.getPlayerWorldName(p.getUniqueId(), isVoid);
        teleportToWorld(x, y, z, p, worldName);
        setKeepWorldLoaded(worldName);
    }

    public static void teleportToWorld(double x, double y, double z, Player player, String worldName) {
        World world = Sponge.getServer().getWorld(worldName).orElseThrow(() -> new RuntimeException("[SPW] World of '" + worldName + "' is not loaded"));
        Location<World> blockLoc = new Location<>(world, x, y, z);
        while (!blockLoc.getBlockType().equals(BlockTypes.AIR)) {
            blockLoc = blockLoc.getBlockRelative(Direction.UP);
        }
        spawnSafeAreaAt(blockLoc.getBlockRelative(Direction.DOWN));

        player.transferToWorld(world, blockLoc.getPosition().add(0, 0, 0));
    }

    public static void communityWorldTeleport(double x, double y, double z, Player player) {
        teleportToWorld(x, y, z, player, Helpers.COMMUNITY_WORLD);
    }

    public static void farmWorldTeleport(double x, double y, double z, Player player) {
        teleportToWorld(x, y, z, player, Helpers.FARM_WORLD);
    }

    public static void spawnlWorldTeleport(double x, double y, double z, Player player) {
        teleportToWorld(x, y, z, player, Helpers.SPAWN_WORLD);
    }

    public static void teleportAsVisitor(double x, double y, double z, Player p, Player target) {

        String playerViewWorld = "player-" + target.getUniqueId();

        World world = Sponge.getServer().getWorld(playerViewWorld).get();

        Location<World> blockLoc = new Location<World>(world, x, y, z);
        BlockType type = blockLoc.getBlock().getType();

        if (!type.equals(BlockTypes.AIR)) {
            teleportAsVisitor(x, y + 1, z, p, target);
        } else {
            Location<World> blockLoc2 = new Location<World>(world, x, y + 1, z);
            BlockType type2 = blockLoc2.getBlock().getType();
            if (!type2.equals(BlockTypes.AIR)) {
                teleportAsVisitor(x, y + 2, z, p, target);
            } else {
                p.transferToWorld(Sponge.getServer().getWorld(playerViewWorld).get(), new Vector3d(blockLoc.getX(), blockLoc.getY() + 0.2, blockLoc.getZ()));
                world.getProperties().setSpawnPosition(blockLoc.getBlockPosition());
            }
        }
    }

    public static void setKeepWorldLoaded(String worldName)
    {
        Sponge.getServer().getAllWorldProperties().forEach(worldProps-> {

            if (worldProps.getWorldName().contains(worldName)) {

                worldProps.setKeepSpawnLoaded(true);
                Sponge.getServer().saveWorldProperties(worldProps);
            }
        });
    }

    public static void unloadPlayerWorld(String worldName)
    {
        World world = Sponge.getGame().getServer().getWorld(worldName).get();
        World defaultWorld = Sponge.getGame().getServer().getWorld(Sponge.getGame().getServer().getDefaultWorld().get().getWorldName()).get();


        for (Entity entity : world.getEntities()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                player.setLocationSafely(defaultWorld.getSpawnLocation());
                player.sendMessage(Text.of("[SPW]", TextColors.YELLOW, worldName, " is being unloaded"));
            }
        }

        Sponge.getGame().getServer().unloadWorld(world);
        PlayerWorldsPlugin.getPlugin().logger.info("[SPW] World unloaded : " + world);
    }

    public static void loadPlayerWorld(String worldName)
    {
        World world = Sponge.getGame().getServer().getWorld(worldName).get();

        Optional<World> opWorld = Sponge.getServer().getWorld(worldName);

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                if (!opWorld.isPresent()) {
                    player.sendMessage(Text.of("[SPW] World Not Existing : " + worldName));
                }

                Sponge.getGame().getServer().loadWorld(worldName);
                PlayerWorldsPlugin.getPlugin().logger.info("[SPW] World Loaded : " + worldName);
            }
        }
    }
}
