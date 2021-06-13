package data;

import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;

import static controllers.Graphic.*;
import static controllers.Time.*;

<<<<<<< HEAD
/**
 * Klasa Enemy zawiera m.in. metodę która sprawdza czy przycisk dotarł do punktu kontrolnego,
 * metodę która szuka kierunku w którym może się przemieszczać przeciwnik
 * oraz taką metodę co zmienia stan wroga na „martwy”.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class Enemy implements Unit {
    protected int width, height, currentCheckpoint;
    protected float speed, x, y, health, startHealth, hiddenHealth; // hiddenHealth jest wykorzystywany przez wieże po to
    // aby nie strzelać do przeciwnika, który i tak zginie od nadlatujących pocisków wystrzelonych przez inne wieże
    protected Texture texture, healthBackground, healthForeground, healthBorder;
    protected Tile startTile;
    protected boolean first, alive;
    protected TileGrid grid;

    protected ArrayList<Checkpoint> checkpoints;
    protected int[] directions;

    public Enemy(int tileX, int tileY, TileGrid grid){
        this.texture = FastLoad("OrthinInterceptor");
        this.healthBackground = FastLoad("healthBackground");
        this.healthForeground = FastLoad("healthForeground");
        this.healthBorder = FastLoad("healthBorder");
        this.startTile = grid.getTile(tileX, tileY);
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = TILE_SIZE;
        this.height = TILE_SIZE;
        this.speed = 50;
        this.health = 50;
        this.startHealth = health;
        this.hiddenHealth = health;
        this.grid = grid;
        this.first = true;
        this.alive = true;
        this.checkpoints = new ArrayList<Checkpoint>();
        this.directions = new int[2];
        // kierunek X
        this.directions[0] = 0;
        // kierunek Y
        this.directions[1] = 0;
        directions = findNextDirection(startTile);
        this.currentCheckpoint = 0;
        populateCheckpointList();
    }

    /**
     * Konstruktor
     * @param texture - tekstura typu przeciwnika
     * @param startTile - pozycja startowa przeciwnika
     * @param grid - kopia mapy gry
     * @param width - szerokość
     * @param height - wysokość
     * @param speed - prędkość poruszania się
     * Lista checkpoint przechowuje każdy zakręt na drodze przeciwnika
     * Tablica directions odnosi się do osi x i y
     */
    public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed, float health){
        this.texture = texture;
        this.healthBackground = FastLoad("healthBackground");
        this.healthForeground = FastLoad("healthForeground");
        this.healthBorder = FastLoad("healthBorder");
        this.startTile = startTile;
        this.first = true;
        this.alive = true;
        this.grid = grid;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.health = health;
        this.startHealth = health;
        this.hiddenHealth = health;
        this.checkpoints = new ArrayList<Checkpoint>();
        this.directions = new int[2];
        // kierunek X
        this.directions[0] = 0;
        // kierunek Y
        this.directions[1] = 0;
        directions = findNextDirection(startTile);
        this.currentCheckpoint = 0;
        populateCheckpointList();
    }

    /**
     * Przesuwa wrogą jednostkę o iloczyn czasu jaki upłynął od ostatniej wyświetlonej klatki i prędkości tej jednostki
     * w kierunku pobranym z listy punktów kontrolnych.
     * Jeśli jest to pierwsze wywołanie tej metody zmienia ona tylko logiczną wartość zmiennej first na false.
     * Jest to zrealizowane w ten sposób ponieważ po uruchomieniu gry,
     * różnica w czasie (wartość zwrócona przez metodę getDelta() czyli
     * czas teraźniejszy minus czas kiedy została dokonana ostatnia aktualizacją klatki wyświetlanego obrazu gry,
     * ta druga składowa będzie miała wartość 0 ponieważ żadna klatka do tej pory nie została wyświetlona )
     * będzie bardzo duża co sprawi że wrogie jednostki zostaną przesunięte poza okno gry i nie będą widoczne.
     * currentCheckpoint + 1 ponieważ w tym przypadku zmienna ta oznacza na którym checkpoincie aktualnie znajduje się
     * przeciwnik a nie do którego zmierza. Więc musi sprawdzać czy od tego punktu, na którym jest ma dokąd podążać.
     * Jeśli przeciwnik dotarł do ostatniego punktu swojej drogi to wywołaj metodę obsługującą takie zdarzenie.
     */
    public void update(){
        if(first)
            first = false;
        else {
            if (checkpointReached()){
                if (currentCheckpoint + 1 == checkpoints.size())
                    endOfRoadReached();
                else
                    currentCheckpoint++;
            } else {
                x += GetDeltaFrameTime() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
                y += GetDeltaFrameTime() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
            }
        }
    }

    /**
     * @return - zwraca 1 jeśli przeciwnik porusza się w prawo, -1 jeśli porusza się w lewo i 0 jeśli nie przemieszcza się na osi x.
     */
    public int getxEnemyDirection(){
        return checkpoints.get(currentCheckpoint).getxDirection();
    }

    /**
     * @return - zwraca 1 jeśli przeciwnik porusza się w dół, -1 jeśli porusza się do góry i 0 jeśli nie przemieszcza się na osi y.
     */
    public int getyEnemyDirection(){
        return checkpoints.get(currentCheckpoint).getyDirection();
    }

    /**
     * Jeśli przeciwnik dotrze do końca drogi zmniejsz liczbę żyć gracza o jeden, wyzeruj licznik zabitych przeciwników
     * bez utraty życia i usuń przeciwnika.
     */
    private void endOfRoadReached(){
        Player.modifyLives(-1);
        Game.resetEnemiesKilledWithoutHPLose();
        die();
    }

    /**
     * Sprawdza czy przeciwnik dotarł do punktu kontrolnego. Trzy piksele granicy błędu ponieważ nie zawsze w momencie
     * sprawdzania przeciwnik będzie idealnie w miejscu, w którym powinien być. Jest to spowodowane tym że jego pozycja
     * jest aktualizowana co klatkę obrazową o wartość równą czas jaki upłynął od poprzedniej wyświetlonej klatki
     * obrazowej razy ewentualnie jego prędkość. Czasami może się zdarzyć większe opóźnienie i wtedy przeciwnik
     * przemieści się o więcej pikseli niż zazwyczaj - stąd możliwe rozbieżności.
     * Jeśli przeciwnik znajduje się w ustalonym przedziale pikseli odpowiadającym kafelkowi, który jest punktem
     * kontrolnym to:
     *      - ustaw wartość zmiennej reached na true.
     *      - przypisz do aktualnej pozycji przeciwnika pozycję punktu kontrolnego.
     * @return - prawda lub fałsz w zależności od tego czy przeciwnik dotarł do punktu kontrolnego.
     */
    private boolean checkpointReached(){
        boolean reached = false;

        Tile t = checkpoints.get(currentCheckpoint).getTile();

        if (x > t.getX() - 8 && x < t.getX() + 8 &&
            y > t.getY() - 8 && y < t.getY() + 8) {

            reached = true;
            x = t.getX();
            y = t.getY();
        }

        return reached;
    }

    /**
     * Wypełnia listę checkpoints kafelkami na których przeciwnik musi zmienić kierunek poruszania się.
     * Zostało to zrealizowane w następujący sposób:
     * Do atrybutu obiektu directions przypisz kierunek, w którym może się przemieszczać przeciwnik.
     * Do atrybutu obiektu checkpoints dodaj nowy punkt kontrolny - jest to kierunek oraz kafelek, do którego będzie
     * podążał przeciwnik.
     * Stwórz zmienną counter i zainicjuj ją wartością 0.
     * Stwórz zmienna stuck typu boolean i zainicjuj ją wartością false - będzie ona służyła do określenia czy
     * przeciwnik dotarł do miejsca, w którym nie może się poruszać dalej lub zmienił kierunek 20 razy.
     * Dopóki przeciwnik nie utknął:
     *      - do tablicy typu int przypisz kierunek w którym będzie się poruszał przeciwnik.
     *      Jeśli aktualny kierunek na osi X to 2 ( czyli przeciwnik utknął )  lub zmienił kierunek 20 razy to:
     *          - zawartość zmiennej stuck ustaw na true.
     *      W przeciwnym przypadku:
     *          - do atrybutu obiektu directions przypisz kierunek, w którym może się przemieszczać przeciwnik z
     *            ostatniego punktu(kafelka) kontrolnego.
     *          - do atrybutu obiektu checkpoints dodaj nowy punkt kontrolny - jest to informacja o kierunku oraz
     *            kafelku, do którego przeciwnik musi dotrzeć a następnie zmienić kierunek poruszania się.
     *      Zwiększ wartość zmiennej counter o 1. Zmienna ta jest wykorzystywana do wskazywania na ostatnio dodany
     *      punkt kontrolny na liście checkpoints.
     */
    private void populateCheckpointList(){
        directions = findNextDirection(startTile);
        checkpoints.add(findNextCheckpoint(startTile, directions));

        int counter = 0;
        boolean stuck = false;
        while (!stuck){
            int[] currentDirection = findNextDirection(checkpoints.get(counter).getTile());
            if (currentDirection[0] == 2 || counter == 10){
                stuck = true;
            } else {
                directions = findNextDirection(checkpoints.get(counter).getTile());
                checkpoints.add(findNextCheckpoint(checkpoints.get(counter).getTile(), directions));
            }

            counter++;
        }
    }

    /**
     * Wyszukuje najbardziej oddalony kafelek tego samego typu co ten pod przeciwnikiem, znajdujący się w kierunku,
     * w którym sie on przemieszczania.
     * Jest to realizowane w następujący sposób:
     * Dopóki zmienna found zawiera false:
     *      Jeśli kafelek oddalony o tyle pól jaką wartość zawiera zmienna counter i w kierunku przemieszczania się
     *      przeciwnika jest innego typu terenu niz ten pod przeciwnikiem lub w sprawdzaniu wykraczasz poza mapę to:
     *          - wartość logiczną zmiennej found ustaw na true
     *          - zmniejsz wartość zmiennej counter o 1 tak żeby wskazywała na poprzedni kafelek.
     *          - przypisz do obiektu next, szukany kafelek
     *      - zwiększ wartość zmiennej counter
     * @param current - kafelek na którym aktualnie znajduje się przeciwnik
     * @param dir - kierunek, w którym przemieszcza się przeciwnik
     * @return - obiekt klasy Checkpoint zawierający informacje o tym, w którym kierunku i jak daleko ma się
     *           przemieszczać przeciwnik.
     */
    private Checkpoint findNextCheckpoint(Tile current, int[] dir){
        Tile next = null;
        Checkpoint c = null;

        boolean found = false;
        int counter = 1;

        while (!found){
            if (current.getXPlace() + dir[0] * counter == grid.getTilesWide() ||
                    current.getYPlace() + dir[1] * counter == grid.getTilesHigh() ||
                    current.getType() != grid.getTile(current.getXPlace() + dir[0] * counter,
                                                current.getYPlace() + dir[1] * counter).getType()){
                found = true;
                counter -= 1;
                next = grid.getTile(current.getXPlace() + dir[0] * counter,
                                    current.getYPlace() + dir[1] * counter);
            }

            counter++;
        }

        c = new Checkpoint(next, dir[0], dir[1]);
        return c;
    }

    /**
     * Metoda szuka kierunku w którym może się przemieszczać przeciwnik.
     * Realizuje to poprzez wczytanie informacji o sąsiednich czterech kafelkach.
     * Jeśli typ terenu na którymś z nich jest taki sam jak ten pod przeciwnikiem i nie jest to kafelek, na którym przed
     * chwilą był to będzie sie on przemieszczał w kierunku tego sąsiedniego kafelka. Kierunki mają priorytety po to aby
     * przeciwnik był w stanie omijać przeszkody w sensowny sposób (poniżej priorytety kierunku wymienione malejąco):
     * - Góra
     * - Prawo
     * - Dół
     * - Lewo
     * Jeśli typ terenu na sąsiednich czterech kafelkach nie jest taki sam jak ten pod przeciwnikiem to zatrzyma się on.
     * @param current - aktualna pozycja przeciwnika
     * @return - kierunek, w którym może się przemieszczać przeciwnik
     */
    private int[] findNextDirection(Tile current){
        int[] dir = new int[2];
        Tile up = grid.getTile(current.getXPlace(), current.getYPlace() - 1);     // Y - 1 to kafelek powyżej
        Tile right = grid.getTile(current.getXPlace() + 1 , current.getYPlace()); // X + 1 to kafelek po prawej
        Tile down = grid.getTile(current.getXPlace(), current.getYPlace() + 1);   // Y + 1 to kafelek poniżej
        Tile left = grid.getTile(current.getXPlace() - 1 , current.getYPlace());  // X - 1 to kafelek po lewej

        if (current.getType() == up.getType() && directions[1] != 1){
            dir[0] = 0;   // oś X
            dir[1] = -1;  // oś Y
        } else if (current.getType() == right.getType() && directions[0] != -1) {
            dir[0] = 1;   // oś X
            dir[1] = 0;   // oś Y
        } else if (current.getType() == down.getType() && directions[1] != -1) {
            dir[0] = 0;   // oś X
            dir[1] = 1;   // oś Y
        } else if (current.getType() == left.getType() && directions[0] != 1) {
            dir[0] = -1;  // oś X
            dir[1] = 0;   // oś Y
        } else {
            dir[0] = 2;   // oś X - 2 po to aby w innej metodzie wykryć że przeciwnik utknął
            dir[1] = 2;   // oś Y - 2 po to aby w innej metodzie wykryć że przeciwnik utknął
        }

        return dir;
    }

    /**
     * Metoda, zmniejszająca liczbę reprezentującą zdrowie przeciwnika i zwiększająca liczbę zabitych przeciwników
     * bez straty życia przez gracza.
     * @param amount - wartość o jaką metoda zmniejszy liczbę reprezentującą zdrowie przeciwnika.
     */
    public void damage(int amount){
        health -= amount;
        if (health <= 0){
            Player.grantCash(5);
            Game.addEnemiesKilledWithoutHPLose();
            die();
        }
    }

    /**
     * Metoda zmieniająca stan przeciwnika na "martwy"
     */
    private void die(){
        alive = false;
    }

    /**
     * Rysuje przeciwnika na ekranie oraz jego pasek zrowia.
     */
    public void draw(){
        DrawRotatedTexture(texture, x, y, width, height, CalculateAngle(x, y,
                checkpoints.get(currentCheckpoint).getTile().getX(), checkpoints.get(currentCheckpoint).getTile().getY()));
        float healthPercentage = health / startHealth;
        // Tutaj zaczyna się rysowanie pasków zdrowia. Grubość paska 8px, szerokość paska to szerokość kafelka czyli 64px.
        // Położenie na osi y 16px nad kafelkiem przeciwnika.
        // Szerokość tekstury oznaczającej w grze poziom zdrowia zmniejsza się proporcjonalnie względem stosunku posiadanego
        // poziomu zdrowia do początkowego poziomu zdrowia.
        DrawTexture(healthBackground, x, y - 16, width, 8);
        DrawTexture(healthForeground, x, y - 16, width * healthPercentage, 8);
        DrawTexture(healthBorder, x, y - 16, width, 8);
    }

    /**
     * Metoda reduceHiddenHealth zmniejsza wartość ukrytego zdrowia przeciwnika o wartość przekazaną przez parametr.
     * Używana po to aby nie wystrzeliwać kolejnych pocisków w przeciwnika, który zostanie zniszczony poprzez wystrzelone
     * do tej pory pociski kierowane.
     * @param amount - obrażenia zadawane przez ten typ pocisku
     */
    public void reduceHiddenHealth(float amount){
        hiddenHealth -= amount;
    }

    /**
     * Metoda restoreHiddenHealth przywraca ukryte zdrowie przeciwnika o wartość przekazaną przez parametr.
     * Używana kiedy na przykład pocisk kierowany trafi w przeciwnika, który zasłania cel.
     * @param amount - obrażenia zadawane przez ten typ pocisku
     */
    public void restoreHiddenHealth(float amount){
        hiddenHealth += amount;
    }

    public float getHiddenHealth(){
        return hiddenHealth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = FastLoad(texture);
    }

    public Tile getStartTile() {
        return startTile;
    }

    public void setStartTile(Tile startTile) {
        this.startTile = startTile;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public TileGrid getTileGrid() {
        return grid;
    }

    public boolean isAlive(){
        return alive;
    }
}
