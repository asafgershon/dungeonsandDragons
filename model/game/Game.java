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
        this.directoryPath = directoryPath;
        msg.send("notice you must put the level_dir folder");
        msg.send("you put it? enter 0 for continue");
        Scanner scanner = new Scanner(System.in);
        int validfolder = scanner.nextInt();
        if (validfolder == 0) {
            tileFactory.printThePlayers();
            msg.send("Choose your player from the list");
            int playerChosen = scanner.nextInt();
            while(playerChosen < 1 || playerChosen > 7) {
                msg.send("Invalid player chosen");
                playerChosen = scanner.nextInt();
            }
            currentLevel.choosePlayer(tileFactory.getPlayer(playerChosen));
        }
        else{
             msg.send("reload the game");
        }

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
