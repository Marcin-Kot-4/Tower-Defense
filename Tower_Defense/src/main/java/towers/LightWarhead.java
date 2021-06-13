package towers;
import data.Enemy;
import data.Tile;
import data.Tower;
import data.TowerType;
import projectiles.ProjectileLightWarhead;

import java.util.concurrent.CopyOnWriteArrayList;

<<<<<<< HEAD
/**
 * Klasa LightWarhead zawiera m.in. w konstruktorze typ wieży i jedną nadpisaną metodę.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class LightWarhead extends Tower {

    public LightWarhead(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    /**
     * Nadpisana metoda shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje pocisk z końca lufy.
     */
    @Override
    public void shoot(Enemy target) {
        this.calculateProjectilePositionModifier(0, 10);
        super.projectiles.add(new ProjectileLightWarhead(super.type.projectileType, super.target,
                super.getX() + 22 + this.xProjectileModifier ,                       // + 22 żeby tekstura pocisku o wymiarach 20px x 20px była na środku kafelka
                super.getY() + 22 + this.yProjectileModifier , 20, 20)); // szerokość i wysokość ustawiona na 20 żeby powiększyć pocisk
    }
}