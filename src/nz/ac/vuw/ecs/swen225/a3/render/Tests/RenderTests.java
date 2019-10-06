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

		Player player = new Player(new Coordinate(3, 3));

		GUI gui = new GUI();
		Game game = new Game();
		game.setTiles(tiles);
		Render render = new Render(game, game.getMaze());
		JLabel[][] outcome = render.createGrid();
		JLabel[][] expected = new JLabel[9][9];
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[0].length; col++) {
				JLabel floor = new JLabel(new ImageIcon(getClass().getResource("../icons/wall.png")));
				expected[row][col] = floor;
			}
		}

		assertEquals(expected[0][0].getIcon().toString(), outcome[0][0].getIcon().toString());

	}

	@Test
	void drawRemovalTest() {

		Tile[][] tiles = new Tile[9][9];
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[0].length; col++) {
				tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
			}
		}

		tiles[5][3].setEntity(new Key(BasicColor.YELLOW));
		tiles[5][4].setEntity(new Key(BasicColor.GREEN));
		tiles[5][5].setEntity(new Key(BasicColor.BLUE));
		tiles[5][6].setEntity(new Key(BasicColor.RED));
		tiles[6][3].setEntity(new KeyDoor(BasicColor.YELLOW));
		tiles[6][4].setEntity(new KeyDoor(BasicColor.GREEN));
		tiles[6][5].setEntity(new KeyDoor(BasicColor.BLUE));
		tiles[6][6].setEntity(new KeyDoor(BasicColor.RED));
		tiles[7][5].setEntity(new Treasure());

		Player player = new Player(new Coordinate(3, 3));
		
		GUI gui = new GUI();
		Game game = new Game();
		game.setTiles(tiles);

		Render render = new Render(game, game.getMaze());
		render.createGrid();
		GraphicalView gv = new GraphicalView(game, render);
		GamePanel gp = new GamePanel(game);
		gv.drawOnGrid();

	}

}
