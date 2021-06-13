package towers;
import data.*;
import projectiles.ProjectileMachineGun;

import java.util.concurrent.CopyOnWriteArrayList;
import static controllers.Time.GetDeltaFrameTime;

/**
 * Klasa TwinMachineGun zawiera m.in. w konstruktorze typ wieży i trzy nadpisane metody.
 */
public class TwinMachineGun extends Tower {

    private int roundsInTheMagazine;

    public TwinMachineGun(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
        this.roundsInTheMagazine = 100;
    }

    /**
     * Nadpisana metoda shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje pociski z końców luf bliźniaczych karabinów maszynowych.
     */
    @Override
    public void shoot(Enemy target) {
        this.calculateProjectilePositionModifier(-13, 25);
        super.projectiles.add(new ProjectileMachineGun(super.type.projectileType, super.target,
                super.getX() + 26 + this.xProjectileModifier , // + 26 żeby środek tekstury pocisku o wymiarach 12px x 12px był na środku kafelka o wymiarach 64px x 64px
                super.getY() + 26 + this.yProjectileModifier , 12, 12)); // tekstura pomniejszona z 16x16 px na 12x12 px
        this.calculateProjectilePositionModifier(13, 25);
        super.projectiles.add(new ProjectileMachineGun(super.type.projectileType, super.target,
                super.getX() + 26 + this.xProjectileModifier , // + 26 żeby środek tekstury pocisku o wymiarach 12px x 12px był na środku kafelka o wymiarach 64px x 64px
                super.getY() + 26 + this.yProjectileModifier , 12, 12)); // tekstura pomniejszona z 16x16 px na 12x12 px
    }

    /**
     * Metoda odpowiadająca za celowanie przed przeciwnika z wyprzedzeniem, dzięki któremu wieża zyskuje 100%
     * celności w przypadku kiedy przeciwnik porusza się ruchem jednostajnym prostoliniowym.
     * Wywoływana metoda przyjmuje typ pocisku jako parametr żeby poznać jego prędkość. Zwraca kąt o jaki wieża musi się obrócić.
     */
    @Override
    protected float calculateAngle(){
        return super.calculateAngleWithPrediction(ProjectileType.ProjectileMachineGun);
    }

    /**
     * Metoda odpowiada za cykliczną obsługę działań wieży.
     * Jeśli wieża nie jest wycelowana lub cel ma 0 lub mniej ukrytego zdrowia lub dystans do celu jest większy niż zasięg wieży:
     *      - znajdz nowy cel dla wieży.
     * W przeciwnym przypadku:
     *      - sprawdź czy wieża nie powinna pozostać w bezruchu z uwagi na niedawny wystrzał.
     *      Jeśli czas od ostatniego wystrzału jest większy lub równy czasowi co jaki wieża powinna strzelać i magazynek nie jest pusty:
     *          - wystrzel w kierunku przeciwnika, który jest celem.
     *          - ustaw czas od ostatniego wystrzału na 0.
     *          - zmniejsz ilość amunicji w magazynku o jeden.
     * Jeśli referencja celu zawiera null lub cel jest martwy:
     *      - przypisz false do zmiennej targeted reprezentującej wycelowanie (lub nie) wieży.
     * Do czasu od ostatniego wystrzału dodaj czas jaki upłynął od ostatnio wyświetlonej klatki obrazu.
     * Jeśli czas od ostatniego wystrzału jest większy niż 4 sekundy uzupełnij zawartość magazynka.
     * Rysuj podstawę wieży pociski i wieżę.
     */
    @Override
    public void update() {
        if (!targeted || target.getHiddenHealth() <= 0 || calculateDistance(target) > range) {
            target = acquireTarget();
        } else {
            stopTowerRotateAfterShotFor(stopRotateOfTowerTime);
            if (timeSinceLastShot >= firingSpeed && roundsInTheMagazine > 0) {
                shoot(target);
                timeSinceLastShot = 0;
                roundsInTheMagazine--;
            }
        }

        if (target == null || !target.isAlive())
            targeted = false;

        timeSinceLastShot += GetDeltaFrameTime();

        if (timeSinceLastShot > 4)
            roundsInTheMagazine = 100;

        draw();
    }
}