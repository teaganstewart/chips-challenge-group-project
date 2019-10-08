package nz.ac.vuw.ecs.swen225.a3.render.Tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.application.ui.GUI;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.render.*;

/**
 * Class which contains all tests that test classes in the render
 * directory.
 * 
 * @author Teagan Stewart
 */
public class RenderTests {

	@Test
	void createGridTest() {

		Tile[][] tiles = emptyBoard();

		GUI gui = new GUI();
		System.out.println(gui.toString());
		Game game = new Game();
		game.getMaze().setPlayer(new Player(new Coordinate(4,4)));
		game.setTiles(tiles);
		Render render = new Render(game, game.getMaze());
		
		JLabel[][] outcome = render.createGrid();
		JLabel[][] expected = new JLabel[9][9];
		
		//sets up the expected board, a bunch of floor tiles.
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[0].length; col++) {
				JLabel floor = new JLabel(new ImageIcon(getClass().getResource("../icons/tiles/floor.png")));
				expected[row][col] = floor;
			}
		}

		assertEquals(expected[0][0].getIcon().toString(), outcome[0][0].getIcon().toString());

	}

	@Test
	void drawEntitiesTest() {

		Tile[][] tiles = emptyBoard();

		// setting up the board
		int i = 3;
		for(BasicColor color : BasicColor.values()) {
			tiles[5][i].setEntity(new Key(color));
			tiles[6][i].setEntity(new KeyDoor(color));
			i++;
		}
		
		tiles[7][5].setEntity(new Treasure());
		
		GUI gui = new GUI();
		gui.disable();
		Game game = new Game();
		game.setTiles(tiles);

		Render render = new Render(game, game.getMaze());
		render.createGrid();
		GraphicalView gv = new GraphicalView(game, render);
		gv.drawOnGrid();
		
		JLabel expected = (JLabel)render.getBoard()[7][5].getComponent(0);
		assertEquals(expected.getIcon().toString(), 
				new ImageIcon(getClass().getResource("../icons/entities/treasure.png")).toString());

	}
	
	@SuppressWarnings("deprecation")
	@Test
	void renderInventoryTest() {
		Player player = new Player(new Coordinate(3, 3));
		
		GUI gui = new GUI();
		gui.disable();
		Game game = new Game();
		game.getMaze().setPlayer(player);
		//setting up the inventory
		for(BasicColor color : BasicColor.values()) {
			player.addToInventory(new Key(color));
			player.addToInventory(new FireBoots());
			player.addToInventory(new IceBoots());
		}
		
		// testing the rendering of the inventory
		Render render = new Render(game, game.getMaze());
		render.renderInventory();
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void renderMoveablesTest() {
		
		GUI gui = new GUI();
		gui.disable();
		Game game = new Game();
		//adding enemies
		List<Skeleton> enemies = new ArrayList<Skeleton>();
		enemies.add(new Skeleton(new Coordinate(0,1), Direction.UP));
		game.getMaze().setEnemyList(enemies);
		
		List<Crate> crates = new ArrayList<Crate>();
		crates.add(new Crate(new Coordinate(0,0)));
		game.getMaze().setCrateList(crates);
		
		// testing the rendering of the inventory
		Render render = new Render(game, game.getMaze());
		render.createGrid();
		
	}
	
	private Tile[][] emptyBoard() {
		
		Tile[][] tiles = new Tile[9][9];
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[0].length; col++) {
				tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
			}
		}
		return tiles;
	}

}
