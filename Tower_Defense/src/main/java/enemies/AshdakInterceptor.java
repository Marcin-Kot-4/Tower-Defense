package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa AshdakInterceptor zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class AshdakInterceptor extends Enemy {
    public AshdakInterceptor(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("AshdakInterceptor"), grid.getTile(tileX, tileY), grid, 64, 64, 150, 200);
    }
}
