package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa PhidiBomber zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class PhidiBomber extends Enemy {

    public PhidiBomber(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("PhidiBomber"), grid.getTile(tileX, tileY), grid, 64, 64, 150, 200);
    }
}
