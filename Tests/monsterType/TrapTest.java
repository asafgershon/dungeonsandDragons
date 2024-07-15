package monsterType;

import model.tiles.units.enemies.Types.Trap;
import model.tiles.units.players.Player;
import model.tiles.units.players.roles.Warrior;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Position;

public class TrapTest {
    private Trap trap;
    private Player player;

    @Before
    public void initTest() {
        trap = new Trap(250, "Bonus Trap", 1, 1, 1, -1, -1, 'B', 1, 5);
        player = new Warrior("Jon Snow", 30, 4, 300, -1, -1, 3);
    }

    @Test
    public void testVisibility() {
        trap.gameTick(player);
        Assert.assertTrue("Trap should be visible", trap.toString().equals("Bonus Trap (B)"));

        for (int i = 0; i < 5; i++) {
            trap.gameTick(player);
        }
        Assert.assertFalse("Trap should be invisible", trap.toString().equals("Bonus Trap (B)"));

        for (int i = 0; i < 6; i++) {
            trap.gameTick(player);
        }
        Assert.assertTrue("Trap should be visible again", trap.toString().equals("Bonus Trap (B)"));
    }

    @Test
    public void testOnDeath() {
        trap.onDeath(player, false);
        Assert.assertEquals("Player should gain EXP", 250, player.getExp());
        // Further assertions based on the onDeath behavior can be added here
    }

    @Test
    public void testDescription() {
        String expectedDescription = "Bonus Trap (B) exp raise : 250 visibilityTime = 1 InVisibilityTime = 5 visible = true ticksCount = 0";
        Assert.assertEquals("Description should match expected", expectedDescription, trap.description());
    }
}
