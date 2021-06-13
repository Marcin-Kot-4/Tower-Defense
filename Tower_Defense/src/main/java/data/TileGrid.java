package data;

import static controllers.Graphic.TILE_SIZE;

/**
 * Klasa siatki kafelek
 */
public class TileGrid {

    public Tile[][] map;
    private int tilesWide, tilesHigh;

    /**
     * Konstruktor bez parametrów - wypełnia planszę takimi samymi kafelkami
     */
    public TileGrid(){
        this.tilesWide = 20;
        this.tilesHigh = 15;
        map = new Tile[20][15];
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                map[i][j] = new Tile(i * TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
            }
        }
    }

    /**
     * Konstruktor wypełniający plansze trzema rodzajami terenu
     * @param newMap - dwuwymiarowa tabela, której elementy oznaczają jakim terenem i w którym miejscu na mapie
     *              ma być wypełniony odpowiadający temu polu kafelek.
     * Do zmiennych prywatnych tileWide i tilesHigh przypisz szerokość oraz wysokość mapy w kafelkach.
     */
    public TileGrid(int[][] newMap){
        this.tilesWide = newMap[0].length;
        this.tilesHigh = newMap.length;
        map = new Tile[tilesWide][tilesHigh];
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                switch (newMap[j][i]){
                    case 0:
                        map[i][j] = new Tile(i * TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
                        break;
                    case 1:
                        map[i][j] = new Tile(i * TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Road);
                        break;
                    case 2:
                        map[i][j] = new Tile(i * TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Water);
                        break;
                }
            }
        }
    }

    /**
     * Setter umożliwiający zmianę typu terenu kafelka
     * @param xCoord - współrzędne na osi x kafelka do zmiany terenu
     * @param yCoord - współrzędne na osi y kafelka do zmiany terenu
     * @param type - nowy typ terenu
     */
    public void setTile(int xCoord, int yCoord, TileType type){
        map[xCoord][yCoord] = new Tile(xCoord * TILE_SIZE, yCoord * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
    }

    /**
     * Zwraca obiekt klasy Tile (czyli kafelek) znajdujący się na współrzędnych przekazanych przez parametry metody.
     * Współrzędne te są podane jako numery indeksów tablicy dwuwymiarowej składającej się z obiektów klasy Tile.
     * Indeksy te pomnożone przez TILE_SIZE (64) to położenie kafelków na mapie w pikselach.
     * @param xPlace - współrzędne na osi x kafelka
     * @param yPlace - współrzędne na osi y kafelka
     * @return - zwraca obiekt klasy Tile (czyli kafelek) znajdujący się na współrzędnych przekazanych przez parametry
     *           metody o ile jest to pozycja na mapie. Jeśli wykracza ona poza mapę to metoda zwraca kafelek, który
     *           w metodzie FindNextCheckpoint klasy Enemy oznacza wykroczenie poza mapę podczas ustalania punktu
     *           kontrolnego.
     */
    public Tile getTile(int xPlace, int yPlace){
        if (xPlace < tilesWide && yPlace < tilesHigh && xPlace >= 0 && yPlace >= 0)
            return map[xPlace][yPlace];
        else
            return new Tile(0, 0, 0, 0, TileType.NULL);
    }

    /**
     * Metoda rysująca kafelki na planszy - są one obiektami klasy Tile.
     * Wykorzystana jest tutaj metoda klasy Tile o nazwie Draw.
     */
    public void draw(){
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                map[i][j].draw();
            }
        }
    }

    public int getTilesWide() {
        return tilesWide;
    }

    public void setTilesWide(int tilesWide) {
        this.tilesWide = tilesWide;
    }

    public int getTilesHigh() {
        return tilesHigh;
    }

    public void setTilesHigh(int tilesHigh) {
        this.tilesHigh = tilesHigh;
    }
}
