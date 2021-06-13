package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa PhidiInterceptor zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class PhidiInterceptor extends Enemy {

    public PhidiInterceptor(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("PhidiInterceptor"), grid.getTile(tileX, tileY), grid, 64, 64, 100, 100);
    }
}
