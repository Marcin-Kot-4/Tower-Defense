package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa OrthinAssaultShuttle zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class OrthinAssaultShuttle extends Enemy {

    public OrthinAssaultShuttle(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("OrthinAssaultShuttle"), grid.getTile(tileX, tileY), grid, 64, 64, 225, 2000);
    }
}
