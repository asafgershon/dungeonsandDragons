package model.tiles.units.players.roles;

import utils.Position;
import model.tiles.Tile;
import utils.Health;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.callbacks.MessegeCallBack;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Mage extends Player {

    private Health mana;
    private int spellPower;
    private int abilityRange;
    private int abilityCost;
    private int hitsCount;

    private MessegeCallBack callBack;

    public Mage(String name ,  int attackPoints, int defensePoints, int health, int x, int y ,
                int manaPool , int spellPower , int abilityCost , int hitsCount , int abilityRange)
    {
        super(name,attackPoints,defensePoints,new Health(health),new Position(x,y));
        this.mana = new Health(manaPool);
        this.spellPower = spellPower;
        this.abilityRange = abilityRange;
        this.abilityCost = abilityCost;
        this.hitsCount = hitsCount;
        callBack = new MessegeCallBack();
    }

    public void levelUP()
    {
        super.levelUp();
        this.mana.tPool(mana.getPool() + this.getLevel()*25);
        this.mana.increaseCurrent(this.mana.getPool()/4);
        this.spellPower = this.spellPower + this.getLevel() * 10;
    }

    public void activateAbility(List<Enemy> enemies)
    {
        callBack.onMessageRecieved("Mage " + this.getName() + " Just activated special ability Blizzard!");
        if(this.mana.getCurrent() >= this.abilityCost)
        {
            List<Enemy> enemiesInRange = new LinkedList<Enemy>();
            enemiesInRange = enemies.stream().filter(e -> this.getPosition().range(e.getPosition()) <= this.abilityRange).collect(Collectors.toList());
            if (enemiesInRange.size() == 0)
                callBack.onMessageRecieved("No enemies in Blizzard Range");
            else
            {
                this.mana.decreasBarPoints(this.abilityCost);
                for (int i = 1; i <= this.hitsCount && enemiesInRange.size() > 0; i++) {
                    int index = (new Random()).nextInt(0, enemiesInRange.size());
                    Enemy randomEnemy = enemiesInRange.get(index);
                    if (randomEnemy.alive()) {
                        this.attackWithAbility(randomEnemy, this.spellPower);
                    }
                    else
                        enemiesInRange.remove(index);
                }
            }
        }
        else
            this.callBack.onMessageRecieved("Not enough mana to use special ability");
    }

    public void move(Tile t)
    {
        this.interact(t);
        this.mana.increaseCurrentHealth(this.getLevel());
    }
    public String description()
    {
        return super.description() + "  Mana : (" + mana.toString() + ")  Spell Power : " + this.spellPower + " ability range " + this.abilityRange;
    }
    public void info()
    {
        this.callBack.onMessageRecieved("Mage " + this.getName() + "\n Stats : " + this.description() + "\n");
    }
}
