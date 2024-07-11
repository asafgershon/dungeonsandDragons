package model.tiles;

import model.tiles.units.Unit;
import utils.Position;
import utils.Position;
import utils.callbacks.MessegeCallBack;

public abstract class Tile {
    protected char tile;
    protected Position position;
    private MessegeCallBack callBack;

    public Tile(char tile, Position p){
        this.tile = tile;
        this.position = p;
        this.callBack = new MessegeCallBack();
    }

    public Tile initialize(Position p){
        this.position = p;
        return this;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public char getSymbol() {
        return tile;
    }

    protected void setSymbol(char x) {
        tile = x;
    }
}
