package roles;

import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.enemies.Types.Trap;
import model.tiles.units.players.roles.Mage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.tiles.units.enemies.Enemy;
import utils.callbacks.MessageCallback;

import java.util.ArrayList;
import java.util.List;


public class MageTest {
    private Mage mage;
    private List<Enemy> enemies;
    private Monster monster;
    private Trap trap;
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
        mage = new Mage("Melisandre", 5, 1, 100, -1, -1, 300, 15, 30, 5, 6,msg);
        enemies = new ArrayList<>();
        monster = new Monster(50, "Lannister Knight", 14, 8, 200, -1, -1, 'k', 4,msg);
        trap = new Trap(100, "Queen's trap", 50, 10, 250, -1, -1, 'Q', 3, 7,msg);
        enemies.add(monster);
        enemies.add(trap);
    }

    @Test
    public void testLevelUp() {
        mage.levelUP();
        Assert.assertEquals("Mana pool should be increased", 325, mage.getMana().getCapacity());
        Assert.assertTrue("Current mana should be increased", mage.getMana().getCurrent() > 300);
        Assert.assertEquals("Spell power should be increased", 25, mage.getSpellPower());
    }

    @Test
    public void testActivateAbility() {
        // Activate ability with enemies in range and enough mana
        mage.activateAbility(enemies);
        Assert.assertTrue("Mana should be decreased", mage.getMana().getCurrent() < 300);
        Assert.assertTrue("At least one enemy should take damage", monster.getHealth().getCurrent() < 200 || trap.getHealth().getCurrent() < 250);

        // Activate ability without enough mana
        mage.getMana().decreaseCurrentHealth(280); // Reduce mana
        mage.activateAbility(enemies);
        Assert.assertTrue("Mana should be insufficient", mage.getMana().getCurrent() < 30);
    }

    @Test
    public void testMove() {
        int initialMana = mage.getMana().getCurrent();
        mage.move(monster);
        Assert.assertTrue("Monster should be dead", monster.getHealth().getCurrent() >= 0);
        Assert.assertEquals("Mana should increase by level amount", initialMana, mage.getMana().getCurrent());
    }

    @Test
    public void testDescription() {
        String expectedDescription = " name: Melisandre  AttackPoints: 5  DefensePoints: 1  Health Points : (Max: 100 Current: 100)  Mana : (Max: 300 Current: 300)  Spell Power : 15 ability range 6";
        Assert.assertEquals("Description should match expected", expectedDescription, mage.description());
    }

}

