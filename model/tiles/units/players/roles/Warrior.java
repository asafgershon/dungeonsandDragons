package model.tiles.units.players.roles;
import  java.util.List;
import java.util.Random;

import utils.callbacks.MessegeCallBack;
import utils.Position;
import model.tiles.Tile;
import utils.Health;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;


public class Warrior extends Player {

    private int abilityCooldown;
    private int remainingCooldown;
    private MessegeCallBack callBack;

    public Warrior(String name ,  int attackPoints, int defensePoints, int health, int x, int y ,
                   int abilityCooldown)
    {
        super(name,attackPoints,defensePoints,new Health(health),new Position(x,y));
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
        callBack = new MessegeCallBack();
    }

    public void levelUP()
    {
        super.levelUP();
        this.remainingCooldown = 0;
        this.getHealth().setPool(this.getHealth().getPool() + this.getLevel()*5);
        this.setAttackPoints(this.getAttackPoints() + 2 * this.getLevel());
        this.setDefensePoints(this.getDefensePoints() + this.getLevel());
    }

    public void activateAbility(List<Enemy> enemies)
    {
        callBack.onMessageRecieved("Warrior " + this.getName() + " Just activated special ability Avengers Shield!");
        if(this.remainingCooldown == 0) {
            List<Enemy> enemiesInRange = enemies.stream().filter(e -> this.getP().Distance(e.getP()) < 3).toList();
            if (enemiesInRange.size() == 0)
                callBack.onMessageRecieved("No enemies in Avengers shield range ");
            else
            {
                this.remainingCooldown = this.abilityCooldown;
                Enemy randomEnemy = enemiesInRange.get((new Random()).nextInt(0, enemiesInRange.size()));
                this.attackWithAbility(randomEnemy, (int) (this.getHealth().getPool() * 0.1));
                this.getHealth().increaseBarPoints(10 * this.getDefensePoints());
            }
        }
        else
            this.callBack.onMessageRecieved("Still have cooldown remaining to use special ability");

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
        this.callBack.onMessageRecieved("Warrior " +this.getName() + "\n Stats : " + this.description() + "\n");
    }
}
