package model.tiles.units.players.roles;

import java.util.List;

import utils.Position;
import model.tiles.Tile;
import utils.Health;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;

public class Rogue extends Player {

    private final int startEnergy = 100;
    private Health energy;
    private int abilityCost;

    public Rogue(String name ,  int attackPoints, int defensePoints, int health, int x, int y , int abilityCost)
    {
        super(name,health, attackPoints, defensePoints, new Position(x, y));
        this.energy = new Health(startEnergy);
        this.abilityCost = abilityCost;
    }

    public void levelUP()
    {
        super.levelUp();
        this.energy.setCurrent(startEnergy);
        this.setAttackPoints(this.getAttack() + this.getLevel() * 3);
    }

    public void activateAbility(List<Enemy> enemies)
    {
        callBack.send("Rogue " + this.getName() + " Just activated special ability Fan Of Knives!");
        if(this.energy.getCurrent() >= this.abilityCost) {
            List<Enemy> enemiesInRange = enemies.stream().filter(e -> this.getPosition().range(e.getPosition()) < 2).toList();
            if (enemiesInRange.size() == 0)
                callBack.send("No enemies in Fan of Knives range ");
            else
            {
                this.energy.decreaseCurrentHealth(this.abilityCost);
                for (Enemy e : enemiesInRange) {
                    this.attackWithAbility(e, this.getAttack());
                }
            }
        }
        else
            this.callBack.send("Not enough energy to use special ability");

    }

    public void move(Tile t)
    {
        this.interact(t);
        this.energy.increaseCurrentHealth(10);
    }
    public String description()
    {
        return super.description() + " Energy : " + this.energy.toString();
    }
    public void info()
    {
        this.callBack.send("Rogue " +this.getName() + "\n Stats : " + this.description() + "\n");
    }

    public Health getEnergy() {
        return energy;
    }
}

