package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.*;

/**
 * Klasa AshdakAssaultShuttle zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class AshdakAssaultShuttle extends Enemy {

    public AshdakAssaultShuttle(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("AshdakAssaultShuttle"), grid.getTile(tileX, tileY), grid, 64, 64, 225, 1000);
    }
}
