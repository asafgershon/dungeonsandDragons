package model.game;

import control.initializers.LevelInitializer;
import control.initializers.TileFactory;
import model.tiles.Empty;
import model.tiles.Tile;
import utils.Position;
import model.tiles.units.players.Player;
import model.tiles.units.enemies.Types.Monster;
import model.tiles.units.enemies.Types.Trap;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.Unit;
import utils.callbacks.MessageCallback;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Level {
    private Board board;
    private List<Monster> monsters;
    private List<Trap> traps;
    private Player player;
    private MessageCallback msg;
    private LevelInitializer buildLevel;
    private TileFactory factory;

    public Level(MessageCallback msg) {
        this.monsters = new LinkedList<>();
        this.traps = new LinkedList<>();
        this.msg = msg;
        this.buildLevel = new LevelInitializer(msg);
        this.factory = new TileFactory(msg);
    }

    public void choosePlayer(Player playerChosen)
    {
        this.player = playerChosen;
    }

    public void loadLevel1(String filePath){
        buildLevel.initLevel(filePath, this.player);
        this.board = buildLevel.getBoard();
        this.monsters = buildLevel.getMonsters();
        this.traps = buildLevel.getTraps();
    }

    public boolean hasLevel(String filePath) {
        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)) {
            return true;
        }
        catch (FileNotFoundException e) {
            return false;
        }
    }

    public void gameTick(String action)
    {
        Position prev = player.getPosition();
        if (action.equals("e")) {
            List<Enemy> castAbilityOn = new LinkedList<Enemy>();
            castAbilityOn.addAll(this.monsters);
            castAbilityOn.addAll(this.traps);
            this.player.activateAbility(castAbilityOn);
        }
        else
            unitMove(this.player,action);

        this.board.swapPosition(this.player.getPosition(),prev);
        betweenGameTicks();

        for (Monster m : monsters)
        {
            prev = m.getPosition();
             //0 - left , 1 - UP ....
            unitMove(m,m.chooseDirection(this.player));
            this.board.swapPosition(m.getPosition(),prev);
            betweenGameTicks();
        }

        for (Trap t : traps) {
            t.gameTick(this.player);
            betweenGameTicks();
        }

    }

    public void unitMove(Unit u, String action)
    {
        if (action.equals("w")) {
            u.move(this.board.getTileInPosition(new Position(u.getPosition().getX(),u.getPosition().getY() - 1 )));
        }
        if (action.equals("s")) {
            u.move(this.board.getTileInPosition(new Position(u.getPosition().getX(),u.getPosition().getY() + 1 )));
        }
        if (action.equals("a"))
            u.move(this.board.getTileInPosition(new Position(u.getPosition().getX() - 1 ,u.getPosition().getY() )));

        if (action.equals("d"))
            u.move(this.board.getTileInPosition(new Position(u.getPosition().getX() + 1,u.getPosition().getY())));
    }
    public boolean gameOver()
    {
        return !player.alive();
    }

    public boolean isOver()
    {
        return monsters.size() == 0;
    }

    public void loadLevel(String filePath) {
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

    public void setBoard(int x, int y)
    {
        this.board = new Board(x,y);
    }

    public void addEnemy(Tile t, char c)
    {
        if (c != '#' && c != '.')
            if (c == 'B' | c == 'Q' | c =='D')
                traps.add((Trap) t);
            else
                monsters.add((Monster)t);
    }

    public void betweenGameTicks()
    {
        List<Monster> aliveMonsters = new LinkedList<Monster>();
        List<Trap> aliveTraps = new LinkedList<Trap>();
        for (Monster m : this.monsters)
        {
            if (!m.alive()) {
                this.board.addTile(new Empty(m.getPosition().getX(), m.getPosition().getY()));
            }
            else
                aliveMonsters.add(m);

        }
        for (Trap t : this.traps)
        {
            if (!t.alive()) {
                this.board.addTile(new Empty(t.getPosition().getX(), t.getPosition().getY()));
            }
            else
                aliveTraps.add(t);
        }

        this.traps = aliveTraps;
        this.monsters = aliveMonsters;
    }


    public void levelInfo()
    {
        msg.send(this.board.toString());
        this.player.info();
    }

}
