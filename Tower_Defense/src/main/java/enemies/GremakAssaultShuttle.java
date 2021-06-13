package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa GremakAssaultShuttle zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class GremakAssaultShuttle extends Enemy {

    public GremakAssaultShuttle(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("GremakAssaultShuttle"), grid.getTile(tileX, tileY), grid, 64, 64, 200, 750);
    }
}
