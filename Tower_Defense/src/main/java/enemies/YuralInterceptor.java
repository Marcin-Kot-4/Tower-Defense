package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa YuralInterceptor zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia
 */
public class YuralInterceptor extends Enemy {

    public YuralInterceptor(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("YuralInterceptor"), grid.getTile(tileX, tileY), grid, 64, 64, 50, 200);
    }
}
