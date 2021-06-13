package towers;

import data.*;
import projectiles.ProjectileRocket;

import java.util.concurrent.CopyOnWriteArrayList;

import static controllers.Graphic.*;

/**
 * Klasa RocketLauncher zawiera m.in. w konstruktorze typ wieży i jedną nadpisaną metodę.
 */
public class RocketLauncher extends Tower {

    public RocketLauncher(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    /**
     * Implementacja abstrakcyjnej metody shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje dwie rakiety z dwóch różnych prowadnic na raz.
     */
    public void shoot(Enemy target) {
        this.calculateProjectilePositionModifier(18, 0);
        super.projectiles.add(new ProjectileRocket(super.type.projectileType, super.target,
                super.getX() + this.xProjectileModifier ,
                super.getY() + this.yProjectileModifier , 64, 64));
        this.calculateProjectilePositionModifier(-18, 0);
        super.projectiles.add(new ProjectileRocket(super.type.projectileType, super.target,
                super.getX() + this.xProjectileModifier ,
                super.getY() + this.yProjectileModifier , 64, 64));
        super.target.reduceHiddenHealth(super.type.projectileType.getDamage() * 2); // damage * 2 ponieważ wystrzeliwane są dwie rakiety
    }

    /**
     * Nadpisana metoda draw abstrakcyjnej klasy Tower.
     * Rysuje podstawę wieży a następnie jeśli czas do kolejnego wystrzału jest krótszy niż jedna sekunda to rysuje
     * teksturę wyrzutni rakiet z załadowanymi rakietami. W przeciwnym przypadku rysuje teksturę pustej wyrzutni rakiet.
     * Na koniec aktualizuje wystrzelone, niezniszczone rakiety.
     */
    @Override
    public void draw() {
        DrawTexture(this.textures[0], this.x, this.y, this.width, this.height);
        if (this.timeSinceLastShot > (this.firingSpeed - 1))
            DrawRotatedTexture(this.textures[1], this.x, this.y, this.width, this.height, this.angle);
        else
            DrawRotatedTexture(this.textures[2], this.x, this.y, this.width, this.height, this.angle);

        for (Projectile p : projectiles) // rysowanie pocisków
            p.update();
    }
}