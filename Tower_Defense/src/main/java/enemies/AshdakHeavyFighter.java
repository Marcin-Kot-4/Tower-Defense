package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.*;

/**
 * Klasa AshdakHeavyFighter zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class AshdakHeavyFighter extends Enemy {

    public AshdakHeavyFighter(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("AshdakHeavyFighter"), grid.getTile(tileX, tileY), grid, 64, 64, 200, 600);
    }
}
