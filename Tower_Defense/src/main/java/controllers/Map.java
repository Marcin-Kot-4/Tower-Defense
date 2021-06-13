package controllers;

import data.Tile;
import data.TileGrid;
import data.TileType;

import java.io.*;

/**
 * Wczytywanie, zapisywanie map i funkcjonalności kafelków.
 */
public class Map {


    /**
     * Metoda saveMap przegląda kolumnami wszystkie kafelki i zapisuje ich typy do zmiennej String.
     * Szerokość i wysokość planszy w kafelkach zwraca metoda klasy TileGrid.
     * @param mapName - nazwa pliku, w którym mają zostać zapisane dane o mapie.
     * @param grid - siatka mapy, która ma zostać zapisana w pliku.
     */
    public static void SaveMap(String mapName, TileGrid grid) {
        String mapData = "";
        for (int i = 0; i < grid.getTilesWide(); i++) {
            for (int j = 0; j < grid.getTilesHigh(); j++) {
                mapData += GetTileID(grid.getTile(i, j));
            }
        }

        try {
            File file = new File("src\\main\\resources\\maps\\" + mapName); // nazwa pliku "mapName"
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(mapData); // zapis zmiennej mapData do pliku
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda LoadMap wczytuje mapę z pliku konwertując jego zawartość.
     * @param mapName - nazwa pliku zawierającego ciąg cyfr. Każda cyfra oznacza typ kafelka na mapie. Kolejność oznacza
     *                gdzie na mapie będzie się on znajdował
     * @return - zwraca obiekt reprezentujący wczytaną mapę, na której program będzie pracował.
     */
    public static TileGrid LoadMap(String mapName) {
        TileGrid grid = new TileGrid();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\maps\\" + mapName)); // otwórz plik do odczytu
            String data = br.readLine(); // zapisz dane z pliku do zmiennej data (pierwszą linię[w pierwszej linii jest cała zawartość pliku])
            for (int i = 0; i < grid.getTilesWide(); i++) {
                for (int j = 0; j < grid.getTilesHigh(); j++) {
                    // substring to metoda, która zwraca zmienną typu String będącą fragmentem innej zmiennej typu String
                    // i * grid.getTilesHigh() + j to indeks znaku określającego typ aktualnie wczytywanego kafelka
                    // i oznacza kolumnę, grid.getTilesHigh() to rozmiar kolumny, j to wiersz w kolumnie
                    // na podstawie wczytanego konkretnego znaku jest ustawiany konkretny typ konkretnego kafelka na mapie
                    grid.setTile(i, j, GetTileType(data.substring(i * grid.getTilesHigh() + j, i * grid.getTilesHigh() + j + 1)));
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grid;
    }

    /**
     * Metoda zwracająca odpowiedni kafelek na podstawie znaku przekazanego przez parametr
     * @param ID - ID typu kafelka
     * @return - typ kafelka
     */
    public static TileType GetTileType(String ID) {
        TileType type = TileType.NULL;
        switch (ID) {
            case "0":
                type = TileType.Grass;
                break;
            case "1":
                type = TileType.Road;
                break;
            case "2":
                type = TileType.Water;
                break;
            case "3":
                type = TileType.NULL;
                break;
        }
        return type;
    }

    /**
     * Metoda zwracająca ID typu kafelka przekazanego jej przez parametr.
     * @param tile - obiekt kafelek
     * @return - znak reprezentujący typ kafelka
     */
    public static String GetTileID(Tile tile) {
        String ID = "E";

        switch (tile.getType()) {
            case Grass:
                ID = "0";
                break;
            case Road:
                ID = "1";
                break;
            case Water:
                ID = "2";
                break;
            case NULL:
                ID = "3";
                break;
        }

        return ID;
    }
}
