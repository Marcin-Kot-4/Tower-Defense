package towers;
import data.*;
import projectiles.ProjectileHeavyCannon;

import java.util.concurrent.CopyOnWriteArrayList;

<<<<<<< HEAD
/**
 * Klasa HeavyCannon zawiera m.in. w konstruktorze typ wieży i dwie nadpisane metody.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class HeavyCannon extends Tower {

    public HeavyCannon(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    /**
     * Nadpisana metoda shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje pocisk z końca lufy ciężkiego działa.
     */
    @Override
    public void shoot(Enemy target) {
        this.calculateProjectilePositionModifier(1, 24);
        super.projectiles.add(new ProjectileHeavyCannon(super.type.projectileType, super.target,
                super.getX() + 24 + this.xProjectileModifier , // + 24 żeby środek tekstury pocisku o wymiarach 16px x 16px był na środku kafelka o wymiarach 64px x 64px
                super.getY() + 24 + this.yProjectileModifier , 16, 16));
    }

    /**
     * Metoda odpowiadająca za celowanie przed przeciwnika z wyprzedzeniem, dzięki któremu wieża zyskuje 100%
     * celności w przypadku kiedy przeciwnik porusza się ruchem jednostajnym prostoliniowym.
     * Wywoływana metoda przyjmuje typ pocisku jako parametr żeby poznać jego prędkość. Zwraca kąt o jaki wieża musi się obrócić.
     */
    @Override
    protected float calculateAngle(){
        return super.calculateAngleWithPrediction(ProjectileType.ProjectileHeavyCannon);
    }
}