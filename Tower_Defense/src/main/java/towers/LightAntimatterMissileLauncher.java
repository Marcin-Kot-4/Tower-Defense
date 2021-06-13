package towers;

import data.*;
import projectiles.ProjectileLightAntimatterMissile;

import java.util.concurrent.CopyOnWriteArrayList;

import static controllers.Graphic.*;
import static controllers.Time.GetDeltaFrameTime;

<<<<<<< HEAD
/**
 * Klasa LightAntimatterMissileLauncher zawiera m.in. metodę która implementuje
 * abstrakcyjną metodę shootabstrakcyjnej klasy Tower oraz dwie nadpisane metody.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class LightAntimatterMissileLauncher extends Tower {
    private int missileFiredInThisSeries;
    private int numberOfLaunchedSeries;
    private float timeSinceLastLaunch;

    public LightAntimatterMissileLauncher(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(type, startTile, enemies);
    }

    /**
     * Implementacja abstrakcyjnej metody shoot abstrakcyjnej klasy Tower.
     * Wystrzeliwuje rakiety z trzech różnych prowadnic w zależności, od tego która z kolei jest to rakieta.
     * Dzięki temu uzyskuje się efekt wystrzeliwania rakiet z prowadnic kolejno 0, 1, 2, 0, 1, 2, 0, 1, 2... itd.
     * Po wystrzale rakiety z odpowiedniej prowadnicy jest redukowany poziom zdrowia przeciwnika, który jest celem.
     */
    public void shoot(Enemy target) {
        if (missileFiredInThisSeries == 0){
            this.calculateProjectilePositionModifier(9, 0);
            super.projectiles.add(new ProjectileLightAntimatterMissile(super.type.projectileType, super.target,
                    super.getX() + this.xProjectileModifier + 16, // + 16 w x i y żeby bazowo środek pocisku był w środku kafelka
                    super.getY() + this.yProjectileModifier + 16, 32, 32));
        }
        if (missileFiredInThisSeries == 1){
            this.calculateProjectilePositionModifier(0, 0);
            super.projectiles.add(new ProjectileLightAntimatterMissile(super.type.projectileType, super.target,
                    super.getX() + this.xProjectileModifier + 16,
                    super.getY() + this.yProjectileModifier + 16, 32, 32));
        }
        if (missileFiredInThisSeries == 2){
            this.calculateProjectilePositionModifier(-9, 0);
            super.projectiles.add(new ProjectileLightAntimatterMissile(super.type.projectileType, super.target,
                    super.getX() + this.xProjectileModifier + 16,
                    super.getY() + this.yProjectileModifier + 16, 32, 32));
        }

        super.target.reduceHiddenHealth(super.type.projectileType.getDamage());
    }

    /**
     * Nadpisana metoda draw abstrakcyjnej klasy Tower.
     * Rysuje podstawę wieży, nad nią rakiety i nad rakietami wyrzutnię.
     */
    @Override
    public void draw() {
        DrawTexture(this.textures[0], this.x, this.y, this.width, this.height);

        for (Projectile p : projectiles)
            p.update();

        DrawRotatedTexture(this.textures[1], this.x, this.y, this.width, this.height, this.angle);
    }

    /**
     * Nadpisana metoda update() abstrakcyjnej klasy Tower.
     * Jeśli wieża nie jest wycelowana lub cel ma 0 lub mniej ukrytego zdrowia lub dystans do celu jest większy niż zasięg wieży:
     *      - znajdz nowy cel dla wieży.
     * W przeciwnym przypadku:
     *      - sprawdź czy wieża nie powinna pozostać w bezruchu z uwagi na niedawny wystrzał. [nie dotyczy tej wieży | czas stopu ustawiony na 0]
     *      Jeśli czas od ostatniego wystrzału jest większy lub równy czasowi co jaki wieża powinna strzelać lub jeśli
     *      wieża jest w trakcie wystrzeliwania serii i jednocześnie czas od odpalenia ostatniej rakiety jest większy niż 0.038 sekundy:
     *          - ustaw czas od ostatnio wystrzelonej serii na 0.
     *          - wystrzel w kierunku przeciwnika, który jest celem.
     *          - zinkrementuj o jeden zmienną determinującą prowadnicę z której należy odpalić następną rakietę.
     *          Jeśli wystrzelono rakiety z prowadnic 0, 1, i 2:
     *              - ustaw prowadnicę 0 jako następną, z której będzie odpalona rakieta.
     *              - zwiększ numer serii. [Cztery serie przed ponownym załadowaniem prowadnic. Lącznie 9 prowadnic i rakiet]
     *              Jeśli została wystrzelona ostatnia seria:
     *                  - ustaw numer serii na 0 co spowoduje że wieża będzie czekać na załadowanie prowadnic ("przełądowanie").
     *              Jeśli aktualny numer serii oraz numer rakiety to 0:
     *                  - ustaw czas od ostatniego wystrzału na 0;
     * Jeśli referencja celu zawiera null lub cel jest martwy:
     *      - przypisz false do zmiennej targeted reprezentującej wycelowanie (lub nie) wieży.
     * Utwórz zmienna temp i przypisz do niej czas od ostatnio odpalonej pojedynczej rakiety.
     * Do czasu od ostatnio odpalonej pojedynczej rakiety dodaj czas jaki upłynął od ostatnio wyświetlonej klatki obrazu.
     * Do zmiennej przechowującej czas od ostatnio wystrzelonych wszystki rakiet z 9 prowadnic dodaj czas jaki upłunął
     * od ostatnio wyświetlonej klatki obrazu wykorzystując do tego zmienną temp oraz timeSinceLastLaunch.
     * Rysuj wieżę i pociski w odpowiedniej kolejności używając metody draw().
     */
    @Override
    public void update() {
        if (!targeted || target.getHiddenHealth() <= 0 || calculateDistance(target) > range) {
            target = acquireTarget();
        } else {
            stopTowerRotateAfterShotFor(stopRotateOfTowerTime);
            if ((timeSinceLastShot >= firingSpeed || missileFiredInThisSeries != 0 || numberOfLaunchedSeries != 0) && timeSinceLastLaunch >= 0.038) {
                timeSinceLastLaunch = 0;
                shoot(target);
                missileFiredInThisSeries++;

                if (missileFiredInThisSeries == 3){
                    missileFiredInThisSeries = 0;
                    numberOfLaunchedSeries++;
                    if (numberOfLaunchedSeries == 3){
                        numberOfLaunchedSeries = 0;
                    }
                }

                if (missileFiredInThisSeries == 0 && numberOfLaunchedSeries == 0)
                    timeSinceLastShot = 0;
            }
        }

        if (target == null || !target.isAlive())
            targeted = false;

        float temp = timeSinceLastLaunch;
        timeSinceLastLaunch += GetDeltaFrameTime();
        timeSinceLastShot += timeSinceLastLaunch - temp;

        draw();
    }
}