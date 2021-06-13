package projectiles;

import data.Enemy;
import data.Projectile;
import data.ProjectileType;

/**
 * Klasa ProjectileDisruptor zawiera m.in. w konstruktorze typ pocisu oraz cel i jedną metodę.
 */
public class ProjectileDisruptor extends Projectile {

    public ProjectileDisruptor(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        super(type, target, x, y, width, height);
    }

    /**
     * Nadpisana metoda damage z abstrakcyjnej klasy Projectile.
     * Wywołaj metodę zadającą obrażenia trafionemu wrogowi i niszczącą pocisk.
     * Spowolnij trafionego wroga o 15%.
     */
    public void damage() {
        super.damage();
        this.getHittedEnemy().setSpeed(this.getHittedEnemy().getSpeed() * 0.85f);
    }
}
