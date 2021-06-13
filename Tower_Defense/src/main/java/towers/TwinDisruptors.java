package towers;
import data.Enemy;
import data.Tile;
import data.Tower;
import data.TowerType;
import projectiles.ProjectileDisruptor;

import java.util.concurrent.CopyOnWriteArrayList;

<<<<<<< HEAD
/**
 * Klasa TwinDisruptors zawiera m.in. w konstruktorze typ wieży i jedną nadpisaną metodę.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class TwinDisruptors extends Tower {

    public TwinDisruptors(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    /**
     * Nadpisana metoda shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje pociski z końca luf bliźniaczych rozpraszaczy.
     */
    @Override
    public void shoot(Enemy target) {
        this.calculateProjectilePositionModifier(-8, 24);
        super.projectiles.add(new ProjectileDisruptor(super.type.projectileType, super.target,
                super.getX() + 22 + this.xProjectileModifier , // + 22 żeby środek tekstury pocisku o wymiarach 20px x 20px był na środku kafelka o wymiarach 64px x 64px
                super.getY() + 22 + this.yProjectileModifier , 20, 20)); // szerokość i wysokość 20px żeby powiększyć pocisk (wymiary tekstury: 16x16 px)
        this.calculateProjectilePositionModifier(10, 24);
        super.projectiles.add(new ProjectileDisruptor(super.type.projectileType, super.target,
                super.getX() + 22 + this.xProjectileModifier , // + 22 żeby środek tekstury pocisku o wymiarach 20px x 20px był na środku kafelka o wymiarach 64px x 64px
                super.getY() + 22 + this.yProjectileModifier , 20, 20)); // szerokość i wysokość 20px żeby powiększyć pocisk (wymiary tekstury: 16x16 px)
    }
}