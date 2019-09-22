package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Josh
 * 
 * Class which contains all tests that test classes in the maze directory
 *
 */
public class MazeTest {

	/**
	 * 
	 */
	@Test
	public void testMovePlayerValid(){
        // Setup

        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(3, 3);
        Maze maze = new Maze(tiles, player);

        // Player starts facing right
        assert(player.getDirection() == Direction.RIGHT);
        assert(player.getLastDirection() == null);
        
		assert(maze.movePlayer(Direction.DOWN));
		assert(player.getLastDirection() == Direction.RIGHT);
		assert(player.getDirection() == Direction.DOWN);
		assert(player.getPrevPos().equals(new Coordinate(3, 3)));
		assert(player.getRow() == 4);
		assert(player.getCol() == 3);
		
		assert(maze.movePlayer(Direction.UP));
		assert(player.getRow() == 3);
		assert(player.getCol() == 3);
		
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

	/**
	 * Testing moving the player off the tiles
	 */
	@Test
	public void testMovePlayerNotValid(){
        // Setup

        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(0, 0);
        Maze maze = new Maze(tiles, player);

		assertFalse(maze.movePlayer(Direction.LEFT));
	}

	/**
	 * Test to see if player correctly unlocks a key door
	 */
	@Test
	public void testUnlockKeyDoor(){
        // Setup

        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(0, 0);
        Maze maze = new Maze(tiles, player);

        // Collect key
		Key key = new Key(BasicColor.YELLOW);
		tiles[0][1].setEntity(key);

		// Test setting inventory
		player.setInventory(new ArrayList<Entity>(Arrays.asList(new Key(BasicColor.BLUE))));
		
		assert(maze.movePlayer( Direction.RIGHT));
		assert(player.getInventory().contains(key));
		assert(player.getInventoryAt(1).equals(key));
		
		assert(player.removeInventoryAt(0));
		assert(player.getInventoryAt(0).equals(key));
		

		// Unlock door with same color as key
        tiles[0][2].setEntity(new KeyDoor(BasicColor.YELLOW));
        assert(maze.movePlayer(Direction.RIGHT));

        // Make sure Chap keeps the key after unlocking the door
        assert(player.getInventory().contains(key));
	}

    /**
     * Test to see if player fails to unlock a key door when player does not have any keys
     */
    @Test
	public void testUnlockDoorNotValid01(){
        // Setup

        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(0, 0);
        Maze maze = new Maze(tiles, player);

		tiles[0][1].setEntity(new KeyDoor(BasicColor.RED));
		assertFalse(maze.movePlayer(Direction.RIGHT));
	}

    /**
     * Test to see if player fails to enter key door when they have the wrong colored key
     */
    @Test
    public void testUnlockDoorNotValid02(){
        // Setup

        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(0, 0);
        Maze maze = new Maze(tiles, player);

        // Collect key
        Key key = new Key(BasicColor.RED);
        tiles[0][1].setEntity(key);

        assert(maze.movePlayer( Direction.RIGHT));
        assert(player.getInventory().contains(key));

        // Unlock door with WRONG colored key
        tiles[0][2].setEntity(new KeyDoor(BasicColor.YELLOW));
        assertFalse(maze.movePlayer(Direction.RIGHT));

        // Make sure Chap still contains key
        assert(player.getInventory().contains(key));
    }

    /**
     * Test to see if player unlocks the treasure door after collecting all the treasure
     */
    @Test
    public void testUnlockTreasureDoor(){
        // Setup

        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(0, 0);
        Maze maze = new Maze(tiles, player);

        // Collect treasure
        Treasure treasure = new Treasure();
        tiles[0][1].setEntity(treasure);

        assert(maze.movePlayer( Direction.RIGHT));

        // Unlock treasure door after all treasure is collected
        tiles[0][2].setEntity(new TreasureDoor());
        assert(maze.movePlayer(Direction.RIGHT));
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
