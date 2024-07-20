package control.initializers;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.enemies.Types.Trap;
import model.tiles.units.players.Player;
import model.tiles.units.players.roles.Hunter;
import model.tiles.units.players.roles.Mage;
import model.tiles.units.players.roles.Rogue;
import model.tiles.units.players.roles.Warrior;
import utils.Position;
import utils.callbacks.MessageCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TileFactory {
    private List<Supplier<Player>> playersList;
    private Map<Character, Supplier<Enemy>> enemiesMap;
    private MessageCallback msg;
    private Player selected;

    public TileFactory(MessageCallback msg) {
        this.msg = msg;
        playersList = initPlayers();
        enemiesMap = initEnemies();
    }

    private List<Supplier<Player>> initPlayers() {
        return Arrays.asList(
                () -> new Warrior("Jon Snow", 30, 4, 300, -1,-1,3,msg),
                () -> new Warrior("The Hound", 20, 6, 400, -1,-1,5,msg),
                () -> new Mage("Melisandre", 5, 1, 100, -1, -1, 300, 15,30,5, 6,msg),
                () -> new Mage("Thoros of Myr", 25, 4, 250, -1, -1, 150, 20,20,3, 4,msg),
                () -> new Rogue("Arya Stark", 40, 2, 150, -1,-1,20,msg),
                () -> new Rogue("Bronn", 35, 3, 250, -1,-1,50,msg),
                () -> new Hunter("Ygritte", 30, 2, 220, -1,-1,6,msg)
        );
    }

    private Map<Character, Supplier<Enemy>> initEnemies() {
        List<Supplier<Enemy>> enemies = Arrays.asList(
                () -> new Monster(25, "Lannister Solider", 8, 3, 80,-1, -1,'s',3,msg),
                () -> new Monster(50,"Lannister Knight",14,8,200,-1 , -1 , 'k' , 4,msg),
                () -> new Monster(100,"Queen's Guard",20,15,400,-1 , -1 , 'q' , 5,msg),
                () -> new Monster(100,"Wright",30,15,600,-1 , -1 , 'z' , 3,msg),
                () -> new Monster(250,"Bear-Wright",75,30,1000,-1 , -1 , 'b' , 4,msg),
                () -> new Monster(500,"Giant-Wright",100,40,1500,-1 , -1 , 'g' , 5,msg),
                () -> new Monster(1000,"White Walker",150,50,2000,-1 , -1 , 'w' , 6,msg),
                () -> new Monster(500,"The Mountain",60,25,1000,-1 , -1 , 'M' , 6,msg),
                () -> new Monster(1000,"Queen Cersei",10,10,100,-1 , -1 , 'C' , 1,msg),
                () -> new Monster(5000,"Night's King",300,150,5000,-1 , -1 , 'K' , 8,msg),
                () -> new Trap(250,"Bonus Trap",1,1,1,-1,-1,'B',1,5,msg),
                () -> new Trap(100,"Queen's trap",50,10,250,-1,-1,'Q',3,7,msg),
                () -> new Trap(250,"Death Trap",100,20,500,-1,-1,'D',1,10,msg)
        );
        return enemies.stream().collect(Collectors.toMap(s -> s.get().getSymbol(), Function.identity()));
    }

    public List<Player> listPlayers() {
        return playersList.stream().map(Supplier::get).collect(Collectors.toList());
    }

    public Player getPlayer(int index) {
        selected = this.playersList.get(index-1).get();
        return selected;
    }

    public Enemy getEnemy(char c) {
        return this.enemiesMap.get(c).get();
    }

    public Tile getTile(char c, int x, int y) {
        if (c == '#')
            return new Wall(x, y);
        if (c == '.')
            return new Empty(x, y);

        Enemy e = getEnemy(c);
        e.setPosition(new Position(x, y));

        return e;
    }

    public void printThePlayers() {
        for (int i = 0; i < listPlayers().size(); i++) {
            msg.send((i + 1) + ". " + listPlayers().get(i).description());
        }
    }
}
