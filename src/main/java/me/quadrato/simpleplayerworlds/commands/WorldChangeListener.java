package me.quadrato.simpleplayerworlds.commands;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.world.World;

public class WorldChangeListener {

    @Listener
    public void onSomeEvent(MoveEntityEvent.Teleport event) {
        Player p = (Player) event.getSource();
        World w = event.getFromTransform().getExtent();
        w.getProperties().setGameRule("mobGriefing", "false");
        World t = event.getToTransform().getExtent();
        t.getProperties().setGameRule("mobGriefing", "false");
    }
}
