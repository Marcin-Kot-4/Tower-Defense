package controllers;

import data.Editor;
import data.Game;
import data.MainMenu;

/**
 * Klasa StateManager zawiera w sobie początkowy stan gry, czyli Główne Menu,
 * obsługę stanu gry, Mierzenie liczby klatek na sekundę oraz zmianę stanu gry.
 */
public class StateManager {
    public static enum GameState{
        MAINMENU, GAME, EDITOR
    }

    public static GameState gameState = GameState.MAINMENU; // początkowy stan gry to Główne Menu
    public static MainMenu mainMenu;
    public static Game game;
    public static Editor editor;

    public static long currentTime = System.currentTimeMillis();
    public static long nextSecond = System.currentTimeMillis() + 1000; // następna sekunda po aktualnej
    public static int framesInLastSecond = 0;
    public static int framesInCurrentSecond = 0;
    public static int gameTime = 0;

    /**
     * Obsługuje stany gry (menu, gra, edytor)
     */
    public static void update(){
        switch (gameState){
            case MAINMENU:
                game = null; // dodane po to żeby po powrocie do głównego menu gra była usuwana
                if (mainMenu == null)
                    mainMenu = new MainMenu();
                mainMenu.update();
                break;
            case GAME:
                if (game == null){
                    game = new Game();
                    gameTime = 0;
                }
                game.update();
                break;
            case EDITOR:
                if (editor == null)
                    editor = new Editor();
                editor.update();
                break;
        }

        // Mierzenie liczby klatek na sekundę
        currentTime = System.currentTimeMillis();
        if (currentTime > nextSecond){ // jeśli minęła sekunda
            nextSecond += 1000; // ustaw następną na 1000 ms po poprzedniej
            framesInLastSecond = framesInCurrentSecond; // do zmiennej framesInLastSecond przypisz te zliczone w pętli w zmiennej framesInCurrentSecond
            framesInCurrentSecond = 0; // wyzeruj liczbę klatek w aktualnej sekundzie
            if (game != null) // czas gry
                gameTime++;
        }

        framesInCurrentSecond++; // co wykonanie pętli dodaj jedną klatkę do licznika
    }

    /**
     * Metoda zmieniająca stan gry na ten przekazany przez parametr. Może to być na przykład MAINMENU, GAME, EDITOR.
     * @param newState - stan gry na jaki ma zostać zmieniony aktualny.
     */
    public static void setState(GameState newState){
        gameState = newState;
    }
}
