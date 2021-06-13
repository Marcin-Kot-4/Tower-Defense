package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa PhidiHeavyFighter zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class PhidiHeavyFighter extends Enemy {

    public PhidiHeavyFighter(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("PhidiHeavyFighter"), grid.getTile(tileX, tileY), grid, 64, 64, 200, 300);
    }
}
