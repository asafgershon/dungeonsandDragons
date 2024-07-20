package monsterType;

import model.tiles.units.enemies.Types.Trap;
import model.tiles.units.players.Player;
import model.tiles.units.players.roles.Warrior;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Position;
import utils.callbacks.MessageCallback;

public class TrapTest {
    private Trap trap;
    private Player player;
    private MessageCallback msg;


    @Before
    public void initTest() {
        msg = new MessageCallback() {
            @Override
            public void send(String message) {
                // Implement a simple mock or use a logging mechanism
                System.out.println(message);
            }
        };
        trap = new Trap(30, "Bonus Trap", 1, 1, 1, -1, -1, 'B', 1, 5, msg);
        player = new Warrior("Jon Snow", 30, 4, 300, -1, -1, 3, msg);
    }

    @Test
    public void testVisibility() {
        // After the first gameTick, the trap should be visible
        trap.gameTick(player);
        Assert.assertEquals("Trap should be visible", "B", trap.toString());

        // After 1 more gameTick, the trap should become invisible
        trap.gameTick(player);
        Assert.assertEquals("Trap should be invisible", ".", trap.toString());

        // After a total of 6 more gameTicks, the trap should be visible again
        for (int i = 0; i < 6; i++) {
            trap.gameTick(player);
        }
        Assert.assertEquals("Trap should be visible again", ".", trap.toString());
    }

    @Test
    public void testOnDeath() {
        trap.onDeath(player, false);
        Assert.assertEquals("Player should gain EXP", 30, player.getExp());
        // Further assertions based on the onDeath behavior can be added here
    }

    @Test
    public void testDescription() {
        String expectedDescription = " name: Bonus Trap  AttackPoints: 1  DefensePoints: 1  Health Points : (Max: 1 Current: 1) exp raise : 30 visibilityTime = 1 InVisibilityTime = 5 visible = true ticksCount = 0";
        Assert.assertEquals("Description should match expected", expectedDescription, trap.description());
    }
}
