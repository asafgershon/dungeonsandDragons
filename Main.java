import model.game.Game;
import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import utils.Position;
import utils.generators.FixedGenerator;

public class Main {
    public static void main(String[] args) {
        Game game = new Game("C:\\Users\\gersh\\IdeaProjects\\dragon\\dungeonsandDragons\\levels_dir");
        game.ActivateGame();
    }
}