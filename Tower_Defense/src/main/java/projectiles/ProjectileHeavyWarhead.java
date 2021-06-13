package projectiles;

import data.Enemy;
import data.Projectile;
import data.ProjectileType;

/**
 * Klasa ProjectileHeavyWarhead zawiera m.in. w konstruktorze typ pocisu oraz cel i jedną nadpisaną metodę.
 */
public class ProjectileHeavyWarhead extends Projectile {

    public ProjectileHeavyWarhead(ProjectileType type, Enemy target, float x, float y, int width, int height) {
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
