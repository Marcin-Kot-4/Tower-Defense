package controllers;

import org.lwjgl.Sys;

<<<<<<< HEAD
/**
 * Klasa Time zawiera m.in. metodę która zwraca czas pomiędzy teraźniejszością,
 * a ostatnią aktualizacją klatki wyświetlanego obrazu gry,
 * metodę aktualizującą czas uruchomienia gry,
 * czy statyczną metodę do zatrzymywania i wznawiania gry.
 */
=======

>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class Time {

    private static boolean paused = false;
    public static float deltaFrameTime = 0, gameSpeed = 1;
    public static long lastFrameTime, totalTime;

    /**
     * @return - zwraca typ long zwierający teraźniejszy czas
     */
    public static long GetTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    /**
     * @return - zwraca czas pomiędzy teraźniejszością a ostatnią aktualizacją klatki wyświetlanego obrazu gry.
     * Jeśli czas od ostatniej wyświetlonej klatki jest większy niż 50 milisekund to zwróć 0,05.
     */
    public static float GetTimeDelta() {
        long currentTime = GetTime();
        int delta = (int) (currentTime - lastFrameTime);
        lastFrameTime = GetTime();
        if (delta * 0.001f > 0.05f)
            return 0.05f;
        return delta * 0.001f;
    }

    /**
     * Wartość statycznej zmiennej deltaFrameTime będącej atrybutem tej klasy zmienia się za każdym razem kiedy zostaje wyświetlona
     * klatka obrazu. Wartość ta to czas pomiędzy poprzednią a aktualnie wyświetlaną klatką obrazu.
     * @return - 0 jeśli gra została zatrzymana, d * multiplier w przeciwnym przypadku
     */
    public static float GetDeltaFrameTime() {
        if (paused)
            return 0;
        else
            return deltaFrameTime * gameSpeed;
    }

    /**
     * Zaktualizuj czas uruchomienia gry
     */
    public static void Update() {
        deltaFrameTime = GetTimeDelta();
        totalTime += deltaFrameTime;
    }

    /**
     * Statyczna metoda zmieniająca tempo gry.
     * @param change - nowe tempo gry
     */
    public static void ChangeGameSpeed(float change) {
        if (gameSpeed + change > 0.1f && gameSpeed + change < 5) {
            gameSpeed += change;
        }
    }

    /**
     * Statyczna metoda do zatrzymywania i wznawiania gry.
     */
    public static void Pause() {
        if (paused)
            paused = false;
        else
            paused = true;
    }

    /**
     * @return - zwraca tempo gry
     */
    public static float GetGameSpeed() {
        return gameSpeed;
    }

    /**
     * @return - zwraca true lub false w zależności od tego czy gra jest zatrzymana.
     */
    public static boolean IsPaused() {
        return paused;
    }
}















