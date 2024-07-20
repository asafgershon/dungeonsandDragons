package monsterType;

import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.players.Player;
import model.tiles.units.players.roles.Warrior;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Position;
import utils.callbacks.MessageCallback;

public class MonsterTest {
    private Monster monster;
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
        monster = new Monster(25, "Lannister Solider", 8, 3, 80, -1, -1, 's', 3, msg);
        player = new Warrior("Jon Snow", 30, 4, 300, -1, -1, 3, msg);
    }

    @Test
    public void testVision() {
        Assert.assertEquals("Initial vision should be 3", 3, monster.getVision());
        monster.setVision(5);
        Assert.assertEquals("Updated vision should be 5", 5, monster.getVision());
    }

    @Test
    public void testChooseDirection() {
        // Set up a monster with vision range of 3
        monster.setVision(3);

        // Player is within the vision range, monster should move towards the player
        player.setPosition(new Position(0, -1)); // Player is to the right of the monster
        Assert.assertEquals("Monster should move right", "d", monster.chooseDirection(player));

        player.setPosition(new Position(-2, -1)); // Player is below the monster
        Assert.assertEquals("Monster should move down", "a", monster.chooseDirection(player));

        player.setPosition(new Position(-1, 1)); // Player is to the left of the monster
        Assert.assertEquals("Monster should move left", "s", monster.chooseDirection(player));

        player.setPosition(new Position(1, -1)); // Player is above the monster
        Assert.assertEquals("Monster should move up", "d", monster.chooseDirection(player));

        // Test when player is exactly at the vision range border
        player.setPosition(new Position(-3, 0)); // Player is at the left border of the vision range
        Assert.assertEquals("Monster should move left", "a", monster.chooseDirection(player));

        player.setPosition(new Position(0, -3)); // Player is at the bottom border of the vision range
        Assert.assertEquals("Monster should move down", "w", monster.chooseDirection(player));
    }

    @Test
    public void testOnDeath() {
        monster.onDeath(player, false);
        Assert.assertEquals("Player should gain EXP", 25, player.getExp());
        // Further assertions based on the onDeath behavior can be added here
    }

    @Test
    public void testDescription() {
        String expectedDescription = " name: Lannister Solider  AttackPoints: 8  DefensePoints: 3  Health Points : (Max: 80 Current: 80) exp raise : 25 vision = 3";
        Assert.assertEquals("Description should match expected", expectedDescription, monster.description());
    }
}
