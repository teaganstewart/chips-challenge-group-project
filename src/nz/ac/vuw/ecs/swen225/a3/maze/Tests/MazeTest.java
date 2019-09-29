package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Josh
 *
 * Class which contains all tests that test classes in the maze directory
 *
 */
public class MazeTest {

    /**
     * Testing basic player moves
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

        Player player = new Player(new Coordinate(3, 3));
        Maze maze = new Maze(tiles, player);

        assertTrue(maze.movePlayer(Direction.DOWN));

        assertEquals(Direction.RIGHT, player.getLastDirection());
        assertEquals(Direction.DOWN, player.getDirection());
        assertEquals(new Coordinate(4, 3), player.getCoordinate());
        assertEquals(new Coordinate(3, 3), player.getPrevPos());

        assertTrue(maze.movePlayer(Direction.RIGHT));
        assertEquals(new Coordinate(4, 4), player.getCoordinate());

        assertTrue(maze.movePlayer(Direction.UP));
        assertEquals(new Coordinate(3, 4), player.getCoordinate());

        assertTrue(maze.movePlayer(Direction.LEFT));
        assertEquals(new Coordinate(3, 3), player.getCoordinate());
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

        Player player = new Player(new Coordinate(0, 0));
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

        Player player = new Player(new Coordinate(0, 0));
        Maze maze = new Maze(tiles, player);

        Key key = new Key(BasicColor.YELLOW);
        tiles[0][1].setEntity(key);

        // Collect key
        assertTrue(maze.movePlayer( Direction.RIGHT));

        // Unlock door with same color as key
        tiles[0][2].setEntity(new KeyDoor(BasicColor.YELLOW));
        assertTrue(maze.movePlayer(Direction.RIGHT));

        // Make sure Chap keeps the key after unlocking the door
        assertTrue(player.getInventory().contains(key));
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

        Player player = new Player(new Coordinate(0, 0));
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

        Player player = new Player(new Coordinate(0, 0));
        Maze maze = new Maze(tiles, player);

        Key key = new Key(BasicColor.RED);
        tiles[0][1].setEntity(key);

        // Collect key
        assertTrue(maze.movePlayer( Direction.RIGHT));

        // Unlock door with WRONG colored key
        tiles[0][2].setEntity(new KeyDoor(BasicColor.YELLOW));
        assertFalse(maze.movePlayer(Direction.RIGHT));

        // Make sure Chap still contains key
        assertTrue(player.getInventory().contains(key));
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

        Player player = new Player(new Coordinate(0, 0));
        Maze maze = new Maze(tiles, player);

        Treasure treasure = new Treasure();
        tiles[0][1].setEntity(treasure);

        // Collect treasure
        assertTrue(maze.movePlayer( Direction.RIGHT));

        // Unlock treasure door after all treasure is collected
        tiles[0][2].setEntity(new TreasureDoor());
        assertTrue(maze.movePlayer(Direction.RIGHT));
    }

    //-----------------------------//
    //-------GENERAL TESTS --------//
    //-----------------------------//

    @Test
    void extensionsTest() {
        Player p = new Player(new Coordinate(1,2));
        Player p2 = new Player(new Coordinate(3,1));
        // Not sure what was trying to be done in this line, but it doesn't compile.
        // Please tell me your intentions
//		assertTrue(p.addToInventory(p2));
    }
}
