package data;

import org.newdawn.slick.opengl.Texture;
import static controllers.Graphic.*;

<<<<<<< HEAD
/**
 * Klasa Tile zawiera m.in. metodę, która rysuje obiekt klasy Tile na ekranie,
 * metoda która zwraca pozycję na osi x oraz metodę która zwraca pozycję na osi y.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class Tile {
    private float x, y;
    private int width, height;
    private Texture texture;
    private TileType type;
    private boolean occupied; // zajętość kafelka przez wieżę

    /**
     * Konstruktor
     * @param x - pozycja na osi x, w której ma znajdować się lewy górny róg czworokąta
     * @param y - pozycja na osi y, w której ma znajdować się lewy górny róg czworokąta
     * @param width - szerokość czworokąta
     * @param height - wysokość czworokąta
     * @param type - obiekt typu wyliczeniowego reprezentujący typ terenu
     */
    public Tile(float x, float y, int width, int height, TileType type){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.texture = FastLoad(type.textureName);
        if (type.buildable)
            occupied = false;
        else
            occupied = true;
    }

    /**
     * Metoda rysująca obiekt klasy Tile na ekranie.
     * Wykorzystuje do tego statyczną metodę DrawQuadTexture klasy Graphic.
     */
    public void draw(){
        DrawTexture(texture, x, y, width, height);
    }

    public int getXPlace(){
        return (int) x / TILE_SIZE;
    }

    public int getYPlace(){
        return (int) y / TILE_SIZE;
    }

    /**
     * @return x - zwraca pozycję na osi x, w której znajduje się lewy górny róg czworokąta
     */
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return y - zwraca pozycję na osi y, w której znajduje się lewy górny róg czworokąta
     */
    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean getOccupied(){
        return  occupied;
    }

    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }
}
