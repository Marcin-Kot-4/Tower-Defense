package projectiles;

import data.Enemy;
import data.Projectile;
import data.ProjectileType;

/**
 * Klasa ProjectileHellBore zawiera m.in. w konstruktorze typ pocisu oraz cel i dwie nadpisane metody.
 */
public class ProjectileHellBore extends Projectile {

    public ProjectileHellBore(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        super(type, target, x, y, width, height);
    }

    /**
     * Celowanie z przewidywaniem położenia przeciwnika w momencie kiedy pocisk pokona dystans, który dzieli wieżę od celu.
     */
    @Override
    public void calculateDirection() {
        super.calculateDirectionWithPrediction();
    }

    /**
     * Nadpisana metoda damage z abstrakcyjnej klasy Projectile.
     * Wywołaj metodę zadającą obrażenia trafionemu wrogowi i niszczącą pocisk.
     * Spowolnij trafionego wroga o 35%.
     */
    public void damage() {
        super.damage();
        this.getHittedEnemy().setSpeed(this.getHittedEnemy().getSpeed() * 0.65f);
    }
}
