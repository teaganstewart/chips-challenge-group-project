package nz.ac.vuw.ecs.swen225.a3.application.Tests;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.event.KeyEvent;
import org.junit.jupiter.api.Test;
import nz.ac.vuw.ecs.swen225.a3.application.*;
import nz.ac.vuw.ecs.swen225.a3.application.ui.*;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

/**
 * Class which contains all tests that test classes in the application
 * directory.
 * 
 * @author Teagan Stewart
 */
public class ApplicationTests {

	/**
	 * Tests whether load level works properly and has the correct
	 * level in the game class.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void nextLevelTest() {
		// setup
		Main.main(new String[1]);
		GUI gui = new GUI();
		gui.disable();
		Game game = new Game();
		GamePanel gp = new GamePanel(game);

		// moving the player to reach the next level.
		game.loadGame();
		
		for(int i= 0; i <6; i++) {
			game.getMaze().movePlayer(Direction.DOWN);
			if(game.getMaze().isOnHint()) {
				game.loadLevel(gp, game.getLevelNum()+1);
			}
		}
		
		assertEquals(2, game.getLevelNum());
		assertEquals(game.getLevel().toString(), LoadUtils.loadLevel(game.getLevelNum()).toString());
	}

	/**
	 * Tests key presses.
	 */
	@SuppressWarnings("deprecation")
	@Test
	void keysTest() {
		GUI gui = new GUI();
		gui.disable();
		Game game = new Game();
		Render render = new Render(game, game.getMaze());
		render.createGrid();

		Player p = new Player(new Coordinate(3,3));
		game.setPlayer(p);
		Integer[] events = {KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN };
		
		for(Integer k : events) {
			KeyEvent key = new KeyEvent(GUI.main, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, k,'Z');
		    GUI.main.getKeyListeners()[0].keyReleased(key);
		    GUI.main.setFocusable(true);
		}

		assertEquals(game.getPlayer().toString(),p.toString());
		assertEquals(new Coordinate(3,3).toString(), game.getPlayer().getCoordinate().toString());
	}
	
}
