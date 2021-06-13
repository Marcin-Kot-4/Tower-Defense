package data;

import enemies.*;
import org.junit.jupiter.api.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static controllers.Map.LoadMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {

    @Test
    void modifyCash() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(1280, 960));
        Display.create();
        TileGrid map = LoadMap("map_1");
        Enemy[] enemyTypes = new Enemy[25];
        int enemySpawnTileX = 3, enemySpawnTileY = 0;
        enemyTypes[0] = new PhidiInterceptor(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[1] = new YuralInterceptor(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[2] = new HumanInterceptor(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[3] = new GremakInterceptor(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[4] = new AshdakInterceptor(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[5] = new OrthinInterceptor(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[6] = new PhidiBomber(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[7] = new YuralBomber(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[8] = new HumanBomber(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[9] = new GremakBomber(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[10] = new AshdakBomber(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[11] = new OrthinBomber(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[12] = new PhidiHeavyFighter(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[13] = new YuralHeavyFighter(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[14] = new HumanHeavyFighter(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[15] = new GremakHeavyFighter(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[16] = new AshdakHeavyFighter(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[17] = new OrthinHeavyFighter(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[18] = new PhidiAssaultShuttle(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[19] = new YuralAssaultShuttle(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[20] = new HumanAssaultShuttle(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[21] = new GremakAssaultShuttle(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[22] = new AshdakAssaultShuttle(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[23] = new OrthinAssaultShuttle(enemySpawnTileX, enemySpawnTileY, map);
        enemyTypes[24] = new AshdakSpecialAce(enemySpawnTileX, enemySpawnTileY, map);
        WaveManager waveManager = new WaveManager(enemyTypes, 2, 3, 25);
        Player player = new Player(map, waveManager, 150, 10);
        player.modifyCash(100);
        player.modifyCash(100);
        assertEquals (player.getCash(), 50);
        assertTrue(Player.cash >= 0);
    }

    @Test
    void grantCash() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(1280, 960));
        Display.create();
        Player.grantCash(190);
        assertEquals (Player.cash, 190);
        Player.grantCash(-190);
        assertEquals (Player.cash, 0);
        Player.grantCash(-1);
        assertTrue(Player.cash >= 0);
    }

    @Test
    void modifyLives() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(1280, 960));
        Display.create();
        Player.modifyLives(10);
        assertEquals (Player.lives, 10);
        Player.modifyLives(-10);
        assertEquals (Player.lives, 0);
        Player.modifyLives(-1);
        assertTrue(Player.lives >= 0);
    }
}