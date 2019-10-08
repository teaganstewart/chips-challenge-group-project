package nz.ac.vuw.ecs.swen225.a3.application.Tests;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.event.KeyEvent;
import org.junit.jupiter.api.Test;
import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.application.ui.GUI;
import nz.ac.vuw.ecs.swen225.a3.application.ui.GamePanel;
import nz.ac.vuw.ecs.swen225.a3.maze.Coordinate;
import nz.ac.vuw.ecs.swen225.a3.maze.Direction;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

/**
 * Class which contains all tests that test classes in the application
 * directory.
 * 
 * @author Teagan Stewart
 */
public class ApplicationTests {

	@Test
	public void nextLevelTest() {
		// setup
		GUI gui = new GUI();
		gui.disable();
		Game game = new Game();
		GamePanel gp = new GamePanel(game);

		// moving the player to reach the next level.
		game.loadGame();
		
		for(int i= 0; i <6; i++) {
			game.getMaze().movePlayer(Direction.UP);
			if(game.getMaze().isOnHint()) {
				game.loadLevel(gp, game.getLevelNum()+1);
			}
		}
	}

	@Test
	void keysTest() {
		GUI gui = new GUI();
		gui.disable();
		Game game = new Game();
		Render render = new Render(game, game.getMaze());
		render.createGrid();

		Player p = new Player(new Coordinate(3,3));
		Integer[] events = {KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN };

		for(Integer k : events) {
			KeyEvent key = new KeyEvent(GUI.main, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, k,'Z');
		    GUI.main.getKeyListeners()[0].keyReleased(key);
		    GUI.main.setFocusable(true);
		}

		assertEquals(new Coordinate(3,3).toString(), p.getCoordinate().toString());
	}
}
