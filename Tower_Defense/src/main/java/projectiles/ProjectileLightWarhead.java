package projectiles;

import data.Enemy;
import data.Projectile;
import data.ProjectileType;

/**
 * Klasa ProjectileLightWarhead zawiera m.in. w konstruktorze typ pocisu oraz cel.
 */
public class ProjectileLightWarhead extends Projectile {

    public ProjectileLightWarhead(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        super(type, target, x, y, width, height);
    }
}
