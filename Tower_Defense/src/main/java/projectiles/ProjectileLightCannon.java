package projectiles;

import data.Enemy;
import data.Projectile;
import data.ProjectileType;

/**
 * Klasa ProjectileLightCannon zawiera m.in. w konstruktorze typ pocisu oraz cel.
 */
public class ProjectileLightCannon extends Projectile {

    public ProjectileLightCannon(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        super(type, target, x, y, width, height);
    }
}
