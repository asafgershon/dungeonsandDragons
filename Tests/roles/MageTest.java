package roles;

import model.tiles.units.enemies.Enemy;
import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.players.roles.Mage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class MageTest {
    private Mage mage;
    private List<Enemy> enemies;
    private Enemy enemy1, enemy2;

    @Before
    public void initTest() {
        mage = new Mage("Gandalf", 20, 5, 100, -1, -1, 100, 30, 20, 3, 5);
        enemies = new ArrayList<>();
        enemy1 = new Monster(25, "Orc", 8, 3, 80, -1, -1, 'o', 3);
        enemy2 = new Monster(25, "Troll", 8, 3, 100, -2, -2, 't', 3);
        enemies.add(enemy1);
        enemies.add(enemy2);
    }

    @Test
    public void testLevelUp() {
        mage.levelUP();
        Assert.assertEquals("Mana capacity should be increased", 125, mage.getMana().getCapacity());
        Assert.assertEquals("Spell power should be increased", 40, mage.getSpellPower());
    }

    @Test
    public void testActivateAbility() {
        mage.activateAbility(enemies);
        Assert.assertEquals("Mana should be decreased", 80, mage.getMana().getCurrent());
        Assert.assertTrue("At least one enemy should be attacked", enemies.stream().anyMatch(e -> e.getHealth().getCurrent() < e.getHealth().getCapacity()));

        mage.getMana().setCurrent(10);
        mage.activateAbility(enemies);
        Assert.assertEquals("Mana should remain the same", 10, mage.getMana().getCurrent());
    }

    @Test
    public void testMove() {
        mage.move(enemy1);
        Assert.assertEquals("Mana should increase", 101, mage.getMana().getCurrent());
    }

    @Test
    public void testDescription() {
        String expectedDescription = "Gandalf (G) Health: 100/100 Attack: 20 Defense: 5 Level: 1 Experience: 0 Mana: (100/100) Spell Power: 30 Ability Range: 5";
        Assert.assertEquals("Description should match expected", expectedDescription, mage.description());
    }
}

