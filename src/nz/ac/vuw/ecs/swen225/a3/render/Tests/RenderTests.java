package nz.ac.vuw.ecs.swen225.a3.render.Tests;

import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.application.ui.GUI;
import nz.ac.vuw.ecs.swen225.a3.application.ui.GamePanel;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.render.*;

/**
 * @author Teagan
 * 
 *         Class which contains all tests that test classes in the render
 *         directory.
 *
 */
public class RenderTests {

	@Test
	void createGridTest() {

		Tile[][] tiles = new Tile[9][9];
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[0].length; col++) {
				tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
			}
		}

		GUI gui = new GUI();
		System.out.println(gui.toString());
		Game game = new Game();
		game.setTiles(tiles);
		Render render = new Render(game, game.getMaze());
		JLabel[][] outcome = render.createGrid();
		JLabel[][] expected = new JLabel[9][9];
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[0].length; col++) {
				JLabel floor = new JLabel(new ImageIcon(getClass().getResource("../icons/tiles/wall.png")));
				expected[row][col] = floor;
			}
		}

		assertEquals(expected[0][0].getIcon().toString(), outcome[0][0].getIcon().toString());

	}

	@Test
	void drawEntitiesTest() {

		Tile[][] tiles = new Tile[9][9];
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[0].length; col++) {
				tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
			}
		}

		// setting up the board
		int i = 3;
		for(BasicColor color : BasicColor.values()) {
			tiles[5][i].setEntity(new Key(color));
			tiles[6][i].setEntity(new KeyDoor(color));
			i++;
		}
		
		tiles[7][5].setEntity(new Treasure());
		
		GUI gui = new GUI();
		Game game = new Game();
		game.setTiles(tiles);

		Render render = new Render(game, game.getMaze());
		render.createGrid();
		GraphicalView gv = new GraphicalView(game, render);
		GamePanel gp = new GamePanel(game);
		gv.drawOnGrid();
		
		JLabel expected = (JLabel)render.getBoard()[7][5].getComponent(0);
		assertEquals(expected.getIcon().toString(), 
				new ImageIcon(getClass().getResource("../icons/entities/treasure.png")).toString());

	}
	
	@Test
	void renderInventoryTest() {
		Player player = new Player(new Coordinate(3, 3));
		
		GUI gui = new GUI();
		Game game = new Game();
		
		//setting up the inventory
		for(BasicColor color : BasicColor.values()) {
			player.addToInventory(new Key(color));
		}
		
		// testing the rendering of the inventory
		Render render = new Render(game, game.getMaze());
		render.renderInventory();
		
	}

}
