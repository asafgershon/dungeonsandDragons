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

    public Level(MessageCallback msg) {
        this.monsters = new LinkedList<>();
        this.traps = new LinkedList<>();
        this.msg = msg;
        this.buildLevel = new LevelInitializer(msg);
    }

    public void choosePlayer(Player playerChosen)
    {
        this.player = playerChosen;
    }

    public void loadLevel(String filePath){
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
        this.player.info();
        msg.send(this.board.toString());
    }

}
