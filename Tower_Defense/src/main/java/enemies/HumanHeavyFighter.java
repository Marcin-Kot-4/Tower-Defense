package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

<<<<<<< HEAD
/**
 * Klasa HumanHeavyFighter zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class HumanHeavyFighter extends Enemy {

    public HumanHeavyFighter(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("HumanHeavyFighter"), grid.getTile(tileX, tileY), grid, 64, 64, 125, 600);
    }
}
