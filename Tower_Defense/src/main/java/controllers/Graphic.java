package controllers;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;

<<<<<<< HEAD
/**
 * Klasa Graphic m.in. ustawia tytuł okna „Tower Defense”, sprawdza czy wystąpiła kolizja dwóch
 * obiektów, rysuje teksture czworokąta w podanych współrzędnych, zwraca obiekty klasy Texture,
 * czy też oblicza kąt o jaki pocisk/rakieta musi się obrócić aby wycelować w przeciwnika.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class Graphic {
    public static final int WIDTH = 1472, HEIGHT = 960;
    public static final int TILE_SIZE = 64;

    public static void StartSession(){
        /** Ustaw tytuł okna "Tower Defense" */
        Display.setTitle("Tower Defense");
        try {
            /** Ustaw rozmiar wyświetlanego okna */
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            /** Stwórz natywne okno **/
            Display.create();
        }
        /** Zwróć wyjątek jeśli nie można ustawić trybu wyświetlania okna lub go stworzyć */
        catch (LWJGLException e) {
            e.printStackTrace();
        }

        /** Ustaw orthographic projection (czyli reprezentację trójwymiarowych obiektów w dwóch wymiarach)
         *  pomiędzy 0 i 600 na osi X, pomiędzy 400 i 0 na osi Y
         *  oraz pomiędzy 1 i -1 na osi Z określającej odległość od renderowanego obiektu */
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        /** Umożliwia umieszczanie tekstur na ekranie */
        glEnable(GL_TEXTURE_2D);
        /** Pozwala na blendowanie tła na kanale alfa - czyli przezroczystość obrazu */
        glEnable(GL_BLEND);
        /** Ustawienia dla kanału alfa i sposobu blendowania */
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Metoda sprawdzająca czy wystąpiła kolizja dwóch obiektów.
     * @param x1 - współrzędne pierwszego obiektu na osi x
     * @param y1 - współrzędne pierwszego obiektu na osi y
     * @param width1 - szerokość pierwszego obiektu
     * @param height1 - wysokość pierwszego obiektu
     * @param x2 - współrzędne drugiego obiektu na osi x
     * @param y2 - współrzędne drugiego obiektu na osi y
     * @param width2 - szerokość drugiego obiektu
     * @param height2 - wysokość drugiego obiektu
     * @return - jeśli wystąpiła kolizja zwraca true. W przeciwnym przypadku zwraca false.
     */
    public static boolean CheckCollision(float x1, float y1, float width1, float height1,
                                         float x2, float y2, float width2, float height2){
        if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2)
            return true;
        return false;
    }

    /**
     * Metoda rysująca tekstury czworokąta w podanych współrzędnych <-(będzie się tam znajdował jego lewy górny róg)
     *  o podanych rozmiarach i teksturze
     * @param texture - obiekt klasy Texture - wczytana tekstura z pliku
     * @param x - pozycja na osi x, w której ma się znajdować lewy górny róg czworokąta
     * @param y - pozycja na osi y, w której ma się znajdować lewy górny róg czworokąta
     * @param width - szerokość prostokąta
     * @param height - wysokość prostokąta
     */
    public static void DrawTexture(Texture texture, float x, float y, float width, float height){
        /** Powiąż określony kontekst GL z teksturą **/
        texture.bind();
        /** Translacja współrzędnych czworokąta względem okna na współrzędne poszczególnych wierzchołków względem siebie
         *  lewy górny: x = 0, y = 0         |        prawy górny: x = 1, y = 0
         *  lewy dolny: x = 0, y = 1         |        prawy dolny: x = 1, y = 1      **/
        glTranslatef(x, y, 0);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        /** Zapobieganie przed "rozrywaniem ekranu" - ang. screen tearing **/
        glLoadIdentity();
    }

    /**
     * Obsługuje tekstury, które mają się obracać wokół własnej osi.
     * Metoda rysująca tekstury czworokąta względem podanych współrzędnych
     *  o podanych rozmiarach i teksturze uwzględniając obrót wokół środka tego czworokąta.
     * @param texture - obiekt klasy Texture - wczytana tekstura z pliku
     * @param x - pozycja na osi x, w której ma się znajdować lewy górny róg czworokąta
     * @param y - pozycja na osi y, w której ma się znajdować lewy górny róg czworokąta
     * @param width - szerokość prostokąta
     * @param height - wysokość prostokąta
     * @param angle - kąt o jaki należy obrócić czworokąt
     */
    public static void DrawRotatedTexture(Texture texture, float x, float y, float width, float height, float angle){
        /** Powiąż określony kontekst GL z teksturą **/
        texture.bind();
        /** Translacja współrzędnych czworokąta względem okna na współrzędne poszczególnych wierzchołków względem siebie
            (wybranie środka czworokąta)
         **/
        glTranslatef(x + width / 2, y + height / 2, 0);
        /**
         * Obrót czworokąta
         */
        glRotatef(angle, 0, 0, 1);
        /** Translacja współrzędnych czworokąta względem okna na współrzędne poszczególnych wierzchołków względem siebie
            (wybranie lewego górnego rogu czworokąta)
         **/
        glTranslatef(- width / 2, - height / 2, 0);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        /** Zapobieganie przed "rozrywaniem ekranu" - ang. screen tearing **/
        glLoadIdentity();
    }

    /**
     * Metoda zwracająca obiekt klasy Texture - teksturę pobraną z pliku z podanej ścieżki o podanym typie pliku
     *  IOException - obsługa wyjątku metody LoadTexture klasy Texture
     * @param path - ścieżka do pliku
     * @param fileType - typ pliku
     * @return - obiekt klasy Texture - wczytana tekstura z pliku
     */
    public static Texture LoadTexture(String path, String fileType) {
        Texture texture = null;
        InputStream in = ResourceLoader.getResourceAsStream(path);
        try {
            texture = TextureLoader.getTexture(fileType, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return texture;
    }

    /**
     * Metoda automatyzująca część wprowadzania ścieżki i typu pliku wykorzystywanego jako tekstura.
     *  Dzięki niej wystarczy podać nazwę pliku który chcemy załadować jako teksturę.
     *  Zwraca obiekt klasy Texture.
     * @param name - nazwa pliku
     * @return - obiekt klasy Texture - wczytana tekstura z pliku
     */
    public static Texture FastLoad(String name){
        Texture texture = null;
        texture = LoadTexture("src/main/resources/img/" + name + ".png", "PNG");
        return texture;
    }

    /**
     * Metoda obliczająca kąt o jaki pocisk/rakieta musi się obrócić aby wycelować w przeciwnika.
     * atan2 to statyczna metoda obiektu Math, która oblicza kąt w radianach na podstawie współrzędnych
     * (z obrotem w kierunku przeciwnym do ruchu wskazówek zegara).
     * toDegrees konwertuje kąt w radianach na przybliżony kąt w stopniach.
     * @return - zwraca kąt w radianach o jaki należy obrócić teksturę.
     */
    public static float CalculateAngle(float x, float y, float targetX, float targetY) {
        double angleTemp = Math.atan2(targetY - y, targetX - x);
        return (float) Math.toDegrees(angleTemp) - 90;
    }
}
