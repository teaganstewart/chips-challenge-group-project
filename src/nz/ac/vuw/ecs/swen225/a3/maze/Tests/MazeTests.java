package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MazeTests {

	private Tile[][] tiles;
	private Maze maze;

	public MazeTests(){
		// Setup

		tiles = new Tile[9][9];
		for(int row=0; row < 9; row++){
			for(int col=0; col < 9; col++) {
				tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
			}
		}
		maze = new Maze(tiles);
	}

	@Test
	public void testMovePlayerValid(){
		tiles[0][0].setEntity(new Player(0,0));
		assert(maze.movePlayer(0, 0, 4, 4));
	}

	// TODO Array Index Out of Bounds means this test fails
	@Test
	public void testMovePlayerNotValid(){
		tiles[0][0].setEntity(new Player(0,0));
		try {
			maze.movePlayer(0, 0, -1, -1);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
			return;
		}

		assert(false);
	}

	@Test
	public void testMovePlayerAndCollectValid(){
		tiles[0][0].setEntity(new Player(0,0));
		tiles[4][4].setEntity(new Key(BasicColor.YELLOW));

		assert(maze.movePlayer(0, 0, 4, 4));
		// TODO assert correct item is collected
	}

	@Test
	public void testMovePlayerAndCollectNotValid(){
		tiles[0][0].setEntity(new Player(0,0));
		tiles[4][4].setEntity(new KeyDoor(BasicColor.RED));
		assertFalse(maze.movePlayer(0, 0, 4, 4));
	}

	//-----------------------------//
	//-------GENERAL TESTS --------//
	//-----------------------------//

	@Test
	void extensionsTest() { 
		Player p = new Player(1,2);
		Player p2 = new Player(3,1);
		assertTrue(p.addToInventory(p2));

	}
}
