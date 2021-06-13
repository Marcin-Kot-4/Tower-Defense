package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa HumanAssaultShuttle zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class HumanAssaultShuttle extends Enemy {

    public HumanAssaultShuttle(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("HumanAssaultShuttle"), grid.getTile(tileX, tileY), grid, 64, 64, 150, 1000);
    }
}
