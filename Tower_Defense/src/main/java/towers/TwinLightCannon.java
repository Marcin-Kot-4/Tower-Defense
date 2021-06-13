package towers;
import data.Enemy;
import data.Tile;
import data.Tower;
import data.TowerType;
import projectiles.ProjectileLightCannon;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Klasa TwinLightCannon zawiera m.in. w konstruktorze typ wieży i jedną nadpisaną metodę.
 */
public class TwinLightCannon extends Tower {

    public TwinLightCannon(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    /**
     * Nadpisana metoda shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje pociski z końców luf bliźniaczych lekkich dział.
     */
    @Override
    public void shoot(Enemy target) {
        this.calculateProjectilePositionModifier(-7, 20);
        super.projectiles.add(new ProjectileLightCannon(super.type.projectileType, super.target,
                super.getX() + 25 + this.xProjectileModifier , // + 25 żeby środek tekstury pocisku o wymiarach 14px x 14px był na środku kafelka o wymiarach 64px x 64px
                super.getY() + 25 + this.yProjectileModifier , 14, 14)); // szerokość i wysokość ustawiona na 14 żeby powiększyć pocisk
        this.calculateProjectilePositionModifier(9, 20);
        super.projectiles.add(new ProjectileLightCannon(super.type.projectileType, super.target,
                super.getX() + 25 + this.xProjectileModifier , // + 25 żeby środek tekstury pocisku o wymiarach 14px x 14px był na środku kafelka o wymiarach 64px x 64px
                super.getY() + 25 + this.yProjectileModifier , 14, 14)); // szerokość i wysokość ustawiona na 14 żeby powiększyć pocisk
    }
}