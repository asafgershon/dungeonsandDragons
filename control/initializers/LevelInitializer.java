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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


    public void setBoard(int x, int y)
    {
        this.board = new Board(x,y);
    }

    public void initLevel(String filePath, Player p) {
        this.player = p;
        int sizex = 0;
        int sizey = 0;
        boolean stopx = false;
        String line = null;
        File file = new File(filePath);

        try(Scanner scannerPre = new Scanner(file)) {
            while (scannerPre.hasNextLine()) {
                line = scannerPre.nextLine();
                sizey++;

                for (int i = 0; i < line.length() && !stopx ; i++) {
                    sizex++;
                }
                stopx = true;
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File doesnt exist");
        }

        setBoard(sizex,sizey);

        try (Scanner scanner = new Scanner(file)) {
            String Line = null;
            while (scanner.hasNextLine()) {
                Line = scanner.nextLine();
                for (int i = 0; i < Line.length(); i++) {
                    char currentTileSymbol = Line.charAt(i);
                    if (currentTileSymbol == '@') {
                        this.player.setPosition(new Position(i, this.board.getBoardCurrentY()));
                        this.board.addTile(this.player);
                    }

                    else {
                        Tile temp = factory.getTile(currentTileSymbol,i,this.board.getBoardCurrentY());
                        this.board.addTile(temp);
                        addEnemy(temp,currentTileSymbol);
                    }
                }
                this.board.increaseHeight();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File doesnt exist");
        }
    }

    private void addEnemy(Tile t, char c)
    {
        if (c != '#' && c != '.')
            if (c == 'B' | c == 'Q' | c =='D')
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
