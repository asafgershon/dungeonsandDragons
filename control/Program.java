package control;

import model.game.Game;
import utils.callbacks.MessageCallback;
import view.CLI;
import view.View;

public class Program {
    public static void main(String[] args) {
        View cliView = new CLI();
        MessageCallback callback = cliView.getCallback();

        Game game = new Game("C:\\Users\\gersh\\IdeaProjects\\dragon\\dungeonsandDragons\\levels_dir", callback);
        game.ActivateGame();
    }
}
