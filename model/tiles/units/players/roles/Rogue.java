package model.tiles.units.players.roles;

import java.util.List;

import utils.Position;
import model.tiles.Tile;
import utils.Health;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.callbacks.MessegeCallBack;

public class Rogue extends Player {

    private Health energy;
    private int abilityCost;
    private MessegeCallBack callBack;
    public Rogue(String name ,  int attackPoints, int defensePoints, int health, int x, int y , int abilityCost)
    {
        super(name, attackPoints, defensePoints, new Health(health), new Position(x, y));
        this.energy = new Health(100);
        this.abilityCost = abilityCost;
        callBack = new MessegeCallBack();
    }

    public void levelUP()
    {
        super.levelUP();
        this.energy.setCurrent(100);
        this.setAttackPoints(this.getAttackPoints() + this.getLevel() * 3);
    }

    public void activateAbility(List<Enemy> enemies)
    {
        callBack.onMessageRecieved("Rogue " + this.getName() + " Just activated special ability Fan Of Knives!");
        if(this.energy.getCurrent() >= this.abilityCost) {
            List<Enemy> enemiesInRange = enemies.stream().filter(e -> this.getP().Distance(e.getP()) < 2).toList();
            if (enemiesInRange.size() == 0)
                callBack.onMessageRecieved("No enemies in Fan of Knives range ");
            else
            {
                this.energy.decreasBarPoints(this.abilityCost);
                for (Enemy e : enemiesInRange) {
                    this.attackWithAbility(e, this.getAttackPoints());
                }
            }
        }
        else
            this.callBack.onMessageRecieved("Not enough energy to use special ability");

    }

    public void move(Tile t)
    {
        this.interact(t);
        this.energy.increaseBarPoints(10);
    }
    public String description()
    {
        return super.description() + " Energy : " + this.energy.toString();
    }
    public void info()
    {
        this.callBack.onMessageRecieved("Rogue " +this.getName() + "\n Stats : " + this.description() + "\n");
    }
}

