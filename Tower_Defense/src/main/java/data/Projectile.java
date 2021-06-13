package data;

import org.newdawn.slick.opengl.Texture;

import java.util.concurrent.CopyOnWriteArrayList;

import static controllers.Time.*;
import static controllers.Graphic.*;

/**
 * Abstrakcyjna klasa podobna do klasy Wave. Odpowiada za pociski.
 */
public abstract class Projectile implements Unit {

    private boolean alive;
    protected int damage, width, height;
    protected float x, y, speed, range, xVelocity, yVelocity, angle, xPreviousPosition, yPreviousPosition, totalDistanceTraveled;
    protected Texture texture;
    protected Enemy target, hittedEnemy;
    private CopyOnWriteArrayList<Enemy> enemyList;

    /**
     *
     * @param type - typ pocisku
     * @param target - obiekt klasy Enemy reprezentujący cel
     * @param x - współrzędne na osi x w których ma znaleźć się lewy górny róg tekstury pocisku
     * @param y - współrzędne na osi y w których ma znaleźć się lewy górny róg tekstury pocisku
     * @param width - szerokość tekstury pocisku
     * @param height - wysokość tekstury pocisku
     */
    public Projectile(ProjectileType type, Enemy target, float x, float y, int width, int height) {
        this.texture = type.texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = type.speed;
        this.range = type.range;
        this.damage = type.damage;
        this.target = target;
        this.alive = true;
        this.xVelocity = 0f;
        this.yVelocity = 0f;
        this.angle = calculateAngle();
        calculateDirection();
        this.enemyList = new CopyOnWriteArrayList<Enemy>();
    }

    /**
     * Metoda odpowiadająca za strzelanie w odpowiednim kierunku.
     * Oblicza odległość od wieży do celu. Metoda abs służy do obliczania wartości bezwzględnej.
     * Oblicza stosunek przemieszczenia pocisku w dwóch osiach aby osiągnąć zamierzony cel.
     * Stosunek ten będzie reprezentował stosunek prędkości pocisku w osi x i y.
     */
    public void calculateDirection() {
        float totalAllowedMovement = 1.0f;
        // + (rozmiar_kafelka / 2 - szerokość tekstury pocisku / 2)  // po to żeby środek pocisku uderzał w środek celu a nie
        float xDistanceFromTarget = Math.abs(target.getX() - x + ((TILE_SIZE / 2) - (width / 2)));
        // + (rozmiar kafelka / 2 - wysokość tekstury pocisku / 2)  // w jego lewy górny róg
        float yDistanceFromTarget = Math.abs(target.getY() - y + ((TILE_SIZE / 2) - (height / 2)));
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
        float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
        xVelocity = xPercentOfMovement;
        yVelocity = totalAllowedMovement - xPercentOfMovement;
        if (target.getX() < x)
            xVelocity *= -1;
        if (target.getY() < y)
            yVelocity *= -1;
    }

    /**
     * Metoda odpowiadająca za strzelanie w odpowiednim kierunku.
     * Oblicza odległość od wieży do celu. Metoda abs służy do obliczania wartości bezwzględnej.
     * Oblicza stosunek przemieszczenia pocisku w dwóch osiach aby osiągnąć zamierzony cel.
     * Stosunek ten będzie reprezentował stosunek prędkości pocisku w osi x i y.
     * Nadpisana wersja metody przewiduje pozycję celu w zależności od prędkości pocisku i celu.
     * Jej zadaniem jest symulowanie celowania Close-In Weapons System takich jak Phalanx. Broń taka posiada dużą
     * celność w przypadku kiedy cel porusza się ruchem jednostajnym prostoliniowym.
     * Zasada działania algorytmu:
     * Oblicz odległość do celu.
     * Oblicz czas jaki potrzebuje pocisk żeby pokonać dystans od wieży do celu.
     * Znając prędkość i kierunek ruchu przeciwnika oblicz pozycję na osi x oraz y, w której będzie się on znajdował
     *      po upłynięciu wcześniej obliczonego czasu.
     * Dopóki dystans dla nowych współrzędnych jest różny od dystansu dla starych przewidywanych współrzędnych, w których
     * pocisk miałby trafić w cel (prawie zawsze program potrzebuje dwóch iteracji):
     *      - do nowej odległości do celu przypisz dystans od wieży (tak na prawdę od miejsca w którym pojawi się pocisk w wieży)
     *        do celu.
     *      - do starej odległości do celu przypisz tą obliczoną w linii wyżej.
     *      - oblicz czas jaki potrzebuje pocisk żeby pokonać nową odległość od wieży do celu.
     *      - oblicz gdzie na osi x i y będzie się znajdował przeciwnik po pokonaniu przez pocisk nowego dystansu.
     * Zapisz przewidywane współrzędne, w których pocisk trafi w cel.
     * Zaktualizuj kąt natarcia pocisku.
     * Reszta metody jest taka sama jak w klasie abstrakcyjnej Projectile.
     */
    protected void calculateDirectionWithPrediction() {
        float totalAllowedMovement = 1.0f;
        //int counter = 0;
        float xDistanceFromTarget = 0;
        float yDistanceFromTarget = 0;
        float newDistanceToTarget = 0;
        float distanceToTarget = calculateDistance(target);
        float timeProjectileFlightToTarget =  distanceToTarget / this.speed;
        float predicted_xTargetPosition = xTargetPositionAfter(timeProjectileFlightToTarget);
        float predicted_yTargetPosition = yTargetPositionAfter(timeProjectileFlightToTarget);

        while(distanceToTarget != newDistanceToTarget)
        {
            newDistanceToTarget = calculateDistanceAxis(predicted_xTargetPosition, predicted_yTargetPosition);
            //System.out.println("Iteration: " + counter + " / DistanceToTarget: " + distanceToTarget + " / newDistanceToTarget: " + newDistanceToTarget);
            distanceToTarget = newDistanceToTarget;
            timeProjectileFlightToTarget = newDistanceToTarget / this.speed;
            predicted_xTargetPosition = xTargetPositionAfter(timeProjectileFlightToTarget);
            predicted_yTargetPosition = yTargetPositionAfter(timeProjectileFlightToTarget);
            //counter++;
        }
        // System.out.println("Iteration: " + counter + " / DistanceToTarget: " + distanceToTarget + " / newDistanceToTarget: " + newDistanceToTarget);
        // System.out.println("----------- FOUND IN: " + counter + " iterations."); // for testing purpose
        xDistanceFromTarget = Math.abs(predicted_xTargetPosition - x);
        yDistanceFromTarget = Math.abs(predicted_yTargetPosition - y);

        double angleTEMP = Math.atan2(predicted_yTargetPosition - y, predicted_xTargetPosition - x - width / 2 + TILE_SIZE / 2); // width / 2 <--- środek górnej części pocisku
        this.angle = (float) Math.toDegrees(angleTEMP) - 90;

        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
        float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
        xVelocity = xPercentOfMovement;
        yVelocity = totalAllowedMovement - xPercentOfMovement;
        if (predicted_xTargetPosition < x)
            xVelocity *= -1;
        if (predicted_yTargetPosition < y)
            yVelocity *= -1;
    }

    /**
     * Metoda damage() zmniejsza ilość zdrowia trafionego przeciwnika i niszczy pocisk.
     * Zmniejsz ilość zdrowia trafionego przeciwnika o wartość obrażeń jaką zadaje ten pocisk.
     * Zniszcz pocisk.
     */
    public void damage(){
        hittedEnemy.damage(this.damage);
        this.alive = false;
    }

    /**
     * Jeśli pocisk nie trafił jeszcze w cel lub uprzednio nie skończył się jego zasięg:
     *      - zapisz aktualną pozycję pocisku.
     *      - przemieść pocisk.
     *      - dodaj przebyty dystans w tej aktualizacji do całkowitego przebytego dystansu przez ten pocisk.
     *      Jeśli całkowity przebyty dystans tego pocisku jest mniejszy lub równy jego zasięgowi:
     *          - zaktualilzuj listę przeciwników. [ze statycznego atrybutu o typie WaveManager klasy Game poprzez statyczną metodę
     *                                              getWaveManager() pobierana jest lista przeciwników w obecnej fali]
     *          Iteruj po liście przeciwników:
     *                Jeśli wykryjesz kolizję pocisku z przeciwnikiem z listy:
     *                    - przypisz do atrybutu hittedEnemy przeciwnika z którym wystąpiła kolizja.
     *                    - zadaj obrażenia i zniszcz pocisk.
     *                    - przerwij wykonywanie się pętli for().
     *          Wywołaj metodę rysującą.
     *      Jeśli całkowity przebyty dystans tego pocisku jest większy niż jego zasięg:
     *          - zniszcz pocisk.
     */
    public void update() {
        if (alive) {
            xPreviousPosition = x;
            yPreviousPosition = y;
            x += xVelocity * GetDeltaFrameTime() * speed;
            y += yVelocity * GetDeltaFrameTime() * speed;
            totalDistanceTraveled += (float) Math.sqrt((Math.pow(x - xPreviousPosition, 2) + Math.pow(y - yPreviousPosition, 2)));
            if (totalDistanceTraveled <= range){
                enemyList = Game.getWaveManager().getCurrentWave().getEnemyList();
                for (Enemy enemyFromList: enemyList){
                    if (CheckCollision(x, y, width, height, enemyFromList.getX(), enemyFromList.getY(), enemyFromList.getWidth(), enemyFromList.getHeight())){
                        hittedEnemy = enemyFromList;
                        damage();
                        break;
                    }
                }
                draw();
            } else {
                setAlive(false);
            }
        }
    }

    /**
     * Metoda rysująca pociski niekierowane.
     * Rysuje teksturę pocisku obróconą o kąt który został obliczony w konstruktorze pocisku czyli podczas wystrzału.
     * Później kąt ten się nie zmienia i jest zawarty w zmiennej angle (typ float).
     */
    public void draw() {
        DrawRotatedTexture(texture, x, y, width, height, angle);
    }

    /**
     * Metoda rysująca pociski kierowane. Obraca teksturę pocisku w trakcie lotu tak aby była skierowana w cel.
     * Jest to realizowane poprzez obliczanie kąta za każdym razem kiedy pocisk jest rysowany. Wynik tych obliczeń jest
     * przekazywany jako czwarty parametr metody DrawQuadTextureRotate.
     * Metoda ta jest używana w typie pocisku "Rocket" poprzez nadpisanie metody draw właśnie tą metodą.
     */
    public void drawGuidedMissile() {
        DrawRotatedTexture(texture, x, y, width, height, calculateAngle());
    }

    /**
     * Metoda obliczająca kąt o jaki pocisk/rakieta musi się obrócić aby wycelować w przeciwnika.
     * atan2 to statyczna metoda obiektu Math, która oblicza kąt w radianach na podstawie współrzędnych
     * (z obrotem w kierunku przeciwnym do ruchu wskazówek zegara).
     * toDegrees konwertuje kąt w radianach na przybliżony kąt w stopniach.
     *
     * @return
     */
    protected float calculateAngle() {
        // + ((TILE_SIZE / 2) - (width / 2)) dla obliczania odległości do środka tekstury przeciwnika
        float xEnemyCenter = target.getX() + ((TILE_SIZE / 2) - (width / 2));
        float yEnemyCenter = target.getY() + ((TILE_SIZE / 2) - (height / 2));
        float xProjectileCenter = x + width / 2;
        float yProjectileTop = y;
        double angle = Math.atan2(yEnemyCenter - yProjectileTop, xEnemyCenter - xProjectileCenter);
        return (float) Math.toDegrees(angle) - 90;
    }

    /**
     * Zwraca odległość od pocisku do przeciwnika.
     * @param enemy - obiekt przeciwnika do którego dystans ma zostać obliczony.
     * @return - zwracana wartość jest przekątną prostokąta o bokach xDistance oraz yDistance czyli odległością
     * od pocisku do przeciwnika.
     */
    protected float calculateDistance(Enemy enemy) {
        float xDistance = Math.abs(enemy.getX() - x + (TILE_SIZE / 2) + width / 2); // środek pocisku na osi x, środek przeciwnika na osi x
        float yDistance = Math.abs(enemy.getY() - y + (TILE_SIZE / 2));             // środek przeciwnika na osi x
        float d = (float) Math.sqrt((Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));
        return d;
    }

    protected float calculatexDistance(Enemy enemy){
        return Math.abs(enemy.getX() - x + (TILE_SIZE / 2) + width / 2);
    }

    protected float calculateyDistance(Enemy enemy){
        return Math.abs(enemy.getY() - y + (TILE_SIZE / 2));
    }

    /**
     * Metoda getNewTarget wyszukuje nowy, najbliższy oraz posiadający dodatnią liczbę reprezentującą jego zdrowie oraz
     * ukryte zdrowie cel dla pocisku.
     * Do zmiennej closest typu Enemy przypisz null.
     * Do zmiennej closestDistance przypisz największą wartość jaką można zapisać w typie float.
     * Przeglądając listę przeciwników znajdź takiego, który jest najbliżej oraz jego ukryte zdrowie jak i zdrowie jest
     * większe od zera.
     * Jeśli został znaleziony cel spełniający powyższe kryteria przypisz jego referencję do atrybutu target.
     */
    public void getNewTarget() {
        Enemy closest = null;
        float closestDistance = Float.MAX_VALUE;
        for (Enemy enemy : enemyList) {
            if (calculateDistance(enemy) < closestDistance && enemy.getHiddenHealth() > 0 && enemy.getHealth() > 0) {
                closestDistance = calculateDistance(enemy);
                closest = enemy;
            }
        }
        if (closest != null)
            target = closest;
    }

    /**
     * Metoda obliczająca przekątną prostokąta. Jest używana do obliczania odległości między pociskiem a celem.
     * @param xTarget - pozycja celu na osi x
     * @param yTarget - pozycja celu na osi y
     * @return - zwraca odległość od pocisku do celu.
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

    public Enemy getHittedEnemy() {
        return hittedEnemy;
    }

    public void setAlive(boolean status){
        alive = status;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
    }
}
