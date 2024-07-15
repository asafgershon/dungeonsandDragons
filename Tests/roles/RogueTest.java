package roles;

import model.tiles.units.enemies.Enemy;
import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.players.roles.Rogue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class RogueTest {
    private Rogue rogue;
    private List<Enemy> enemies;
    private Enemy enemy1, enemy2;

    @Before
    public void initTest() {
        rogue = new Rogue("Arya Stark", 25, 4, 100, -1, -1, 3);
        enemies = new ArrayList<>();
        enemy1 = new Monster(25, "Orc", 8, 3, 80, -1, -1, 'o', 3);
        enemy2 = new Monster(25, "Troll", 8, 3, 100, -2, -2, 't', 3);
        enemies.add(enemy1);
        enemies.add(enemy2);
    }

    @Test
    public void testLevelUp() {
        rogue.levelUP();
        Assert.assertEquals("Energy should be increased", 100, rogue.getEnergy());
    }

    @Test
    public void testActivateAbility() {
        rogue.activateAbility(enemies);
        Assert.assertEquals("Energy should be decreased", 80, rogue.getEnergy());
        Assert.assertTrue("At least one enemy should be attacked", enemies.stream().anyMatch(e -> e.getHealth().getCurrent() < e.getHealth().getCapacity()));

        rogue.setEnergy(10);
        rogue.activateAbility(enemies);
        Assert.assertEquals("Energy should remain the same", 10, rogue.getEnergy());
    }

    @Test
    public void testMove() {
        rogue.move(enemy1);
        Assert.assertEquals("Energy should increase", 83, rogue.getEnergy());
    }

    @Test
    public void testDescription() {
        String expectedDescription = "Arya Stark (R) Health: 100/100 Attack: 25 Defense: 4 Level: 1 Experience: 0 Energy: 83/100";
        Assert.assertEquals("Description should match expected", expectedDescription, rogue.description());
    }
}

