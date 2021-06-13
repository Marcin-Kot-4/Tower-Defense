package enemies;

import data.Enemy;
import data.TileGrid;

import static controllers.Graphic.FastLoad;

/**
 * Klasa HumanBomber zawiera konstruktor który dla przeciwnika
 * o tej nazwie ustawia m.in. wygląd, prędkość poruszania się i ilość zdrowia.
 */
public class HumanBomber extends Enemy {

    public HumanBomber(int tileX, int tileY, TileGrid grid) {
        super(FastLoad("HumanBomber"), grid.getTile(tileX, tileY), grid, 64, 64, 100, 400);
    }
}
