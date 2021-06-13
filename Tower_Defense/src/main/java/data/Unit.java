package data;

/**
 * Unit.java to interfejs.
 * Służy do implementacji atrybutów i funkcji obsługujących pozycję,
 * rozmiar oraz rysowanie.
 */
public interface Unit {

    public float getX();
    public float getY();
    public int getWidth();
    public int getHeight();
    public void setX(float x);
    public void setY(float y);
    public void setWidth(int width);
    public void setHeight(int height);
    public void update();
    public void draw();
}
