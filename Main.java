import model.game.Game;
import utils.callbacks.MessageCallback;
import view.CLI;
import view.View;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {
        View cliView = new CLI();
        MessageCallback callback = cliView.getCallback();

        String levelsDirPath = Paths.get("levels_dir").toAbsolutePath().toString();
        Game game = new Game(levelsDirPath, callback);
        game.ActivateGame();
    }
}
