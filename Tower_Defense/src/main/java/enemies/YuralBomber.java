package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa YuralBomber zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class YuralBomber extends Enemy {

    public YuralBomber(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("YuralBomber"), grid.getTile(tileX, tileY), grid, 64, 64, 75, 400);
    }
}
