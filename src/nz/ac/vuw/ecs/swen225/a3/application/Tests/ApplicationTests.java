package nz.ac.vuw.ecs.swen225.a3.application.Tests;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import nz.ac.vuw.ecs.swen225.a3.application.*;
import nz.ac.vuw.ecs.swen225.a3.application.ui.*;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.recnplay.ActionRecord;
import nz.ac.vuw.ecs.swen225.a3.recnplay.ReplayUtils;
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
		 game.loadLevel(gp, 1);
		
		for(int i= 0; i <6; i++) {
			game.getMaze().movePlayer(Direction.DOWN);
			if(game.getMaze().isOnHint()) {
				game.loadLevel(gp, game.getLevelNum()+1);
			}
		}
		
		GUI.stopTimer();
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
		gui.infoPanel.setPause(true);
		gui.disable();
		Game game = new Game();
		Render render = new Render(game, game.getMaze());
		render.createGrid();

		recnplayThings(game);

		Player p = new Player(new Coordinate(3,3));
		game.setPlayer(p);
		Integer[] events = {KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN };
		
		for(Integer k : events) {
			KeyEvent key = new KeyEvent(GUI.main, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, k,'Z');
		    GUI.main.getKeyListeners()[0].keyReleased(key);
		}
		
		Integer[] events2 = {KeyEvent.VK_SPACE, KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD,  KeyEvent.VK_D};

		for(Integer k : events2) {
			KeyEvent key = new KeyEvent(GUI.main, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, k,'Z');
			GUI.setReplayMode(true);
		    GUI.main.getKeyListeners()[0].keyReleased(key);
		    GUI.setReplayMode(false);
		    GUI.main.setFocusable(true);
		}
		GUI.stopTimer();
		
		assertEquals(game.getPlayer().toString(),p.toString());
		assertEquals(new Coordinate(3,3).toString(), game.getPlayer().getCoordinate().toString());
	}
	
	/**
	 * Tests the recnplay elements of the info panel.
	 */
	@Test
	void testingRec() {
		GUI gui = new GUI();
		Game game = new Game();
		GamePanel gp = new GamePanel(game);
		gp.drawBoard();
		gui.infoPanel.setPause(true);
		gui.infoPanel.recnplayPanel();
		gui.infoPanel.updateRec(true);
		
		GUI.stopTimer();
	
	}
	
	/**
	 * Mild testing of recnplay but inside application.
	 */
	void recnplayThings(Game game) {
		ReplayUtils r = new ReplayUtils();
		List<ActionRecord> record = new ArrayList<ActionRecord>();
		record.add(new ActionRecord(1, game.getMaze()));
		record.add(new ActionRecord(24336, game.getMaze()));
		r.setReplayList(record);
		
		GUI.stopTimer();
//		for(String s : LoadUtils.getSavesByID().keySet()) {
//			ReplayUtils.playBack(s);
//		}
	}
	
}
