package model.tiles;

import model.tiles.units.Unit;
import utils.Position;

public class Wall extends Tile {
    public static final char WALL_TILE = '#';

    public Wall(int x,int y) {
        super(WALL_TILE, new Position(x,y));
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }
}
