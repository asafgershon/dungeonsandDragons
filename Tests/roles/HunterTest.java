package roles;

import model.tiles.units.enemies.Enemy;
import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.players.roles.Hunter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Position;
import utils.callbacks.MessageCallback;

import java.util.ArrayList;
import java.util.List;

public class HunterTest {
    private Hunter hunter;
    private List<Enemy> enemies;
    private Enemy enemy1, enemy2;
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
        hunter = new Hunter("Legolas", 30, 4, 100, -1, -1, 5,msg);
        enemies = new ArrayList<>();
        enemy1 = new Monster(25, "Orc", 8, 3, 80, -1, -1, 'o', 3,msg);
        enemy2 = new Monster(25, "Troll", 8, 3, 100, -2, -2, 't', 3,msg);
        enemies.add(enemy1);
        enemies.add(enemy2);
    }

    @Test
    public void testLevelUp() {
        hunter.levelUP();
        Assert.assertEquals("Arrows count should be increased", 20, hunter.getArrowsCount());
        Assert.assertEquals("Attack should be increased", 36, hunter.getAttack());
        Assert.assertEquals("Defense should be increased", 6, hunter.getDefense());
    }

    @Test
    public void testActivateAbility() {
        hunter.activateAbility(enemies);
        Assert.assertEquals("Arrows count should be decreased", 9, hunter.getArrowsCount());
        Assert.assertTrue("At least one enemy should be attacked", enemies.stream().anyMatch(e -> e.getHealth().getCurrent() < e.getHealth().getCapacity()));

        hunter.setArrowsCount(0);
        hunter.activateAbility(enemies);
        Assert.assertEquals("Arrows count should remain 0", 0, hunter.getArrowsCount());
    }

    @Test
    public void testMove() {
        hunter.move(enemy1);
        Assert.assertEquals("Arrows count should remain the same", 10, hunter.getArrowsCount());

        for (int i = 0; i < 10; i++) {
            hunter.move(enemy1);
        }
        Assert.assertEquals("Arrows count should increase", 24, hunter.getArrowsCount());
    }

    @Test
    public void testDescription() {
        String expectedDescription = " name: Legolas  AttackPoints: 30  DefensePoints: 4  Health Points : (Max: 100 Current: 100)  Range : 5 Arrows Count : 10 TicksCount : 0";
        Assert.assertEquals("Description should match expected", expectedDescription, hunter.description());
    }
}