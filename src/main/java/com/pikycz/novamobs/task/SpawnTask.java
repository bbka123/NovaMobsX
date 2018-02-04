package com.pikycz.novamobs.task;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import com.pikycz.novamobs.NovaMobsX;
import com.pikycz.novamobs.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PikyCZ
 */
public class SpawnTask implements Runnable {

    NovaMobsX plugin;

    public SpawnTask(NovaMobsX plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        List<Player> onlinePlayers = new ArrayList<>();
        for (Player player : onlinePlayers) {
            Position pos = player.getPosition();

            // x - longitude, z - latitude, y - high/low (64 is sea level)
            Position spawnPosition = new Position(pos.x, pos.y, pos.z);
            getSpawnPosition(spawnPosition, new int[0], 2, 5, player.getLevel());
        }
    }

    private Position getSpawnPosition(Position startSpawnPosition, int[] notAllowedBlockIds, int minAirAboveSpawnBlock, int maxFindingTries, Level level) {
        int spawnX = (int) startSpawnPosition.x; // east/west (increase = west, decrease = east)
        int spawnZ = (int) startSpawnPosition.z; // north/south (increase = south, decrease = north)
        Position spawnPosition = null;

        int minSpawnX1 = spawnX - plugin.getConfig().getInt("MINSPAWN_RADIUS");
        int minSpawnX2 = spawnX + plugin.getConfig().getInt("MINSPAWN_RADIUS");
        int maxSpawnX1 = spawnX - plugin.getConfig().getInt("MAXSPAWN_RADIUS");
        int maxSpawnX2 = spawnX + plugin.getConfig().getInt("MAXSPAWN_RADIUS");

        int minSpawnZ1 = spawnZ - plugin.getConfig().getInt("MINSPAWN_RADIUS");
        int minSpawnZ2 = spawnZ + plugin.getConfig().getInt("MINSPAWN_RADIUS");
        int maxSpawnZ1 = spawnZ - plugin.getConfig().getInt("MAXSPAWN_RADIUS");
        int maxSpawnZ2 = spawnZ + plugin.getConfig().getInt("MAXSPAWN_RADIUS");

        // now we've our x/z boundaries ... let's start to check the blocks ...
        boolean found = false;
        int findTries = 0;
        // find a randomly choosing starting point ...
        boolean startEast = Utils.rand();
        boolean startNorth = Utils.rand();

        int x = startEast ? Utils.rand(minSpawnX1, maxSpawnX1) : Utils.rand(minSpawnX2, maxSpawnX2);
        int z = startNorth ? Utils.rand(minSpawnZ1, maxSpawnZ1) : Utils.rand(minSpawnZ2, maxSpawnZ2);
        int y = spawnZ;

        while (!found && findTries < maxFindingTries) {
            int blockId = level.getBlockIdAt(x, y, z);
            if (isBlockAllowed(blockId, notAllowedBlockIds) && isEnoughAirAboveBlock(x, y, z, minAirAboveSpawnBlock, level)) {
                found = true;
            }
            if (!found) {

            }
            findTries++;
        }

        if (found) {
            spawnPosition = new Position(x, y, z);
        }

        return spawnPosition;
    }

    private boolean isBlockAllowed(int blockId, int[] notAllowedBlockIds) {
        if (notAllowedBlockIds.length > 0) {
            for (int notAllowed : notAllowedBlockIds) {
                if (notAllowed == blockId) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isEnoughAirAboveBlock(int x, int y, int z, int minAirAbove, Level level) {
        if (minAirAbove > 0) {
            int maxTestY = y + minAirAbove;
            int addY = 1;
            while ((y + addY) <= maxTestY) {
                int blockId = level.getBlockIdAt(x, y + addY, z);
                if (blockId != Block.AIR) {
                    return false;
                }
                addY++;
            }
        }
        return true;
    }

}
