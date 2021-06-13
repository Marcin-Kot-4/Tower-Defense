package towers;
import data.*;
import projectiles.ProjectileTwinCannon;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Klasa TwinCannon zawiera m.in. w konstruktorze typ wieży i dwie nadpisane metody.
 */
public class TwinCannon extends Tower {

    public TwinCannon(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    /**
     * Nadpisana metoda shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje pociski z końców luf bliźniaczych dział.
     */
    @Override
    public void shoot(Enemy target) {
        this.calculateProjectilePositionModifier(-8, 24);
        super.projectiles.add(new ProjectileTwinCannon(super.type.projectileType, super.target,
                super.getX() + 24 + this.xProjectileModifier , // + 24 żeby środek tekstury pocisku o wymiarach 16px x 16px był na środku kafelka o wymiarach 64px x 64px
                super.getY() + 24 + this.yProjectileModifier , 16, 16));
        this.calculateProjectilePositionModifier(8, 24);
        super.projectiles.add(new ProjectileTwinCannon(super.type.projectileType, super.target,
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
        return super.calculateAngleWithPrediction(ProjectileType.ProjectileTwinCannon);
    }
}