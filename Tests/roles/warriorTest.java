package roles;

import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.enemies.Types.Trap;
import model.tiles.units.players.roles.Warrior;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.tiles.units.enemies.Enemy;
import utils.callbacks.MessageCallback;

import java.util.ArrayList;
import java.util.List;

public class warriorTest {
    private Warrior warrior;
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
        warrior = new Warrior("Jon Snow", 30, 4, 300, -1, -1, 3,msg);
        enemies = new ArrayList<>();
        enemy1 = new Monster(25, "Lannister Solider", 8, 3, 80, -1, -1, 's', 3,msg);
        enemy2 = new Trap(250, "Bonus Trap", 1, 1, 1, -1, -1, 'B', 1, 5,msg);
        enemies.add(enemy1);
        enemies.add(enemy2);
    }

    @Test
    public void testLevelUp() {
        warrior.levelUP();
        Assert.assertEquals("Remaining cooldown should be 0", 0, warrior.getRemainingCooldown());
        Assert.assertEquals("Health capacity should be increased", 315, warrior.getHealth().getCapacity());
        Assert.assertEquals("Attack should be increased", 36, warrior.getAttack());
        Assert.assertEquals("Defense should be increased", 6, warrior.getDefense());
    }

    @Test
    public void testActivateAbility() {
        //Activate ability with enemies in range
        warrior.activateAbility(enemies);
        Assert.assertEquals("Remaining cooldown should be set to ability cooldown", 3, warrior.getRemainingCooldown());
        Assert.assertTrue("Current health should be increased", warrior.getHealth().getCurrent() > 100);

        //Activate ability without enemies in range
        List<Enemy> noEnemies = new ArrayList<>();
        warrior.activateAbility(noEnemies);
        Assert.assertEquals("Remaining cooldown should still be the same", 3, warrior.getRemainingCooldown());
    }

    @Test
    public void testMove() {
        warrior.move(enemy1);
        Assert.assertTrue("Enemy should be dead", enemy1.getHealth().getCurrent() >= 0);
        Assert.assertEquals("Remaining cooldown should decrease", 0, warrior.getRemainingCooldown());
    }

    @Test
    public void testDescription() {
        String expectedDescription = " name: Jon Snow  AttackPoints: 30  DefensePoints: 4  Health Points : (Max: 300 Current: 300)  Ability Cooldown : 3  Remaining Cooldown : 0";
        Assert.assertEquals("Description should match expected", expectedDescription, warrior.description());
    }

}
