package roles;

import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.enemies.Types.Trap;
import model.tiles.units.players.roles.Rogue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.tiles.units.enemies.Enemy;


import java.util.ArrayList;
import java.util.List;

public class RogueTest {
    private Rogue rogue;
    private List<Enemy> enemies;
    private Monster monster;
    private Trap trap;

    @Before
    public void initTest() {
        rogue = new Rogue("Arya Stark", 40, 2, 150, -1, -1, 20);
        enemies = new ArrayList<>();
        monster = new Monster(50, "Lannister Knight", 14, 8, 200, -1, -1, 'k', 4);
        trap = new Trap(100, "Queen's trap", 50, 10, 250, -1, -1, 'Q', 3, 7);
        enemies.add(monster);
        enemies.add(trap);
    }

    @Test
    public void testLevelUp() {
        rogue.levelUP();
        Assert.assertEquals("Energy should be reset to 100", 100, rogue.getEnergy().getCurrent());
        Assert.assertEquals("Attack points should be increased", 43, rogue.getAttack());
    }

    @Test
    public void testActivateAbility() {
        // Activate ability with enemies in range and enough energy
        rogue.activateAbility(enemies);
        Assert.assertTrue("Energy should be decreased", rogue.getEnergy().getCurrent() < 100);
        Assert.assertTrue("At least one enemy should take damage", monster.getHealth().getCurrent() < 200 || trap.getHealth().getCurrent() < 250);

        // Activate ability without enough energy
        rogue.getEnergy().decreaseCurrentHealth(80); // Reduce energy
        rogue.activateAbility(enemies);
        Assert.assertTrue("Energy should be insufficient", rogue.getEnergy().getCurrent() < 20);
    }

    @Test
    public void testMove() {
        int initialEnergy = rogue.getEnergy().getCurrent();
        rogue.move(monster);
        Assert.assertTrue("Monster should be dead", monster.getHealth().getCurrent() <= 0);
        Assert.assertEquals("Energy should increase by 10", initialEnergy + 10, rogue.getEnergy().getCurrent());
    }

    @Test
    public void testDescription() {
        String expectedDescription = "Name: Arya Stark, Health: 150/150, Attack: 40, Defense: 2, Experience: 0, Level: 1, Energy: (100/100)";
        Assert.assertEquals("Description should match expected", expectedDescription, rogue.description());
    }

}

