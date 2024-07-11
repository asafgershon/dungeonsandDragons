package model.tiles;

import model.tiles.units.Unit;
import utils.Position;

public class Empty extends Tile {
    public static final char EMPTY_TILE = '.';

    public Empty(int x,int y)
    {
        super('.',new Position(x,y));
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }
}
