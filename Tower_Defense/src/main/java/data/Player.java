package data;

import controllers.Time;
import controllers.StateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

import static controllers.Graphic.*;

<<<<<<< HEAD
/**
 * Klasa Player zawiera m.in. do sprawdzenia czy gracz ma wystarczającą ilość pieniążków
 * by mógł kupić daną wieżę obronną, metoda która zmienia liczbę
 * żyć gracza oraz metoda obsługująca rysowanie oraz umieszczenie trzymanej wieży.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class Player {

    private TileGrid grid;
    private TileType[] types;
    private WaveManager waveManager;
    private ArrayList<Tower> towerList;
    private boolean leftMouseButtonDown, rightMouseButtonDown, holdingTower, showHelp, showFPS, gameOver;
    private Tower tempTower;
    public static int cash, lives;

    /**
     * Zapisz siatkę kafelków.
     * Pobierz typy kafelków.
     * Zapisz referencję do menadżera fal przeciwników.
     * Utwórz listę wież, która będzie służyła do przechowywania wież zbudowanych przez gracza.
     * @param grid - obiekt klasy TileGrid. Przekazuje informacje o siatce kafelek.
     * @param waveManager - menadżer fal przeciwników.
     */
    public Player(TileGrid grid, WaveManager waveManager) {
        this.grid = grid;
        this.types = new TileType[3];
        this.types[0] = TileType.Grass;
        this.types[1] = TileType.Road;
        this.types[2] = TileType.Water;
        this.waveManager = waveManager;
        this.towerList = new ArrayList<Tower>();
        this.leftMouseButtonDown = false;
        this.rightMouseButtonDown = false;
        this.holdingTower = false;
        this.tempTower = null;
        this.showHelp = false;
        this.showFPS = false;
        this.gameOver = false;
        cash = 0;
        lives = 0;
    }

    /**
     * Ten konstruktor jest używany podczas zmiany mapy aby zacząć ją z pustą listą wież, nową siatką, oraz nowym
     * menadżerem fal przeciwników. Stara pozostaje ilość żyć i gotówki.
     * @param grid - atrybut tej klasy to obiekt klasy TileGrid. Przechowuje informacje o siatce kafelek.
     */
    public Player(TileGrid grid, WaveManager waveManager, int cash, int lives) {
        this.grid = grid;
        this.types = new TileType[3];
        this.types[0] = TileType.Grass;
        this.types[1] = TileType.Road;
        this.types[2] = TileType.Water;
        this.waveManager = waveManager;
        this.towerList = new ArrayList<Tower>();
        this.leftMouseButtonDown = false;
        this.rightMouseButtonDown = false;
        this.holdingTower = false;
        this.tempTower = null;
        Player.cash = cash;
        Player.lives = lives;
    }

    /**
     * Służy do ustawiania początkowych wartości atrybutów statycznych klasy Player
     */
    public void setup() {
        cash = 100;
        lives = 10;
    }

    /**
     * Metoda sprawdza czy gracz posiada wystarczającą ilość gotówki na zakup wieży. Jeśli tak od do aktualnej liczby
     * gotówki odejmuje wartość wieży i zwraca true. Jeśli nie zwraca false.
     * @param amount
     * @return
     */
    public boolean modifyCash(int amount) {
        if (cash - amount >= 0) {
            cash -= amount;
            return true;
        }
        System.out.println(cash);
        return false;
    }

    /**
     * Metoda dodająca graczowi gotówkę.
     * @param amount - liczba o jaka ma zostać dodana do aktualnej liczby gotówki.
     */
    public static void grantCash(int amount){
        if (cash + amount >= 0)
            cash += amount;
    }

    /**
     * Modyfikuje liczbę żyć gracza.
     * @param amount - liczba o jaką liczba żyć ma zostać zmodyfikowana.
     */
    public static void modifyLives(int amount) {
        if (lives + amount >= 0)
            lives += amount;
    }

    /**
     * Metoda obsługująca rysowanie oraz umieszczanie trzymanej wieży, aktualizację listy wież i przeciwników.
     * Sprawdza czy gracz został pokonany przez przeważające siły wroga.
     * Jeśli został naciśnięty prawy przycisk myszy porzuć umieszczanie trzymanej wieży.
     */
    public void update() {
        if (lives == 0){
            gameOver = true;
        }

        if (holdingTower) {
            if (Mouse.getX() < 1280){
                tempTower.setX(getMouseTile().getX()); // pobierz pozycje kursora myszy na osi X
                tempTower.setY(getMouseTile().getY()); // pobierz pozycje kursora myszy na osi Y
                tempTower.draw(); // Rysuj trzymaną wieżę w miejscu nad którym znajduje się kursor myszy
            }
        }

        for (Tower t : towerList) {
            t.update();
            t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
            // Aktualizuje listę przeciwników dla każdego obiektu klasy Tower.
        }

        // Obsługa myszy - z jednorazową rejestracją pojedynczego kliknięcia
        if (Mouse.isButtonDown(0) && leftMouseButtonDown == false) { // jesli LPM jest wcisniety i wartosc zmiennej leftMouseButtonDown to false
            placeTower(); // metoda umieszczająca wieżę
        }

        if (Mouse.isButtonDown(1) && rightMouseButtonDown == false) { // jesli LPM jest wcisniety i wartosc zmiennej leftMouseButtonDown to false
                holdingTower = false;
        }

        leftMouseButtonDown = Mouse.isButtonDown(0); // przypisuje true jeśli lewy przycisk myszy jest nadal naciśnięty
        // co uniemożliwia ponowne wejście w instrukcje warunkową powyżej i tym samym sprawia że jej zawartość nie jest
        // wykonywana wielokrotnie podczas pojedynczego naciśnięcia lewego przycisku myszy
        // lub false w przeciwnym przypadku

        rightMouseButtonDown = Mouse.isButtonDown(1);

        // Obsługa klawiatury
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
                Time.ChangeGameSpeed(0.2f); // zwiększenie tempa gry o 0.2
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
                Time.ChangeGameSpeed(-0.2f);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_P && Keyboard.getEventKeyState()) {
                Time.Pause();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_F1 && Keyboard.getEventKeyState()) {
                if (showHelp)
                    showHelp = false;
                else
                    showHelp = true;
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_F && Keyboard.getEventKeyState()) {
                if (showFPS)
                    showFPS = false;
                else
                    showFPS = true;
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
                StateManager.setState(StateManager.GameState.MAINMENU);
            }
        }
    }

    /**
     * Metoda umieszczająca wieżę na kafelku, nad którym znajduje się kursor myszy jeżeli gracz aktualnie trzyma wybraną
     * wieżę oraz posiada wystarczającą ilość gotówki.
     * Po próbie umieszczenia zmień wartość holdingTower na false (co będzie oznaczało że gracz aktualnie nie trzyma
     * żadnej wieży) oraz przypisz null do zmiennej tempTower, która jest tymczasowym pojemnikiem na wieżę, która
     * została wybrana i jeśli zostaną spełnione odpowiednie warunki zostanie umieszczona.
     */
    private void placeTower() {
        Tile currentTile = getMouseTile();
        if (holdingTower)
            if (!currentTile.getOccupied() && modifyCash(tempTower.getCost())){ // warunki muszą być w takiej kolejności bo inaczej odejmuje
                // koszt budowy od aktualnej liczby gotówki nawet jeśli nie można budować w tym miejscu
                towerList.add(tempTower);
                currentTile.setOccupied(true);
                holdingTower = false;
                tempTower = null;
            }

    }

    /**
     * Metoda podnosząca wieżę z UI wykorzystywana w klasie Game
     *
     * @param t
     */
    public void pickTower(Tower t) {
        tempTower = t;
        holdingTower = true;
    }

    /**
     * Metoda zwracająca kafelek nad którym znajduje się kursor myszy.
     *
     * @return
     */
    private Tile getMouseTile() {
        return grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
        // od wysokości
        // zostaje odjęta pozycja myszy w osi Y ponieważ w grze wartość na osi Y rośnie kiedy poruszamy się w dół
        // a nie w górę jak w przypadku układu kartezjańskiego, -1 ponieważ wysokość liczymy od zera a nie od jedynki
        // 960px indeksowanych od 0 (czyli pierwszy piksel ma indeks 0 a ostatni 959)
    }

    /**
     * Opróżnia listę zbudowanych wież czyli usuwa je z mapy.
     */
    protected void removeTowers(){
        this.towerList.removeAll(towerList);
    }

    public boolean showHelp() {
        return showHelp;
    }

    public boolean showFPS() {
        return showFPS;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getCash() {
        return cash;
    }

    public int getLives() {
        return lives;
    }
}
