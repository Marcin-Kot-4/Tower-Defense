package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa OrthinHeavyFighter zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class OrthinHeavyFighter extends Enemy {
    public OrthinHeavyFighter(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("OrthinHeavyFighter"), grid.getTile(tileX, tileY), grid, 64, 64, 200, 1200);
    }
}
