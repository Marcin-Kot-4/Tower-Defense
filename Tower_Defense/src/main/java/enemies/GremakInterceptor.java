package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa GremakInterceptor zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class GremakInterceptor extends Enemy {

    public GremakInterceptor(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("GremakInterceptor"), grid.getTile(tileX, tileY), grid, 64, 64, 125, 150);
    }
}

