package towers;
import data.Enemy;
import data.Tile;
import data.Tower;
import data.TowerType;
import projectiles.ProjectileLightCannon;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Klasa LightCannon zawiera m.in. w konstruktorze typ wieży i jedną nadpisaną metodę.
 */
public class LightCannon extends Tower {

    public LightCannon(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    /**
     * Nadpisana metoda shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje pocisk z końca lufy lekkiego działa.
     */
    @Override
    public void shoot(Enemy target) {
        this.calculateProjectilePositionModifier(1, 10);
        super.projectiles.add(new ProjectileLightCannon(super.type.projectileType, super.target,
                super.getX() + 26 + this.xProjectileModifier , // + 26 żeby środek tekstury pocisku o wymiarach 12px x 12px był na środku kafelka o wymiarach 64px x 64px
                super.getY() + 26 + this.yProjectileModifier , 12, 12)); // szerokość i wysokość ustawiona na 12 żeby powiększyć pocisk (tekstura ma 8x8 px)
    }
}