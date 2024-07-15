package monsterType;

import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.players.Player;
import model.tiles.units.players.roles.Warrior;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Position;

public class MonsterTest {
    private Monster monster;
    private Player player;

    @Before
    public void initTest() {
        monster = new Monster(25, "Lannister Solider", 8, 3, 80, -1, -1, 's', 3);
        player = new Warrior("Jon Snow", 30, 4, 300, -1, -1, 3);
    }

    @Test
    public void testVision() {
        Assert.assertEquals("Initial vision should be 3", 3, monster.getVision());
        monster.setVision(5);
        Assert.assertEquals("Updated vision should be 5", 5, monster.getVision());
    }

    @Test
    public void testChooseDirection() {
        player.setPosition(new Position(-1, 0)); // Player is to the right of the monster
        Assert.assertEquals("Monster should move right", "d", monster.chooseDirection(player));

        player.setPosition(new Position(-1, -2)); // Player is below the monster
        Assert.assertEquals("Monster should move down", "s", monster.chooseDirection(player));

        player.setPosition(new Position(-3, -1)); // Player is to the left of the monster
        Assert.assertEquals("Monster should move left", "a", monster.chooseDirection(player));

        player.setPosition(new Position(1, -1)); // Player is above the monster
        Assert.assertEquals("Monster should move up", "w", monster.chooseDirection(player));
    }

    @Test
    public void testOnDeath() {
        monster.onDeath(player, false);
        Assert.assertEquals("Player should gain EXP", 25, player.getExp());
        // Further assertions based on the onDeath behavior can be added here
    }

    @Test
    public void testDescription() {
        String expectedDescription = "Lannister Solider (s) exp raise : 25 vision = 3";
        Assert.assertEquals("Description should match expected", expectedDescription, monster.description());
    }
}