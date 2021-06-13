package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa YuralHeavyFighter zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class YuralHeavyFighter extends Enemy {

    public YuralHeavyFighter(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("YuralHeavyFighter"), grid.getTile(tileX, tileY), grid, 64, 64, 100, 600);
    }
}
