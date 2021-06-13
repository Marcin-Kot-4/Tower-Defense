package projectiles;

import data.Enemy;
import data.Projectile;
import data.ProjectileType;

/**
 * Klasa ProjectileRocket zawiera m.in. w konstruktorze typ pocisu oraz cel i trzy nadpisane metody.
 */
public class ProjectileRocket extends Projectile {

    private long projectileLastAccelerationTime = System.currentTimeMillis();
    private long projectileFlightTime = System.currentTimeMillis();

    public ProjectileRocket(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        super(type, target, x, y, width, height);
    }

    /**
     * Nadpisana metoda damage z abstrakcyjnej klasy Projectile.
     * Jeśli trafiony przeciwnik nie jest pierwotnym celem (czyli pocisk trafił w przeciwnika, który zasłonił cel):
     *      - przywróć zdrowie przeciwnika
     * Wywołaj metodę zadającą obrażenia trafionemu wrogowi i niszczącą pocisk.
     * Spowolnij trafionego wroga o 25%.
     */
    @Override
    public void damage() {
        if (this.getTarget() != this.getHittedEnemy())
            this.getTarget().restoreHiddenHealth(damage);
        super.damage();
    }

    /**
     * Zwykła metoda draw nadpisana metodą drawGuidedMissile(), która jest zdefiniowana w abstrakcyjnej klasie Projectile i
     * obsługuje rysowanie pocisków kierowanych. Metoda draw jest wykorzystywana w metodzie update() więc takie nadpisanie
     * powoduje zmianę działania oraz rezultatu metody update(), która również jest zdefiniowana w abstrakcyjnej klasie Projectile.
     * Nadpisanie to jest wykonywane po to aby pocisk był obracany w stronę celu w takcie lotu.
     */
    @Override
    public void draw(){
        super.drawGuidedMissile();
    }

    /**
     * Nadpisana metoda update() abstrakcyjnej klasy Projectile.
     * Wykonaj to co zostało zdefiniowane w metodzie update() klasy Projectile.
     * Wykonaj to co zostało zdefiniowane w metodzie calculateDirection() klasy Projectile.
     * Przypisz aktualny czas lotu w milisekundch do atrybutu o nazwie projectileFilghtTime, który należy do tego obiektu.
     * Jeśli cel posiada 0 lub mniej zdrowia lub jest martwy:
     *      - znajdź nowy cel dla tego pocisku.
     *      Jeśli po wyszukiwaniu aktualny cel posiada 0 lub mniej zdrowia lub nie jest żywy:
     *          - zniszcz pocisk.
     * Jeśli pocisk ma prędkość mniejszą niż 700 i czas lotu pocisku jest większy o co najmniej 50 w porównaniu z momentem
     * ostatniego zwiększenia prędkości pocisku (celem drugiego warunku jest przyspieszanie pocisku co 50 ms):
     *      - zaktualizuj czas ostatniego zwiększenia prędkości pocisku.
     *      - przyspiesz pocisk o 10%.
     */
    @Override
    public void update() {
        super.update();
        super.calculateDirection();
        this.projectileFlightTime = System.currentTimeMillis();
        if (this.getTarget().getHealth() <= 0 || !this.getTarget().isAlive()){
            this.getNewTarget();
            if(this.getTarget().getHealth() <= 0 || !this.getTarget().isAlive())
                this.setAlive(false);
        }
        if (this.getSpeed() < 700 && this.projectileFlightTime > (this.projectileLastAccelerationTime + 50)){
            this.projectileLastAccelerationTime = this.projectileFlightTime;
            this.setSpeed(this.getSpeed() * (float) 1.1);
        }
    }
}
