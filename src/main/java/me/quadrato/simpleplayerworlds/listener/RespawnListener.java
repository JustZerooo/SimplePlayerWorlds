package me.quadrato.simpleplayerworlds.listener;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class RespawnListener {

    //@Listener
    public void onSomeEvent(String worldName, RespawnPlayerEvent event) {
        Location<World> spawn = Sponge.getServer().getWorld(worldName).get().getSpawnLocation();
        Location<World> safeLoc = Sponge.getTeleportHelper().getSafeLocation(spawn).orElse(spawn);

        Transform<World> to = new Transform<>(safeLoc);
        event.setToTransform(to);
    }
}
