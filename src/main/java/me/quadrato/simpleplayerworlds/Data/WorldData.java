package me.quadrato.simpleplayerworlds.Data;

import net.obnoxint.xnbt.XNBT;
import net.obnoxint.xnbt.types.CompoundTag;
import net.obnoxint.xnbt.types.NBTTag;
import net.obnoxint.xnbt.types.StringTag;
import org.spongepowered.api.Sponge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class WorldData {
    private String worldName;
    private File dataFile;
    private CompoundTag compoundTag;
    private boolean exists = false;

    public WorldData(String worldName) {
        this.worldName = worldName;

        String defaultWorld = Sponge.getGame().getServer().getDefaultWorldName();

        if (defaultWorld.equalsIgnoreCase(worldName)) {
            dataFile = new File(defaultWorld, "level.dat");
        } else {
            dataFile = new File(defaultWorld + File.separator + worldName, "level.dat");
        }

        if (dataFile.exists()) {
            exists = true;
            init();
        }
    }

    public WorldData(File directory) {
        this.dataFile = new File(directory, "level.dat");
        this.worldName = directory.getName();

        if (dataFile.exists()) {
            exists = true;
            init();
        }
    }

    public boolean exists() {
        return exists;
    }

    private void init() {
        try {
            for (NBTTag root : XNBT.loadTags(dataFile)) {
                CompoundTag compoundRoot = (CompoundTag) root;

                for (Entry<String, NBTTag> rootItem : compoundRoot.entrySet()) {
                    if (rootItem.getKey().equalsIgnoreCase("Data")) {
                        compoundTag = (CompoundTag) rootItem.getValue();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isCorrectLevelName() {
        for (Entry<String, NBTTag> entry : compoundTag.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase("LevelName")) {
                continue;
            }

            String levelName = (String) entry.getValue().getPayload();

            if (levelName.equalsIgnoreCase(worldName)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void setLevelName(String name) throws IOException {
        compoundTag.put(new StringTag("LevelName", name));

        CompoundTag compoundRoot = new CompoundTag("", null);

        compoundRoot.put(compoundTag);

        List<NBTTag> list = new ArrayList<>();

        list.add(compoundRoot);

        XNBT.saveTags(list, dataFile);
    }
}
