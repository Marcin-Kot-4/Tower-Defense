package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa OrthinBomber zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class OrthinBomber extends Enemy {

    public OrthinBomber(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("OrthinBomber"), grid.getTile(tileX, tileY), grid, 64, 64, 175, 800);
    }
}
