package towers;
import data.*;
import projectiles.ProjectilePulson;

import java.util.concurrent.CopyOnWriteArrayList;

<<<<<<< HEAD
/**
 * Klasa Pulson zawiera m.in. w konstruktorze typ wieży i dwie nadpisane metody.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class Pulson extends Tower {

    public Pulson(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    /**
     * Nadpisana metoda shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje pocisk z końca lufy.
     * Po każdym oddanym strzale wyceluj w przeciwnika, który znajduje się najbliżej wieży.
     */
    @Override
    public void shoot(Enemy target) {
        this.calculateProjectilePositionModifier(0, 0);
        super.projectiles.add(new ProjectilePulson(super.type.projectileType, super.target,
                super.getX() + 20 + this.xProjectileModifier ,
                super.getY() + 20 + this.yProjectileModifier , 24, 24));
        this.target = super.acquireTarget();
    }

    /**
     * Metoda odpowiadająca za celowanie przed przeciwnika z wyprzedzeniem, dzięki któremu wieża zyskuje 100%
     * celności w przypadku kiedy przeciwnik porusza się ruchem jednostajnym prostoliniowym.
     * Wywoływana metoda przyjmuje typ pocisku jako parametr żeby poznać jego prędkość. Zwraca kąt o jaki wieża musi się obrócić.
     */
    @Override
    protected float calculateAngle(){
        return super.calculateAngleWithPrediction(ProjectileType.ProjectilePulson);
    }
}