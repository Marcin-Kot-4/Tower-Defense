package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

<<<<<<< HEAD
/**
 * Klasa AshdakSpecialAce zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
=======
>>>>>>> 5c362d4da769c490fc611112221bceb8bf02bb47
public class AshdakSpecialAce extends Enemy {
    public AshdakSpecialAce(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("AshdakSpecialAce"), grid.getTile(tileX, tileY), grid, 64, 64, 250, 800);
    }
}
