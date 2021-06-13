package data;

/**
 * Typ wyliczeniowy zawierający rodzaje terenu
 */
public enum TileType {

    Grass("grass", true),
    Road("road", false),
    Water("water", false),
    NULL("water", false);

    public String textureName;
    public boolean buildable;

    /**
     * Konstruktor przyjmujący dwa parametry
     * @param textureName - nazwa pliku
     * @param buildable - możliwość budowania na tym terenie
     */
    TileType(String textureName, boolean buildable){
        this.textureName = textureName;
        this.buildable = buildable;
    }
}
