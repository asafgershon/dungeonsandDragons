package model.tiles.units.players;

import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import utils.Position;
import utils.callbacks.MessegeCallBack;

import java.util.List;

public abstract class Player extends Unit {
    public static final char PLAYER_TILE = '@';
    protected static final int LEVEL_REQUIREMENT = 50;
    protected static final int HEALTH_GAIN = 10;
    protected static final int ATTACK_GAIN = 4;
    protected static final int DEFENSE_GAIN = 1;

    protected int level;
    protected int experience;
    private MessegeCallBack callBack;


    public Player(String name, int hitPoints, int attack, int defense, Position p) {
        super(PLAYER_TILE, name, hitPoints, attack, defense, p);
        this.level = 1;
        this.experience = 0;
    }

    public void addExperience(int experienceValue){
        this.experience += experienceValue;
        while (experience >= levelRequirement()) {
            levelUp();
        }
    }

    public void levelUp(){
        this.experience -= levelRequirement();
        this.level++;
        int healthGain = healthGain();
        int attackGain = attackGain();
        int defenseGain = defenseGain();
        health.increaseMax(healthGain);
        health.heal();
        attack += attackGain;
        defense += defenseGain;
    }

    protected int levelRequirement(){
        return LEVEL_REQUIREMENT * level;
    }

    protected int healthGain(){
        return HEALTH_GAIN * level;
    }

    protected int attackGain(){
        return ATTACK_GAIN * level;
    }

    public abstract void activateAbility(List<Enemy> enemies);

    public void attackWithAbility(Enemy e,  int attackPoints)
    {
        if(attackPoints > e.getDefensePoints())
        {
            e.getHealth().decreasBarPoints(attackPoints - e.getDefensePoints());
            callBack.onMessageRecieved(this.getName() + " attacked " + e.getName() +
                    " with " + (attackPoints - e.getDefensePoints()) + " attack points!");
            e.info();
            if (e.isDead())
                e.onDeath(this,true);
        }
        else
            callBack.onMessageRecieved("Attack was too low to break " + e.getName() + " defense");

    }

    public abstract void info();

    public String getName() {
        return name;
    }

    public String description() {
        return " name: " + this.name + "  AttackPoints: " +
                this.attack + "  DefensePoints: " + this.defense + "  " +
                "Health Points : (" + this.health.toString() + ")";
    }

    protected int defenseGain(){
        return DEFENSE_GAIN * level;
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    public void visit(Player p){
        // Do nothing
    }

    public void visit(Enemy e){
        battle(e);
        if(!e.alive()){
            addExperience(e.experienceValue());
            if(!e.alive())
                e.onDeath(this,false);
        }
    }

    public void onDeath(Unit killer,boolean fromAbility) {
        this.setSymbol('X');
        killer.swapPosition(this);
        callBack.onMessageRecieved("Player " + this.getName() + " died.");
    }

    public void gainEXP(int exp)
    {
        callBack.onMessageRecieved("Player " + this.getName() + " just Gained " + exp + " EXP");
        while(exp > 0)
        {
            if(this.getExp() + exp >= this.level * 50)
            {
                exp = exp - (this.level * 50 - this.getExp());
                levelUp();
            }
            else
            {
                this.setExp(this.getExp()+exp);
                exp = 0;
            }
        }
        this.info();
    }

    public int getExp() {
        return experience;
    }

    public void setExp(int exp) {
        this.experience = exp;
    }
    
    public int getLevel(){
        return level;
    }
}
