package control;

import model.game.Game;
import utils.callbacks.MessageCallback;
import view.CLI;
import view.View;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {
    public static void main(String[] args) {
        View cliView = new CLI();
        MessageCallback callback = cliView.getCallback();

        //String levelsDirPath = Paths.get("levels_dir").toAbsolutePath().toString();
        String levelsDirPath;

        if (args.length > 0) {
            // Use the path provided as a command line argument
            levelsDirPath = args[0];
        } else {
            // Fallback to the default resource path within the JAR
            URL resource = Program.class.getClassLoader().getResource("levels_dir");
            if (resource == null) {
                System.err.println("Resource levels_dir not found.");
                return;
            }
            try {
                Path path = Paths.get(resource.toURI());
                levelsDirPath = path.toAbsolutePath().toString();
            } catch (URISyntaxException e) {
                //e.printStackTrace();
                return;
            }
        }
        Game game = new Game(levelsDirPath, callback);
        game.ActivateGame();
    }
}
