package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa AshdakSpecialAce zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class AshdakSpecialAce extends Enemy {
    public AshdakSpecialAce(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("AshdakSpecialAce"), grid.getTile(tileX, tileY), grid, 64, 64, 250, 800);
    }
}
