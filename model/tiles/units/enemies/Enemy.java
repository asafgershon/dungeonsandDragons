package model.tiles.units.enemies;

import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import utils.Position;

public abstract class Enemy extends Unit {
    protected int experienceValue;

    public Enemy(char tile, String name, int hitPoints, int attack, int defense, int experienceValue, Position p) {
        super(tile, name, hitPoints, attack, defense, p);
        this.experienceValue = experienceValue;
    }

    public int experienceValue() {
        return experienceValue;
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    public abstract void onDeath(Unit killer,boolean fromAbility);
    public abstract void info();

    public void visit(Enemy e){
        // Do nothing
    }

    public void visit(Player p){
        battle(p);
        if (!p.alive()){
            if(!p.alive())
                p.onDeath(this,false);
        }
    }

    public int getExpRaise()
    {
        return this.experienceValue;
    }

}
