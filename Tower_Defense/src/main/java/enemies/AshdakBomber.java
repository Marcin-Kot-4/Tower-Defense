package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa AshdakBomber zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class AshdakBomber extends Enemy {

    public AshdakBomber(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("AshdakBomber"), grid.getTile(tileX, tileY), grid, 64, 64, 175, 400);
    }
}
