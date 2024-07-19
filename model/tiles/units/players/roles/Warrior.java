package model.tiles.units.players.roles;

import  java.util.List;
import java.util.Random;

import utils.Position;
import model.tiles.Tile;
import utils.Health;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;


public class Warrior extends Player {

    private final int abilityCooldown;
    private int remainingCooldown;

    public Warrior(String name ,  int attackPoints, int defensePoints, int health, int x, int y ,
                   int abilityCooldown)
    {
        super(name,health,attackPoints,defensePoints,new Position(x,y));
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }

    public void levelUP()
    {
        super.levelUp();
        this.remainingCooldown = 0;
        this.getHealth().setCapacity(this.getHealth().getCapacity() + this.getLevel()*5);
        this.setAttack(this.getAttack() + 2 * this.getLevel());
        this.setDefense(this.getDefense() + this.getLevel());
    }

    public void activateAbility(List<Enemy> enemies)
    {
        callBack.send("Warrior " + this.getName() + " Just activated special ability Avengers Shield!");
        if(this.remainingCooldown == 0) {
            List<Enemy> enemiesInRange = enemies.stream().filter(e -> this.getPosition().range(e.getPosition()) < 3).toList();
            if (enemiesInRange.size() == 0)
                callBack.send("No enemies in Avengers shield range ");
            else
            {
                this.remainingCooldown = this.abilityCooldown;
                Enemy randomEnemy = enemiesInRange.get((new Random()).nextInt(0, enemiesInRange.size()));
                this.attackWithAbility(randomEnemy, (int) (this.getHealth().getCapacity() * 0.1));
                this.getHealth().increaseCurrentHealth(10 * this.getDefense());
            }
        }
        else
            this.callBack.send("Still have cooldown remaining to use special ability");

    }
    public void move(Tile t)
    {
        this.interact(t);
        if(this.remainingCooldown > 0)
            this.remainingCooldown--;
    }
    public String description()
    {
        return super.description() + "  Ability Cooldown : " + this.abilityCooldown + "  Remaining Cooldown : " + this.remainingCooldown;
    }
    public void info()
    {
        this.callBack.send("Warrior " +this.getName() + "\n Stats : " + this.description() + "\n");
    }

    public int getRemainingCooldown() {
        return remainingCooldown;
    }
}
