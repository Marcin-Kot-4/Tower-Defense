package data;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static controllers.Time.GetDeltaFrameTime;

<<<<<<< HEAD
/**
 * Klasa Wave zawiera listę CopyOnWriteArrayList, ponieważ ten typ listy jest bezpieczny dla wątków.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class Wave {

    private float timeSinceLastSpawn, spawnTime;
    private Enemy[] enemyTypes;
    private CopyOnWriteArrayList<Enemy> enemyList; // ten typ listy jest bezpieczny dla wątków ponieważ operacje
    // takie jak dodawanie, usuwanie są implementowane przez utworzenie nowej kopii podstawowej listy
    // i nie jest ona modyfikowana jeśli istnieje jej iterator. Sam zaś iterator nie zgłasza ConcurrentModificationException
    // oraz nie odzwierciedla dodań, usunięć ani zmian w liście od czasu utworzenia iteratora. Operacje zmiany elementów
    // przy użyciu iteratorów nie są obsługiwane. Jest użyteczna kiedy występuje dużo więcej operacji odczytu niż
    // modyfikacji na tej liście.
    // W tym przypadku kiedy jeden wątek iteruje po liście a drugi(może to być też ten sam wątek) usuwa z niej
    // przeciwnika ponieważ jest martwy, java.util zwraca wyjątek ConcurrentModificationException. Co oznacza że
    // żaden wątek nie może modyfikować listy kiedy iterator po niej iteruje.
    // Zmodyfikowana kopia listy zastępuje oryginalną tylko kiedy iteracja po liście oryginalnej się zakończy.
    private int enemiesPerWave, enemiesSpawned, enemySpawnTypeRange;
    private boolean waveCompleted;

    /**
     * Konstruktor
     *
     * @param spawnTime - odstęp czasu pomiędzy pojawianiem sie nowych przeciwników
     * @param enemyTypes - typ przeciwnika - obiekt klasy Enemy jest atrybutem klasy Wave (tej klasy), w nim są
     *                  przechowywane informacje o tym jaki przeciwnik i gdzie ma zostać stworzony
     *                  Tworzy pierwszego przeciwnika.
     */
    public Wave(Enemy[] enemyTypes, float spawnTime, int enemiesPerWave, int enemySpawnTypeRange) {
        this.enemyTypes = enemyTypes;
        this.spawnTime = spawnTime;
        this.enemiesPerWave = enemiesPerWave;
        this.enemySpawnTypeRange = enemySpawnTypeRange;
        this.enemiesSpawned = 0;
        this.timeSinceLastSpawn = 0;
        this.enemyList = new CopyOnWriteArrayList<Enemy>();
        this.waveCompleted = false;

        spawn();
    }

    /**
     * Metoda ta przesuwa i rysuje na ekranie przeciwników oraz co ustalony czas tworzy nowych.
     * Czas od ostatniego pojawienia się przeciwnika jest zwiększany o wartość zwróconą przez metodę Delta() należącą
     * do klasy Time. Jest to realizowane co wyświetlenie się klatki obrazu. Tworzy nowych przeciwników dopóki ich
     * liczba nie jest równa liczbie zadeklarowanych przeciwników na jedną falę. Sprawdza czy jacyś przeciwnicy w tej fali
     * żyją. Jeśli tak to aktualizuje ich położenie i rysuje.
     * Jeśli żaden z nich nie żyje i wszyscy przeciwnicy z tej fali zostali odrodzeni:
     *      - ustaw wartość zmiennej waveCompleted na true.
     */
    public void update() {
        // Zakłada że wszyscy przeciwnicy są martwi dopóki pętla for nie udowodni że jest inaczej
        boolean allEnemiesDead = true;
        if (enemiesSpawned < enemiesPerWave) {
            timeSinceLastSpawn += GetDeltaFrameTime();
            if (timeSinceLastSpawn > spawnTime) {
                spawn();
                timeSinceLastSpawn = 0;
            }
        }

        for (Enemy e : enemyList) {
            if (e.isAlive()) {
                allEnemiesDead = false;
                e.update();
                e.draw();
            } else
                enemyList.remove(e); // jeśli jakiś przeciwnik nie żyje, usuń go z listy
        }

        if (allEnemiesDead && enemiesSpawned == enemiesPerWave)
            waveCompleted = true;
    }

    /**
     * Dodaje nowego przeciwnika do listy. Typ przeciwnika jest losowany z zakresu zawartego w zmiennej enemySpawnTypeRange.
     * Wartość tej zmiennej jest ustawiana w klasie Game podczas uruchamiania gry i rośnie przy każdej zmianie mapy tak
     * aby zwiększać poziom trudności. Należy pamiętać, że zakres maksymalny zakres może być taki jak rozmiar
     * tablicy - aktualnie 25. Przekazanie wartości 25 jako indeks tablicy dwudziestopięcio elementowej spowodowałoby
     * wyrzucenie wyjątku IndexOutOfBoundException. Jako parametr metody nextInt jest przekazywana wartość 25 natomiast
     * metoda ta zwraca liczbę z przedziału 0-24 czyli 25 różnych wartości co sprawia, że nie wykroczymy poza rozmiar
     * tablicy.
     */
    private void spawn() {
        int chosenEnemy = 0;
        Random random = new Random();
        chosenEnemy = random.nextInt(enemySpawnTypeRange);

        enemyList.add(new Enemy(enemyTypes[chosenEnemy].getTexture(), enemyTypes[chosenEnemy].getStartTile(), enemyTypes[chosenEnemy].getTileGrid(),
                enemyTypes[chosenEnemy].width, enemyTypes[chosenEnemy].height, enemyTypes[chosenEnemy].getSpeed(), enemyTypes[chosenEnemy].getHealth()));

        enemiesSpawned++;
    }

    public boolean isCompleted() {
        return waveCompleted;
    }

    /**
     * Getter zwracający listę przeciwników, którzy są w tej fali.
     * @return
     */
    public CopyOnWriteArrayList<Enemy> getEnemyList(){
        return enemyList;
    }
}
