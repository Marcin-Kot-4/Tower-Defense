package data;

import user_interface.*;
import user_interface.UI.*;
import controllers.StateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static controllers.Graphic.*;
import static controllers.Map.*;

<<<<<<< HEAD
/**
 * Klasa Editor wczytuje pierwszą mapę, do jej edycji.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class Editor {

    private boolean showHelp;
    private TileGrid grid;
    private int index;
    private TileType[] types;
    private UI editorUI;
    private Menu tilePickerMenu;
    private Texture menuBackground;

    /**
     * Wczytuje pierwszą mapę.
     * Indeks typu kafelka ustawia na 0.
     * Pobiera typy kafelków.
     * Wczytuje teksturę tła, które jest wyświetlane po prawej stronie ekranu od współrzędnej x = 1280.
     * Tworzy interfejs użytkownika.
     * Ustawia zmienną determinującą czy użytkownik chce aby pomoc była wyświetlana na false.
     */
    public Editor() {
        this.grid = LoadMap("map_1");
        this.index = 0;
        this.types = new TileType[3];
        this.types[0] = TileType.Grass;
        this.types[1] = TileType.Road;
        this.types[2] = TileType.Water;
        this.menuBackground = FastLoad("menu_background");
        setupUI();
        showHelp = false;
    }

    /**
     * Metoda tworząca interfejs użytkownika w Edytorze mapy.
     * Utwórz obiekt klasy UI.
     * Stwórz menu przycisków z jego lewym górnym rogiem na pozycji x = 1280, y = 16. O szerokości 192 px i wysokości 960 px.
     * Pobierz i zapisz w atrybucie tilePickerMenu referencję do obiektu klasy menu będącego częścią kompozycji obiektu klasy UI.
     * Dodaj przyciski odpowiadające za kafelki trawy, drogi oraz wody.
     */
    private void setupUI() {
        editorUI = new UI();
        editorUI.createMenu("TilePicker", 1280, 16, 192, 960, 2, 0);
        tilePickerMenu = editorUI.getMenu("TilePicker");
        tilePickerMenu.quickAdd("Grass", "grass");
        tilePickerMenu.quickAdd("Road", "road");
        tilePickerMenu.quickAdd("Water", "water");
    }

    /**
     * Rysowanie oraz obsługa myszy i klawiatury.
     */
    public void update() {
        draw();

        if (Mouse.next()) { // jeśli zarejestrowano nowe zdarzenie związane z myszą
            boolean mouseClicked = Mouse.isButtonDown(0);
            if (mouseClicked) {
                if (tilePickerMenu.isButtonClicked("Grass")) {
                    index = 0;
                } else if (tilePickerMenu.isButtonClicked("Road")) {
                    index = 1;
                } else if (tilePickerMenu.isButtonClicked("Water")) {
                    index = 2;
                } else
                    setTile();
            }
        }

        // Obsługa klawiatury
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
                SaveMap("map_1", grid);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_F1 && Keyboard.getEventKeyState()) {
                showHelp = !showHelp;
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
                StateManager.setState(StateManager.GameState.MAINMENU);
            }
        }
    }

    /**
     * Rysuje tło wyboru kafelków, mapę, menu kafelków oraz pomoc.
     */
    private void draw() {
        DrawTexture(menuBackground, 1280, 0, 192, 960); // załadowana tekstura jest rozmiaru 256x1024
        grid.draw();
        editorUI.draw();
        if (showHelp){
            DrawTexture(menuBackground, 400, 100, 590, 180);
            editorUI.drawString(420, 110, "Press 'S' to save edited map.");
            editorUI.drawString(420, 150, "This map editor influence only first map.");
            editorUI.drawString(420, 190, "The path must start from the top, left or bottom edge.");
            editorUI.drawString(420, 230, "Press 'ESC' to exit the editor.");
        }
        editorUI.drawString(1310, 900, "F1 - HELP");
    }

    /**
     * Metoda zmieniająca typ terenu kafelka, który znajduje się pod kursorem myszki.
     * Przed próbą zmiany sprawdza czy kursor myszy znajduje się nad planszą.
     */
    private void setTile() {
        if (Mouse.getX() < 1280 && Mouse.getY() < 980){
            grid.setTile((int) Math.floor((float) Mouse.getX() / 64), (int) Math.floor((float) (HEIGHT - Mouse.getY() - 1) / 64), types[index]);
        }
    }
}
