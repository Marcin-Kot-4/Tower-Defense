package data;

import user_interface.UI;
import controllers.StateManager;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static controllers.Graphic.*;

/**
 * Klasa MainMenu zawiera m.in. przyciski znajdujące się w menu głównym,
 * metodę która odpowiada za włączenie stanów gry dostępnych w głównyum menu,
 * informacje o grze oraz zmianę tekstur przycisków.
 */
public class MainMenu {

    private Texture background;
    private UI menuUI;
    private boolean showINFO;
    boolean mouseButton1;

    /**
     * Wczytaj teksturę tła głównego menu gry.
     * Utwórz interfejs użytkownika.
     * Dodaj przyciski.
     */
    public MainMenu() {
        background = FastLoad("mainmenu");
        menuUI = new UI();
        // metoda poniżej dodaje przycisk do listy przycisków składającej się z obiektów klasy Button będących atrybutem należącym do klasy UI,
        // której to obiekt jest atrybutem klasy MainMenu
        menuUI.addButton("PLAY", "playButton", WIDTH / 4 - 256, (int) (HEIGHT * 0.30f)); // przycisk ma rozmiar 256x64px
        // wartości x oraz y są dodatkowo modyfikowane aby odpowiednio pozycjonować przyciski
        menuUI.addButton("EDIT", "editButton", WIDTH / 4 - 256, (int) (HEIGHT * 0.40f));
        menuUI.addButton("INFO", "infoButton", WIDTH / 4 - 256, (int) (HEIGHT * 0.50f));
        menuUI.addButton("QUIT", "quitButton", WIDTH / 4 - 256, (int) (HEIGHT * 0.60f));
    }

    /**
     * Metoda updateButtons odpowiada za włączanie stanów gry dostępnych w głównym menu, wyświetlanie informacji o grze,
     * oraz zmianę tekstur przycisków.
     * Jeśli sprawdzono, że LPM jest wciśnięty po raz pierwszy od kąd nie był naciśnięty:
          Jeśli kursor znajduje się nad przyciskiem "PLAY":
          - zmień stan gry na GAME - czyli wejdź do gry.
          Jeśli kursor znajduje się nad przyciskiem "EDIT":
          - zmień stan gry na EDITOR - czyli wejdź do gry.
          Jeśli kursor znajduje się nad przyciskiem "INFO":
          - zmień wartość logiczną zmiennej showINFO na przeciwną (sprawi to że w metodzie update zostanie wywołana
                                                                            metoda rysująca informacje o grze).
          Jeśli kursor znajduje się nad przyciskiem "QUIT":
     * - zamknij grę.
     * W przeciwnym przypadku:
     * Wywołaj metodę changeButtonTextureDependingCursorPosition na rzecz wszystkich przycisków w menu.
     * Metoda ta odpowiada za ustawianie odpowiedniej tekstury przycisków w zależności od aktualnego położenia kursora myszy.
     */
    private void updateButtons() {
        if (Mouse.isButtonDown(0) && !mouseButton1) {
            if (menuUI.isCursorOnTheButton("PLAY"))
                StateManager.setState(StateManager.GameState.GAME);
            if (menuUI.isCursorOnTheButton("EDIT"))
                StateManager.setState(StateManager.GameState.EDITOR);
            if (menuUI.isCursorOnTheButton("INFO"))
                showINFO = !showINFO;
            if (menuUI.isCursorOnTheButton("QUIT"))
                System.exit(0);
        } else {
            menuUI.changeButtonTextureDependingCursorPosition("PLAY", "playButtonYellow", "playButton");
            menuUI.changeButtonTextureDependingCursorPosition("EDIT", "editButtonYellow", "editButton");
            menuUI.changeButtonTextureDependingCursorPosition("INFO", "infoButtonYellow", "infoButton");
            menuUI.changeButtonTextureDependingCursorPosition("QUIT", "quitButtonYellow", "quitButton");
        }
        mouseButton1 = Mouse.isButtonDown(0);
    }

    /**
     * Metoda rysująca tło menu, przyciski i wykorzystująca metodę updateButtons do obsługi przycisków i akcji z nimi związanych.
     */
    public void update() {
        DrawTexture(background, 0, 0, 2048, 1024);
        menuUI.draw();
        updateButtons();
        if (showINFO)
            drawINFO();
    }

    public void drawINFO() {
        menuUI.drawString(450, 110, "Build towers, earn money by destroying enemies and win!");
        menuUI.drawString(450, 150, "To build a tower, select one that you can afford (from the menu on the right) and then click");
        menuUI.drawString(450, 190, "on the green field where you want to build it.");
        menuUI.drawString(450, 230, "By destroying one enemy you will get 5 cash as a reward.");
        menuUI.drawString(450, 270, "For every 30 kills without losing a life, you will receive an bonus 15 cash.");
        menuUI.drawString(450, 310, "If you manage to defeat the enemy and move to the next map you will get a bonus that is");
        menuUI.drawString(450, 350, "equal to 100 * number of the map.");
        menuUI.drawString(450, 390, "The cost of building a tower is displayed in the panel after you hover over it.");
        menuUI.drawString(450, 430, "In this panel you can find a lot of other information about the tower, such as:");
        menuUI.drawString(450, 470, "damage, range, rate of fire, projectile speed.");
        menuUI.drawString(450, 510, "There are 10 maps. Every map has 10 waves of enemies.");
        menuUI.drawString(450, 550, "In subsequent maps, more enemies appear and they are stronger than their predecessors.");
        menuUI.drawString(450, 590, "In game you can change the speed of it by using left and right arrow on the keyboard.");
        menuUI.drawString(450, 630, "You can edit the first map by clicking on EDIT button.");
        menuUI.drawString(450, 670, "To change tile you must chose one from menu on the right and then click on the filed where");
        menuUI.drawString(450, 710, "you want to place it.");
        menuUI.drawString(450, 790, "Good luck!");
    }
}
