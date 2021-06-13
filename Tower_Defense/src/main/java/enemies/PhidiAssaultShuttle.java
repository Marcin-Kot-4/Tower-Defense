package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa PhidiAssaultShuttle zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class PhidiAssaultShuttle extends Enemy {

    public PhidiAssaultShuttle(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("PhidiAssaultShuttle"), grid.getTile(tileX, tileY), grid, 64, 64, 250, 500);
    }
}
