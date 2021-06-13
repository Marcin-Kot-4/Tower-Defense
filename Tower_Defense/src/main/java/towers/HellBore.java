package towers;
import data.*;
import projectiles.ProjectileHellBore;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Klasa HellBore zawiera m.in. w konstruktorze typ wieży i dwie nadpisane metody.
 */
public class HellBore extends Tower {

    public HellBore(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    /**
     * Nadpisana metoda shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje pocisk z końca lufy.
     * Jeśli prędkość poruszania się celu jest mniejsza niż 10:
     *      - wyceluj w przeciwnika, który znajduje się najbliżej wieży.
     */
    @Override
    public void shoot(Enemy target) {
        this.calculateProjectilePositionModifier(0, 0);
        super.projectiles.add(new ProjectileHellBore(super.type.projectileType, super.target,
                super.getX() + this.xProjectileModifier ,
                super.getY() + this.yProjectileModifier , 64, 64));
        if (target.getSpeed() < 10)
            this.target = super.acquireTarget();
    }

    /**
     * Metoda odpowiadająca za celowanie przed przeciwnika z wyprzedzeniem, dzięki któremu wieża zyskuje 100%
     * celności w przypadku kiedy przeciwnik porusza się ruchem jednostajnym prostoliniowym.
     * Wywoływana metoda przyjmuje typ pocisku jako parametr żeby poznać jego prędkość. Zwraca kąt o jaki wieża musi się obrócić.
     */
    @Override
    protected float calculateAngle(){
        return super.calculateAngleWithPrediction(ProjectileType.ProjectileHellBore);
    }
}