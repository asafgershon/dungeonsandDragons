package control.initializers;

import model.game.Board;
import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.MessageCallback;
import model.tiles.units.enemies.Types.Trap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LevelInitializer {
    private List<Monster> monsters;
    private List<Trap> traps;
    private Player player;
    private Board board;
    private TileFactory factory;

    public LevelInitializer(MessageCallback msg){
        this.monsters = new ArrayList<>();
        this.traps = new ArrayList<>();
        this.factory = new TileFactory(msg);
    }

    public void initLevel(String levelPath){
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(levelPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int sizeX = lines.get(0).length();
        int sizeY = lines.size();
        board = new Board(sizeX, sizeY);

        int currentY = 0;
        for (String line : lines) {
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                switch (c) {
                    case '.':
                        board.addTile(new Empty(x, currentY));
                        break;
                    case '#':
                        board.addTile(new Wall(x, currentY));
                        break;
                    case '@':
                        player = factory.getPlayer(1); // Assuming player index is 1 for simplicity
                        player.setPosition(new Position(x, currentY));
                        board.addTile(player);
                        break;
                    default:
                        Tile tile = factory.getTile(c, x, currentY);
                        board.addTile(tile);
                        addEnemy(tile, c);
                        break;
                }
            }
            currentY++;
        }
    }

    private void addEnemy(Tile t, char c) {
        if (c == 'B' || c == 'Q' || c == 'D')
            traps.add((Trap) t);
        else
            monsters.add((Monster) t);
    }

    public Board getBoard() {
        return board;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Trap> getTraps() {
        return traps;
    }

}
