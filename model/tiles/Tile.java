package model.tiles;

import model.tiles.units.Unit;
import model.utils.Position;

public abstract class Tile {
    protected char tile;
    protected Position position;

    public Tile(char tile){
        this.tile = tile;
    }

    public void initialize(Position p){
        this.position = p;
    }

    public void swapPosition(Tile t) {
        Position temp = t.position;
        t.position = this.position;
        this.position = temp;
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }

    public abstract void accept(Unit unit);
}
