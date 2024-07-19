package model.game;

import utils.callbacks.MessageCallback;
import view.CLI;
import java.util.Scanner;

    public class Game {
        private Level currentLevel;
        private String directoryPath;

        public Game(String directoryPath)
        {
            MessageCallback msg = (MessageCallback) new CLI();
            EmptyRow();
            msg.send("Game Keys - w = up  - d = right  - s = down - a = left - e = ability");
            EmptyRow();
            this.currentLevel = new Level();
            currentLevel.showPlayers();
            this.directoryPath = directoryPath;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose your player from the list");
            int playerChosen = scanner.nextInt();
            currentLevel.choosePlayer(playerChosen);

        }

        public void ActivateGame() {


            Scanner scanner = new Scanner(System.in);
            int levelNumber = 1;
            while (!currentLevel.gameOver() & currentLevel.hasLevel(directoryPath + "\\level" + levelNumber + ".txt")) {
                currentLevel.loadLevel(directoryPath + "\\level" + levelNumber + ".txt");

                while (!currentLevel.gameOver() & !currentLevel.isOver()) {
                    EmptyRow();
                    currentLevel.levelInfo();
                    System.out.println("Your turn - ");
                    String userAction = scanner.nextLine();
                    currentLevel.gameTick(userAction);

                }

                levelNumber++;
            }

            System.out.println("\n GAME OVER!!!");
        }

        public void EmptyRow(){
            MessageCallback msg = (MessageCallback) new CLI();
            msg.send("");
        }

    }

