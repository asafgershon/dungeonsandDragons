package model.game;

import control.initializers.TileFactory;
import utils.callbacks.MessageCallback;
import view.CLI;
import java.util.Scanner;

public class Game {
    private Level currentLevel;
    private String directoryPath;
    private TileFactory tileFactory;
    private MessageCallback msg;

    public Game(String directoryPath, MessageCallback msg) {
        this.msg = msg;
        this.tileFactory = new TileFactory(msg);
        this.currentLevel = new Level(msg);
        tileFactory.printThePlayers();
        this.directoryPath = directoryPath;

        Scanner scanner = new Scanner(System.in);
        msg.send("Choose your player from the list");
        int playerChosen = scanner.nextInt();
        currentLevel.choosePlayer(tileFactory.getPlayer(playerChosen));
    }

    public void ActivateGame() {
        Scanner scanner = new Scanner(System.in);
        int levelNumber = 1;

        while (!currentLevel.gameOver() && currentLevel.hasLevel(directoryPath + "\\level" + levelNumber + ".txt")) {
            currentLevel.loadLevel(directoryPath + "\\level" + levelNumber + ".txt");

            while (!currentLevel.gameOver() && !currentLevel.isOver()) {
                EmptyRow();
                currentLevel.levelInfo();
                msg.send("Your turn - ");
                String userAction = scanner.nextLine();
                currentLevel.gameTick(userAction);
            }

            levelNumber++;
        }

        msg.send("\n GAME OVER!!!");
    }

    public void EmptyRow() {
        msg.send("");
    }
}
