package model.tiles.units.enemies.Types;

import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;
import utils.Health;
import model.tiles.Tile;
import model.tiles.units.Unit;
import utils.callbacks.MessegeCallBack;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Monster extends Enemy {

    private int vision;
    private MessegeCallBack callBack;

    public Monster(int expRaise, String name, int attackPoints, int defensePoints, int health, int x, int y, char symbol, int vision)
    {
        super(symbol, name,health,attackPoints,defensePoints,expRaise);
        this.vision = vision;
        callBack = new MessegeCallBack();
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
        killer.gainEXP(this.getExpRaise());
        if (!fromAbility)
            killer.swapPosition(this);
        callBack.onMessageRecieved("Monster " + this.getName() + " died.");
    }

    public void gainEXP(int exp)
    {

    }
    public void move(Tile t)
    {
        this.interact(t);
    }
    public String chooseDirection(Player p) {

        List<String> actions = new LinkedList<String>();
        actions.add("a");
        actions.add("w");
        actions.add("d");
        actions.add("s");

        if (this.getPosition().range(p.getPosition()) < this.vision) {
            int dx = this.getPosition().getX() - p.getPosition().getX();
            int dy = this.getPosition().getY() - p.getPosition().getY();
            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0)
                    return actions.get(0);
                else
                    return actions.get(2);
            } else {
                if (dy > 0)
                    return actions.get(1);
                else
                    return actions.get(3);
            }
        }
        else {
            Random random = new Random();
            int randomIndex = random.nextInt(actions.size());
            return (actions.get(randomIndex));
        }
    }

    @Override
    public String description() {
        return super.description() + " exp raise : " + this.getExpRaise() + " vision = " + vision;
    }
    public void info()
    {
        this.callBack.onMessageRecieved("Monster " + this.getName() + "\n Stats : " + this.description() + "\n");
    }

}
