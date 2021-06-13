package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa OrthinInterceptor zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class OrthinInterceptor extends Enemy {
    public OrthinInterceptor(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("OrthinInterceptor"), grid.getTile(tileX, tileY), grid, 64, 64, 150, 400);
    }
}
