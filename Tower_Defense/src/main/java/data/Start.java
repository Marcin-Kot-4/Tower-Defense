package data;

import controllers.Time;
import controllers.StateManager;
import org.lwjgl.opengl.Display;

/** Zaimportuj wszystko z biblioteki GL11 **/

/** Zaimportuj wszystko z klasy Graphic **/
import static controllers.Graphic.*;

public class Start {

    public Start() {

        /** Wywołuje statyczną metodę klasy Graphic żeby zainicjować wywołania OpenGL */
        StartSession();

        /** Główna pętla gry */
        while (!Display.isCloseRequested()) {

            Time.Update();
            StateManager.update();
            /** Zaktualizuj okno - wyświetl to co zostało narysowane. Odpytaj klawiaturę i myszkę. **/
            Display.update();
            /** Ustaw określoną liczbę klatek na sekundę. Gra jest usypiana na dodatkowy dowolny czas w przypadku
             *  kiedy liczba FPS miałaby przekroczyć liczbę podaną w argumencie metody **/
            Display.sync(60);
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        new Start();
    }
}
