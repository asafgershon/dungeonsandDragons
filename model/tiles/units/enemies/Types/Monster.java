package model.tiles.units.enemies.Types;

import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;
import model.tiles.Tile;
import model.tiles.units.Unit;
import utils.callbacks.MessageCallback;
import java.util.Random;

public class Monster extends Enemy {

    private int vision;

    public Monster(int expRaise, String name, int attackPoints, int defensePoints, int health, int x, int y, char symbol, int vision, MessageCallback callback)
    {
        super(symbol, name,health,attackPoints,defensePoints,expRaise, new Position(x,y),callback);
        this.vision = vision;
    }

    public void visit (Player p)
    {
        this.battle(p);
    }

    public void visit(Enemy e)
    {
        /*e.visit(this);*/
    }
    public void accept(Unit u)
    {
        u.visit(this);
    }
    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public void onDeath(Unit killer,boolean fromAbility)
    {
        killer.addExperience(this.getExpRaise());
        if (!fromAbility)
            killer.swapPosition(this);
        callBack.send("Monster " + this.getName() + " died.");
    }

    @Override
    public void addExperience(int experience) { }

    public void move(Tile t)
    {
        this.interact(t);
    }
    public String chooseDirection(Player p) {
        Position playerPosition = p.getPosition();
        int dx = playerPosition.getX() - position.getX();
        int dy = playerPosition.getY() - position.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance < vision) {
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
                    return "d"; // Move right
                } else {
                    return "a"; // Move left
                }
            } else {
                if (dy > 0) {
                    return "s"; // Move down
                } else {
                    return "w"; // Move up
                }
            }
        }
        else {
            String[] directions = {"a", "d", "w", "s", "stay"};
            return directions[new Random().nextInt(directions.length)];
        }
    }

    @Override
    public String description() {
        return super.description() + " exp raise : " + this.getExpRaise() + " vision = " + vision;
    }
    public void info()
    {
        this.callBack.send("Monster " + this.getName() + "\n Stats : " + this.description() + "\n");
    }

}