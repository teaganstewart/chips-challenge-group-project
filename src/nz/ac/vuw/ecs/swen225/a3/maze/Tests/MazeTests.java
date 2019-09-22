package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.persistence.SaveUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MazeTests {

	@Test
	public void testMovePlayerValid(){
        // Setup

        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(new Coordinate(3, 3));
        Maze maze = new Maze(tiles, player);

		assert(maze.movePlayer(Direction.LEFT));
		assert(player.getRow() == 3);
		assert(player.getCol() == 2);
		assert(maze.movePlayer(Direction.UP));
		assert(player.getRow() == 2);
		assert(player.getCol() == 2);
		assert(maze.movePlayer(Direction.RIGHT));
		assert(player.getRow() == 2);
		assert(player.getCol() == 3);
		assert(maze.movePlayer(Direction.DOWN));
		assert(player.getRow() == 3);
		assert(player.getCol() == 3);
	}

	@Test
	public void testMovePlayerNotValid(){
        // Setup

        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(new Coordinate(0,0));
        Maze maze = new Maze(tiles, player);

		assertFalse(maze.movePlayer(Direction.LEFT));
	}

	@Test
	public void testCollectValid(){
        // Setup

        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(new Coordinate(0, 0));
        Maze maze = new Maze(tiles, player);

		Key key = new Key(BasicColor.YELLOW);
		tiles[0][1].setEntity(key);

		assert(maze.movePlayer( Direction.RIGHT));
		assert(player.getInventory().contains(key));
	}

	@Test
	public void testCollectNotValid(){
        // Setup

        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(new Coordinate(3, 3));
        player.addToInventory(new Key(BasicColor.GREEN));
        Maze maze = new Maze(tiles, player);
        Level level = new Level(1, maze, System.currentTimeMillis(), 0);
		SaveUtils.saveGame(level);

		tiles[3][2].setEntity(new KeyDoor(BasicColor.RED));
		assertFalse(maze.movePlayer(Direction.LEFT));
	}

	//-----------------------------//
	//-------GENERAL TESTS --------//
	//-----------------------------//

	@Test
	void extensionsTest() {
		Player p = new Player(new Coordinate(1, 2));
		assertTrue(p.addToInventory(new TreasureDoor()));
	}
}
