package model.tiles.units.enemies.Types;

import model.tiles.units.enemies.Enemy;
import utils.callbacks.MessegeCallBack;
import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import utils.Health;
import utils.Position;
import java.util.List;




public class Trap extends Enemy {

    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;
    private MessegeCallBack callBack;
    private char defSymbol;

    public Trap(int expRaise, String name, int attackPoints, int defensePoints, int health, int x, int y, char symbol, int visibilityTime, int invisibilityTime) {
        super(expRaise, name, attackPoints, defensePoints, new Health(health),new Position(x,y), symbol);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
        this.defSymbol = symbol;
        this.callBack = new MessegeCallBack();
    }
    public void visit (Player p)
    {
    }

    public void visit(Enemy e)
    {

    }

    public void accept(Unit u)
    {
        u.visit(this);
    }
    public void onDeath(Unit killer,boolean fromAbility)
    {
        killer.gainEXP(this.getExpRaise());
        if (!fromAbility)
            killer.swapPosition(this);
        callBack.onMessageRecieved("Trap " + this.getName() + " died.");
    }
    public void gainEXP(int exp) { }

    public void gameTick(Player p)
    {
        this.visible = ticksCount < visibilityTime;

        if (visible)
            this.setSymbol(defSymbol);
        else
            this.setSymbol('.');

        if(ticksCount == visibilityTime+invisibilityTime)
            ticksCount = 0;
        else
            ticksCount ++;
        if(this.getP().Distance(p.getP()) < 2)
            this.battle(p);
    }
    public String toString()
    {
        if (visible)
            return super.toString();
        return ".";
    }
    @Override
    public String description()
    {
        return super.description() + " exp raise : " + this.getExpRaise() + " visibilityTime = " + this.visibilityTime + " InVisibilityTime = "
                + this.invisibilityTime + " visible = " + this.visible + " ticksCount = " + this.ticksCount;
    }
    public void info()
    {
        this.callBack.onMessageRecieved("Trap " + this.getName() + "\n Stats : " + this.description() + "\n");
    }
}
