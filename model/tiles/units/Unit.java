package model.tiles.units;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Health;
import utils.Position;
import utils.generators.FixedGenerator;
import utils.generators.Generator;
import utils.callbacks.MessegeCallBack;
import utils.generators.RandomGenerator;

public abstract class Unit extends Tile {
    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;
    protected Generator generator;
    protected MessegeCallBack callBack;

    public Unit(char tile, String name, int hitPoints, int attack, int defense,Position p) {
        super(tile, p);
        this.name = name;
        this.health = new Health(hitPoints);
        this.attack = attack;
        this.defense = defense;
        this.generator = new RandomGenerator();
        this.callBack = new MessegeCallBack();
    }

    public int attack(){
        return generator.generate(attack);
    }

    public int defend(){
        return generator.generate(defense);
    }

    public boolean alive(){
        return health.getCurrent() > 0;
    }

    public void battle(Unit enemy) {
        this.callBack.onMessageRecieved(this.getName() + " just started a fight with " + enemy.getName());
        this.info();
        enemy.info();
        int attack = this.attack();
        int defense = enemy.defend();
        if(attack > defense)
        {
            enemy.getHealth().decreaseCurrentHealth(attack - defense);
            callBack.onMessageRecieved(this.getName() + " attacked with " + (attack - defense) + " points");
            enemy.info();
        }
        else
            callBack.onMessageRecieved("Attack was too low to break " + enemy.getName() + " defense");
        if(!enemy.alive())
            enemy.onDeath(this,false);
    }

    public abstract void onDeath(Unit killer,boolean fromAbility);

    public void move(Tile t)
    {
        this.interact(t);
    }

    public abstract void info();

    public void interact(Tile t){
        t.accept(this);
    }

    public void visit(Empty e){
        swapPosition(e);
    }

    public void visit(Wall w){
        // Do nothing
    }

    public String getName() {
        return name;
    }

    public abstract void gainEXP(int exp);

    public abstract void visit(Player p);

    public abstract void visit(Enemy e);

    public String description()
    {
        return " name: " + this.name + "  AttackPoints: " +
                this.attack + "  DefensePoints: " + this.defense + "  " +
                "Health Points : (" + this.health.toString() + ")";
    }

    public Health getHealth(){
        return health;
    }

    public int getDefense(){
        return defense;
    }
    public void setDefense(int defense){
        this.defense = defense;
    }
    public int getAttack() {
        return attack;
    }

    public void setAttack(int attackPoints) {
        this.attack = attackPoints;
    }

    protected void setAttackPoints(int i) {
        this.attack = i;
    }
}
