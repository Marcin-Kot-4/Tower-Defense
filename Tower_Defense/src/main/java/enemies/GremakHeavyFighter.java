package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa GremakHeavyFighter zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class GremakHeavyFighter extends Enemy {

    public GremakHeavyFighter(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("GremakHeavyFighter"), grid.getTile(tileX, tileY), grid, 64, 64, 175, 450);
    }
}
