package projectiles;

import data.Enemy;
import data.Projectile;
import data.ProjectileType;

<<<<<<< HEAD
/**
 * Klasa ProjectileMachineGun zawiera m.in. w konstruktorze typ pocisu oraz cel i jedną nadpisaną metodę.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class ProjectileMachineGun extends Projectile {

    public ProjectileMachineGun(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        super(type, target, x, y, width, height);
    }

    /**
     * Celowanie z przewidywaniem położenia przeciwnika w momencie kiedy pocisk pokona dystans, który dzieli wieżę od celu.
     */
    @Override
    public void calculateDirection() {
        super.calculateDirectionWithPrediction();
    }
}