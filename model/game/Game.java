package model.game;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.units.players.Player;
import model.tiles.units.players.Player;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

    public class Game {
        private Level currentLevel;
        private String directoryPath;

        public Game(String directoryPath)
        {
            System.out.println();
            System.out.println("Game Keys - w = up  - d = right  - s = down - a = left - e = ability");
            System.out.println();
            this.currentLevel = new Level();
            System.out.println(currentLevel.showPlayers());
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
                    System.out.println();
                    currentLevel.levelInfo();
                    System.out.println("Your turn - ");
                    String userAction = scanner.nextLine();
                    currentLevel.gameTick(userAction);

                }

                levelNumber++;
            }

            System.out.println("\n GAME OVER!!!");
        }

    }

