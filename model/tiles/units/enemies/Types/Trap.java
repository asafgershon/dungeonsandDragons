package model.tiles.units.enemies.Types;

import model.tiles.units.enemies.Enemy;
import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.MessageCallback;

public class Trap extends Enemy {

    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;
    private char defSymbol;

    public Trap(int expRaise, String name, int attackPoints, int defensePoints, int health, int x, int y, char symbol, int visibilityTime, int invisibilityTime, MessageCallback callBack) {
        super(symbol, name,health, attackPoints, defensePoints,expRaise,new Position(x,y),callBack);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
        this.defSymbol = symbol;
    }
    public void visit (Player p)
    {
    }

    public void addExperience(int experience) { }


    public void visit(Enemy e)
    {

    }

    public void accept(Unit u)
    {
        u.visit(this);
    }

    public void onDeath(Unit killer,boolean fromAbility)
    {
        killer.addExperience(this.getExpRaise());
        if (!fromAbility)
            killer.swapPosition(this);
        callBack.send("Trap " + this.getName() + " died.");
    }

    public void gainEXP(int exp) { }

    public void gameTick(Player p)
    {
        // Update visibility based on ticksCount
        if (ticksCount < visibilityTime) {
            this.visible = true;
        } else if (ticksCount < visibilityTime + invisibilityTime) {
            this.visible = false;
        } else {
            // Reset the ticksCount and start the visibility cycle over
            ticksCount = 0;
        }

        // Update the trap's symbol based on visibility
        if (this.visible) {
            this.setSymbol(defSymbol);
        } else {
            this.setSymbol('.');
        }

        // Increment the ticks count
        ticksCount++;

        // Check distance to player and trigger battle if within range
        if (this.getPosition().range(p.getPosition()) < 2) {
            this.battle(p);
        }
    }
    public String toString()
    {
        if (this.visible) {
            return super.toString(); // This should return the symbol for visible traps
        }
        return "."; // Return symbol for invisible traps
    }
    @Override
    public String description()
    {
        return super.description() + " exp raise : " + this.getExpRaise() + " visibilityTime = " + this.visibilityTime + " InVisibilityTime = "
                + this.invisibilityTime + " visible = " + this.visible + " ticksCount = " + this.ticksCount;
    }
    public void info()
    {
        this.callBack.send("Trap " + this.getName() + "\n Stats : " + this.description() + "\n");
    }
}
