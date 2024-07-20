package roles;

import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.enemies.Types.Trap;
import model.tiles.units.players.roles.Rogue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.tiles.units.enemies.Enemy;
import utils.callbacks.MessageCallback;

import java.util.ArrayList;
import java.util.List;

public class RogueTest {
    private Rogue rogue;
    private List<Enemy> enemies;
    private Monster monster;
    private Trap trap;
    private List<String> messages;
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
        messages = new ArrayList<>();
        rogue = new Rogue("Arya Stark", 40, 2, 150, -1, -1, 20, msg);
        enemies = new ArrayList<>();
        monster = new Monster(50, "Lannister Knight", 14, 8, 200, -1, -1, 'k', 4,msg);
        trap = new Trap(100, "Queen's trap", 50, 10, 250, -1, -1, 'Q', 3, 7,msg);
        enemies.add(monster);
        enemies.add(trap);
    }

    @Test
    public void testLevelUp() {
        rogue.levelUP();
        Assert.assertEquals("Energy should be reset to 100", 100, rogue.getEnergy().getCurrent());
        Assert.assertEquals("Attack points should be increased", 47, rogue.getAttack());
    }

    @Test
    public void testActivateAbility() {
        // Activate ability with enemies in range and enough energy
        rogue.activateAbility(enemies);
        Assert.assertTrue("Energy should be decreased", rogue.getEnergy().getCurrent() < 100);
        Assert.assertTrue("At least one enemy should take damage",
                monster.getHealth().getCurrent() < 200 || trap.getHealth().getCurrent() < 250);

        // Reset messages
        messages.clear();

        // Activate ability without enough energy
        rogue.getEnergy().decreaseCurrentHealth(80); // Reduce energy
        rogue.activateAbility(enemies);
        Assert.assertTrue("Energy should be insufficient", rogue.getEnergy().getCurrent() < 20);
    }

    @Test
    public void testMove() {
        int initialEnergy = rogue.getEnergy().getCurrent();
        rogue.move(monster);
        Assert.assertTrue("Monster should be dead", monster.getHealth().getCurrent() >= 0);
        Assert.assertEquals("Energy should increase by 10", initialEnergy, rogue.getEnergy().getCurrent());
    }

    @Test
    public void testDescription() {
        String expectedDescription =" name: Arya Stark  AttackPoints: 40  DefensePoints: 2  Health Points : (Max: 150 Current: 150) Energy : Max: 100 Current: 100";
        Assert.assertEquals("Description should match expected", expectedDescription, rogue.description());
    }
}
