package data;

import org.junit.jupiter.api.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import projectiles.ProjectileDisruptor;

import static controllers.Map.LoadMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectileTest {

    @Test
    void calculateDistance() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(1280, 960));
        Display.create();
        TileGrid map = LoadMap("map_1");
        Enemy enemy = new Enemy(1, 1, map);
        Projectile projectile = new ProjectileDisruptor(ProjectileType.ProjectileDisruptor, enemy, 480, 480, 20, 20);
        float d = projectile.calculateDistance(enemy);
        assertEquals (d, 536.0335693359375);
        assertTrue(d >= 0);
    }

    @Test
    void calculatexDistance() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(1280, 960));
        Display.create();
        TileGrid map = LoadMap("map_1");
        Enemy enemy = new Enemy(1, 1, map);
        Projectile projectile = new ProjectileDisruptor(ProjectileType.ProjectileDisruptor, enemy, 480, 480, 20, 20);
        float d = projectile.calculatexDistance(enemy);
        assertEquals (d, 374);
        assertTrue(d >= 0);
    }

    @Test
    void calculateyDistance() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(1280, 960));
        Display.create();
        TileGrid map = LoadMap("map_1");
        Enemy enemy = new Enemy(1, 1, map);
        Projectile projectile = new ProjectileDisruptor(ProjectileType.ProjectileDisruptor, enemy, 480, 480, 20, 20);
        float d = projectile.calculatexDistance(enemy);
        assertEquals (d, 374);
        assertTrue(d >= 0);
    }
}