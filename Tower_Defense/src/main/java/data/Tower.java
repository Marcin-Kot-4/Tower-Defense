package data;

import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static controllers.Graphic.*;
import static controllers.Time.GetDeltaFrameTime;

/**
 * Klasa abstrakcyjna zawiera m.in. metodę która wyszukuje najbliższy cel
 * w zasięgu więży, metodę która sprawdza czy przeciwnik jest
 * w zasięgu wieży oraz metodę która rysuje wieżę nad wystrzeliwanymi pociskami.
 */
public abstract class Tower implements Unit {

    protected float x, y, timeSinceLastShot, firingSpeed, angle, xProjectileModifier, yProjectileModifier, stopRotateOfTowerTime;
    protected double radianAngle;
    protected int width, height, range, cost;
    public Enemy target;
    protected Texture[] textures;
    private CopyOnWriteArrayList<Enemy> enemies;
    protected boolean targeted;
    public ArrayList<Projectile> projectiles;
    public TowerType type;

    public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        this.type = type;
        this.textures = type.textures;
        this.range = type.range;
        this.cost = type.cost;
        this.firingSpeed = type.firingSpeed;
        this.stopRotateOfTowerTime = type.stopRotateOfTowerTime;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = startTile.getWidth();
        this.height = startTile.getHeight();
        this.enemies = enemies;
        this.targeted = false;
        this.timeSinceLastShot = 0f;
        this.projectiles = new ArrayList<Projectile>();
        this.angle = 0f;
    }

    /**
     * Metoda wyszukująca najbliższy cel w zasięgu wieży. Jeśli znajdzie cel zmienia wartość targeted na true.
     * @return - jesli znajdzie cel to zwraca jego obiekt, w przeciwnym przypadku zwraca null.
     */
    public Enemy acquireTarget() {
        Enemy closest = null;
        float closestDistance = range;
        for (Enemy e : enemies) {
            if (isInRange(e) && calculateDistance(e) < closestDistance && e.getHiddenHealth() > 0 && e.getHealth() > 0) {
                closestDistance = calculateDistance(e);
                closest = e;
            }
        }
        if (closest != null)
            targeted = true;
        return closest;
    }

    /**
     * Metoda sprawdza czy przeciwnik jest w zasięgu wieży.
     * @param enemy - przeciwnik do którego dystans będzie sprawdzany.
     * @return - zwraca true jeśli przeciwnik jest w zasięgu, false w przeciwnym przypadku.
     */
    private boolean isInRange(Enemy enemy) {
        if (calculateDistance(enemy) <= range)
            return true;
        return false;
    }

    /**
     * Zwraca odległość od wieży do przeciwnika.
     * @param enemy - obiekt przeciwnika do którego dystans ma zostać obliczony.
     * @return - zwracana wartość jest przekątną prostokąta o bokach xDistance oraz yDistance czyli odległością
     * od wieży do przeciwnika.
     */
    protected float calculateDistance(Enemy enemy) {
        float xDistance = Math.abs(enemy.getX() - x);
        float yDistance = Math.abs(enemy.getY() - y);
        float d = (float) Math.sqrt((Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));
        return d;
    }

    /**
     * Metoda obliczająca kąt o jaki wieża musi się obrócić aby wycelować w przeciwnika.
     * atan2 to statyczna metoda obiektu Math, która oblicza kąt w radianach na podstawie współrzędnych
     * (z obrotem w kierunku przeciwnym do ruchu wskazówek zegara).
     * toDegrees konwertuje kąt w radianach na przybliżony kąt w stopniach.
     *
     * @return
     */
    protected float calculateAngle() {
        radianAngle = Math.atan2(target.getY() - y, target.getX() - x);
        return (float) Math.toDegrees(radianAngle) - 90;
    }

    /**
     * Metoda odpowiadająca za celowanie przed przeciwnika z wyprzedzeniem, dzięki któremu wieża zyskuje 100%
     * celności w przypadku kiedy przeciwnik porusza się ruchem jednostajnym prostoliniowym.
     * Zasada działania metody jest taka sama jak w klasie abstrakcyjnej Projectile. Dodatkowo metoda przyjmuje
     * typ pocisku jako parametr. Zwraca kąt o jaki wieża musi się obrócić.
     */
    protected float calculateAngleWithPrediction(ProjectileType projectileType) {
        float xDistanceFromTarget = 0;
        float yDistanceFromTarget = 0;
        float newDistanceToTarget = 0;
        float distanceToTarget = calculateDistance(target);
        float timeProjectileFlightToTarget =  distanceToTarget / projectileType.speed;
        float predicted_xTargetPosition = xTargetPositionAfter(timeProjectileFlightToTarget);
        float predicted_yTargetPosition = yTargetPositionAfter(timeProjectileFlightToTarget);

        while(distanceToTarget != newDistanceToTarget)
        {
            newDistanceToTarget = calculateDistanceAxis(predicted_xTargetPosition, predicted_yTargetPosition);
            distanceToTarget = newDistanceToTarget;
            timeProjectileFlightToTarget = newDistanceToTarget / projectileType.speed;
            predicted_xTargetPosition = xTargetPositionAfter(timeProjectileFlightToTarget);
            predicted_yTargetPosition = yTargetPositionAfter(timeProjectileFlightToTarget);
        }

        radianAngle = Math.atan2(predicted_yTargetPosition - y, predicted_xTargetPosition - x);
        return (float) Math.toDegrees(radianAngle) - 90;
    }

    /**
     * Metoda obliczająca przekątną prostokąta. Jest używana do obliczania odległości między wieżą a celem.
     * @param xTarget - pozycja celu na osi x
     * @param yTarget - pozycja celu na osi y
     * @return - zwraca odległość od wieży do celu.
     */
    protected float calculateDistanceAxis(float xTarget, float yTarget){
        // - TILE_SIZE ponieważ połowa rozmiaru wieży i połowa rozmiaru przeciwnika to rozmiar kafelka
        return (float) Math.sqrt(Math.pow(x - xTarget, 2) + Math.pow(y - yTarget, 2) + TILE_SIZE);
    }

    /**
     * Oblicza pozycję na osi x w jakiej będzie się znajdował cel po upływie czasu przekazanego jako parametr.
     * @param time - czas po upływie, którego chcemy poznać pozycję przeciwnika
     * @return - pozycja na osi x
     */
    protected float xTargetPositionAfter(float time){
        return target.getX() + target.getSpeed() * time * target.getxEnemyDirection() + TILE_SIZE / 2 - width / 2;
    }

    /**
     * Oblicza pozycję na osi y w jakiej będzie się znajdował cel po upływie czasu przekazanego jako parametr.
     * @param time - czas po upływie, którego chcemy poznać pozycję przeciwnika
     * @return - pozycja na osi y
     */
    protected float yTargetPositionAfter(float time){
        return target.getY() + target.getSpeed() * time * target.getyEnemyDirection() + TILE_SIZE / 2 - width / 2;
    }

    /**
     * Metoda obliczająca o jaką wartość należy zmienić współrzędne względem wieży aby pocisk podczas wystrzału
     * znajdował się w odpowiednim miejscu na wieży w zależności od kąta o jaki jest obrócona wieża.
     * Metoda jest używana między innymi w wieży wyposażonej w rakiety
     * po to aby dwa pociski zostały wystrzelone obok siebie, jednocześnie z miejsca w którym są widoczne na teksturze
     * wieży, co daje efekt odpalenia pocisku z prowadnicy wyrzutni.
     * Do obliczenia tych współrzędnych zostały użyte funkcje sinus, cosinus oraz kąt obrotu wieży w radianach.
     *
     * @param x - od tego parametru zależy o ile pikseli  na osi x będzie oddalony pocisk od wieży
     * @param y - od tego parametru zależy o ile pikseli  na osi y będzie oddalony pocisk od wieży
     */
    protected void calculateProjectilePositionModifier(float x, float y) {
        xProjectileModifier = (float)(y * Math.cos(radianAngle) - x * Math.sin(radianAngle));
        yProjectileModifier = (float)(y * Math.sin(radianAngle) + x * Math.cos(radianAngle));


        //Poniżej przedstawione obliczenia jakie są wykonywane
//        System.out.println(" ProjectileModifier =  y  * cos   -  x  *  sin   =  y*cos  -  x*sin");
//        System.out.println("xProjectileModifier = " + y + " * " + String.format("%.2f", Math.cos(radianAngle)) + " - " + x + " * " + String.format("%.2f", Math.sin(radianAngle)) + "  =  "
//                + String.format("%.2f", y * Math.cos(radianAngle)) + "    -  " + String.format("%.2f", x * Math.sin(radianAngle)) + "  =  " + xProjectileModifier);
//        System.out.println(" ProjectileModifier =  y  * sin   +  x  *  cos   =  y*cos  +  x*sin");
//        System.out.println("xProjectileModifier = " + y + " * " + String.format("%.2f", Math.sin(radianAngle)) + " + " + x + " * " + String.format("%.2f", Math.cos(radianAngle)) + "  =  "
//                + String.format("%.2f", y * Math.sin(radianAngle)) + "    +  " + String.format("%.2f", x * Math.cos(radianAngle)) + "  =  " + yProjectileModifier);

    }

    /**
     * Abstrakcyjna metoda wykorzystywana do strzelania w klasach dziedziczących
     *
     * @param target - cel
     */
    public abstract void shoot(Enemy target);

    /**
     * Metoda aktualizująca listę przeciwników.
     *
     * @param newList - nowa lista przeciwników
     */
    public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
        enemies = newList;
    }

    /**
     * Metoda obraca wieżę tylko jeśli minął zadany czas od osatniego wystrzału.
     * @param stopRotateOfTowerTime
     */
    public void stopTowerRotateAfterShotFor(float stopRotateOfTowerTime){
        if (timeSinceLastShot > stopRotateOfTowerTime)
            angle = calculateAngle();
    }

    /**
     * Metoda odpowiada za cykliczną obsługę działań wieży.
     * Jeśli wieża nie jest wycelowana lub cel ma 0 lub mniej ukrytego zdrowia lub dystans do celu jest większy niż zasięg wieży:
     *      - znajdz nowy cel dla wieży.
     * W przeciwnym przypadku:
     *      - sprawdź czy wieża nie powinna pozostać w bezruchu z uwagi na niedawny wystrzał.
     *      Jeśli czas od ostatniego wystrzału jest większy lub równy czasowi co jaki wieża powinna strzelać:
     *          - wystrzel w kierunku przeciwnika, który jest celem.
     *          - ustaw czas od ostatniego wystrzału na 0.
     * Jeśli referencja celu zawiera null lub cel jest martwy:
     *      - przypisz false do zmiennej targeted reprezentującej wycelowanie (lub nie) wieży.
     * Do czasu od ostatniego wystrzału dodaj czas jaki upłynął od ostatnio wyświetlonej klatki obrazu.
     * Rysuj podstawę wieży pociski i wieżę.
     */
    public void update() {
        if (!targeted || target.getHiddenHealth() <= 0 || calculateDistance(target) > range) {
            target = acquireTarget();
        } else {
            stopTowerRotateAfterShotFor(stopRotateOfTowerTime);
            if (timeSinceLastShot >= firingSpeed) {
                shoot(target);
                timeSinceLastShot = 0;
            }
        }

        if (target == null || !target.isAlive())
            targeted = false;

        timeSinceLastShot += GetDeltaFrameTime();

        draw();
    }

    /**
     * Metoda rysuje wieżę nad wystrzeliwanymi pociskami.
     */
    public void draw() {
        DrawTexture(textures[0], x, y, width, height); // rysowanie podstawy wieży

        for (Projectile p : projectiles) // rysowanie pocisków
            p.update();

        if (textures.length > 1)
            for (int i = 1; i < textures.length; i++) // tekstury są rysowane w pętli w odpowiedniej kolejności (to znaczy: kolejne jej elementy nad poprzednimi)
                DrawRotatedTexture(textures[i], x, y, width, height, angle);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Enemy getTarget() {
        return target;
    }

    public int getCost() {
        return cost;
    }

    public void setFiringSpeed(int firingSpeed) {
        this.firingSpeed = firingSpeed;
    }

    public void setTimeSinceLastShot(float timeSinceLastShot) {
        this.timeSinceLastShot = timeSinceLastShot;
    }
}
