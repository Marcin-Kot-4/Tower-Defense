package data;

/**
 * Klasa WaveManager zawiera m.in. metody które tworzą nowe fale,
 * metodę która zwraca numer fali, czy też metodę która ustawia numer fali.
 */
public class WaveManager {

    private float timeSinceLastWave, timeBetweenEnemies;
    private int waveNumber, enemiesPerWave, enemySpawnTypeRange;
    private Enemy[] enemyTypes;
    private Wave currentWave;

    public WaveManager(Enemy[] enemyTypes, float timeBetweenEnemies, int enemiesPerWave, int enemySpawnTypeRange){
        this.enemyTypes = enemyTypes;
        this.timeBetweenEnemies = timeBetweenEnemies;
        this.enemiesPerWave = enemiesPerWave;
        this.timeSinceLastWave = 0;
        this.waveNumber = 0;
        this.currentWave = null;
        this.enemySpawnTypeRange = enemySpawnTypeRange;
        newWave();
    }

    /**
     * Jeśli przynajmniej część obecnej fali przeciwników żyje to wywołaj metodę aktualizującą przeciwników.
     * W przeciwnym przypadku
     */
    public void update(){
        if (!currentWave.isCompleted())
            currentWave.update();
        else
            newWave();
    }

    /**
     * Tworzy nowe fale i przypisuje najnowszy obiekt fali do referencji currentWave, która jest atrybutem tej klasy.
     * Następnie inkrementuje licznik fal.
     * Skraca czas między kolejnymi falami o 10%.
     * Zwiększa ilość przeciwników o jeden.
     */
    private void newWave(){
        currentWave = new Wave(enemyTypes, timeBetweenEnemies, enemiesPerWave, enemySpawnTypeRange);
        waveNumber++;
        timeBetweenEnemies *= 0.90f;
        enemiesPerWave++;
    }

    /**
     * Zwraca obiekt reprezentujący ostatnio stworzoną falę
     * @return
     */
    public Wave getCurrentWave(){
        return currentWave;
    }

    /**
     * Zwraca numer fali.
     */
    public int getWaveNumber(){
        return this.waveNumber;
    }

    /**
     * Ustawia numer fali.
     */
    public void setWaveNumber(int waveNumber) {
        this.waveNumber = waveNumber;
    }
}
