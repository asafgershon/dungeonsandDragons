package model.tiles.units.players.roles;

import utils.Position;
import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import java.util.List;

public class Hunter extends Player {

    private int range;
    private int arrowsCount;
    private int ticksCount;

    public Hunter(String name ,  int attackPoints, int defensePoints, int hitPoints, int x, int y ,
                  int range)
    {
        super(name,hitPoints, attackPoints,defensePoints,new Position(x,y));
        this.range = range;
        this.arrowsCount = 10;
        this.ticksCount = 0;
    }

    public void levelUP()
    {
        this.arrowsCount += this.getLevel()*10;
        this.setAttack(this.getAttack() + (2*this.getLevel()));
        this.setDefense(this.getDefense() + this.getLevel());
        super.levelUp();
    }

    public void activateAbility(List<Enemy> enemies)
    {
        callBack.send("Hunter " + this.getName() + " Just activated special ability Shoot!");
        if(this.arrowsCount > 0)
        {
            List<Enemy> enemiesInRange = enemies.stream().filter(e -> this.getPosition().range(e.getPosition()) <= this.range).toList();
            if (enemiesInRange.size() == 0)
                callBack.send("No enemies in  Shoot range ");
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
            callBack.send("You dont have enough Arrows ");
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
        this.callBack.send("Hunter " + this.getName() + "\n Stats : " + this.description() + "\n");
    }

    public int getArrowsCount() {
        return arrowsCount;
    }

    public void setArrowsCount(int i) {
        this.arrowsCount = i;
    }
}
