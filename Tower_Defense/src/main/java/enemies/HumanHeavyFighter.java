package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa HumanHeavyFighter zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class HumanHeavyFighter extends Enemy {

    public HumanHeavyFighter(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("HumanHeavyFighter"), grid.getTile(tileX, tileY), grid, 64, 64, 125, 600);
    }
}
