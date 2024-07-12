package model.tiles.units.players.roles;

import utils.Position;
import model.tiles.Tile;
import utils.Health;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.callbacks.MessegeCallBack;

import java.util.List;
import java.util.Random;

public class Hunter extends Player {

    private int range;
    private int arrowsCount;
    private int ticksCount;
    private MessegeCallBack callBack;

    public Hunter(String name ,  int attackPoints, int defensePoints, int hitPoints, int x, int y ,
                  int range)
    {
        super(name,hitPoints, attackPoints,defensePoints,new Position(x,y));
        this.range = range;
        this.arrowsCount = 10;
        this.ticksCount = 0;
        callBack = new MessegeCallBack();
    }

    public void levelUP()
    {
        super.levelUp();
        this.arrowsCount += this.getLevel()*10;
        this.setAttack(this.getAttack() + (2*this.getLevel()));
        this.setDefense(this.getDefense() + this.getLevel());
    }

    public void activateAbility(List<Enemy> enemies)
    {
        callBack.onMessageRecieved("Hunter " + this.getName() + " Just activated special ability Shoot!");
        if(this.arrowsCount > 0)
        {
            List<Enemy> enemiesInRange = enemies.stream().filter(e -> this.getPosition().range(e.getPosition()) <= this.range).toList();
            if (enemiesInRange.size() == 0)
                callBack.onMessageRecieved("No enemies in  Shoot range ");
            else
            {
                this.arrowsCount--;
                Enemy closest = enemies.get(0);
                for(Enemy e : enemiesInRange)
                {
                    if(this.getPosition().range(e.getPosition()) < this.getPosition().range(closest.getPosition()))
                        closest = e;
                }
                this.attackWithAbility(closest,this.getAttack());
            }
        }
        else
            callBack.onMessageRecieved("You dont have enough Arrows ");
    }
    public void move(Tile t)
    {
        this.interact(t);
        if(this.ticksCount == 10)
        {
            this.arrowsCount += this.getLevel();
            this.ticksCount = 0;
        }
        else
            this.ticksCount++;
    }
    public String description()
    {
        return super.description() + "  Range : " + this.range + " Arrows Count : " + this.arrowsCount + " TicksCount : " + this.ticksCount;
    }
    public void info()
    {
        this.callBack.onMessageRecieved("Hunter " + this.getName() + "\n Stats : " + this.description() + "\n");
    }
}
