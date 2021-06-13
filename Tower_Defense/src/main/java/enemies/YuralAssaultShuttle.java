package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa YuralAssaultShuttle zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class YuralAssaultShuttle extends Enemy {

    public YuralAssaultShuttle(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("YuralAssaultShuttle"), grid.getTile(tileX, tileY), grid, 64, 64, 125, 1000);
    }
}
