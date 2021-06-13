package projectiles;

import data.Enemy;
import data.Projectile;
import data.ProjectileType;
<<<<<<< HEAD

/**
 * Klasa ProjectileLightWarhead zawiera m.in. w konstruktorze typ pocisu oraz cel.
 */
=======
import org.newdawn.slick.opengl.Texture;

>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class ProjectileLightWarhead extends Projectile {

    public ProjectileLightWarhead(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        super(type, target, x, y, width, height);
    }
}
