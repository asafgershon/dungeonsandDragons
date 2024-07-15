package roles;

import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.enemies.Types.Trap;
import model.tiles.units.players.roles.Mage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.tiles.units.enemies.Enemy;
import java.util.ArrayList;
import java.util.List;


public class MageTest {
    private Mage mage;
    private List<Enemy> enemies;
    private Monster monster;
    private Trap trap;

    @Before
    public void initTest() {
        mage = new Mage("Melisandre", 5, 1, 100, -1, -1, 300, 15, 30, 5, 6);
        enemies = new ArrayList<>();
        monster = new Monster(50, "Lannister Knight", 14, 8, 200, -1, -1, 'k', 4);
        trap = new Trap(100, "Queen's trap", 50, 10, 250, -1, -1, 'Q', 3, 7);
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
        Assert.assertTrue("Monster should be dead", monster.getHealth().getCurrent() <= 0);
        Assert.assertEquals("Mana should increase by level amount", initialMana + 1, mage.getMana().getCurrent());
    }

    @Test
    public void testDescription() {
        String expectedDescription = "Name: Melisandre, Health: 100/100, Attack: 5, Defense: 1, Experience: 0, Level: 1, Mana: (300/300), Spell Power: 15, ability range 6";
        Assert.assertEquals("Description should match expected", expectedDescription, mage.description());
    }

}

