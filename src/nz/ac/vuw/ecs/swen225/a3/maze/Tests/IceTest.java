package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.application.ui.GUI;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Josh
 *         <p>
 *         Class which contains tests for the Ice Tile
 */
public class IceTest {

    private Tile[][] tiles;
    private Player player;
    private Maze maze;
    private List<Crate> crateList = new ArrayList<>();

    /*
     * Create a new maze before each test
     * */
    @BeforeEach
    public void setUp() {
    	GUI gui = new GUI();
        tiles = new Tile[9][9];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }
        player = new Player(new Coordinate(3, 3));
        crateList = new ArrayList<Crate>(Arrays.asList(
                new Crate(new Coordinate(5, 3)),
                new Crate(new Coordinate(5, 5))
        ));

        maze = new Maze(tiles, player, crateList,null);
        GUI.startTimer();
    }

    /*
     * Reset the Treasure class after each test as it contains
     * static methods that will change it's state
     * */
    @AfterEach
    public void tearDown() {
        Treasure.reset();
    }

    /**
     * Test to see if player walks on an ice block with ice boots
     */
    @Test
    public void testIceBlockWalking() {
        tiles[3][4].setEntity(new IceBoots());
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        assertTrue(maze.movePlayer(Direction.RIGHT));
        assertTrue(player.isInInventory(new IceBoots()));
        assertTrue(maze.movePlayer(Direction.RIGHT));
        assertTrue(maze.movePlayer(Direction.RIGHT));
        assertTrue(maze.movePlayer(Direction.RIGHT));
        assertEquals(new Coordinate(3, 7), player.getCoordinate());
    }

    /**
     * Test to see if player does not have ice boots and slides on ice onto a floor block
     */
    @Test
    public void testIceBlockSlidingOntoFloor() {
        tiles[3][4] = new Tile(new Coordinate(3, 4), Tile.TileType.ICE);
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        assertTrue(maze.movePlayer(Direction.RIGHT));
        // Player slides to the next non-ice block
        assertEquals(new Coordinate(3, 7), player.getCoordinate());
    }

    /**
     * Test to see if player does not have ice boots and slides on ice and next to a wall
     */
    @Test
    public void testIceBlockSlidingOntoWall() {
        tiles[3][4] = new Tile(new Coordinate(3, 4), Tile.TileType.ICE);
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        tiles[3][7] = new Tile(new Coordinate(3, 7), Tile.TileType.WALL);
        assertTrue(maze.movePlayer(Direction.RIGHT));
        // Player slides to the next non-ice block
        assertEquals(new Coordinate(3, 6), player.getCoordinate());
    }

    /**
     * Test to see if player does not have ice boots and slides on ice into a fire block
     */
    @Test
    public void testIceBlockSlidingOntoFire() {
        tiles[3][4] = new Tile(new Coordinate(3, 4), Tile.TileType.ICE);
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        tiles[3][7] = new Tile(new Coordinate(3, 7), Tile.TileType.FIRE);
        assertTrue(maze.movePlayer(Direction.RIGHT));
        // Player should now be standing on last ice block
        assertEquals(new Coordinate(3, 6), player.getCoordinate());
        assertFalse(maze.movePlayer(Direction.RIGHT));
        // Player should be dead from the fire
        assertTrue(maze.isResetLevel());
    }

    /**
     * Test to see if player does not have ice boots and slides on ice onto a hint tile
     */
    @Test
    public void testIceBlockSlidingOntoHint() {

        tiles[3][4] = new Tile(new Coordinate(3, 4), Tile.TileType.ICE);
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        tiles[3][7] = new HintTile(new Coordinate(3, 7), "You are Big Gay. Stop standing on me!!");
        assertTrue(maze.movePlayer(Direction.RIGHT));
        // Player should now be standing on hint tile
        HintTile hintTile = (HintTile) tiles[3][7];
        assertEquals("You are Big Gay. Stop standing on me!!", hintTile.getMessage());
        assertEquals("You are Big Gay. Stop standing on me!!", maze.getHintMessage());
        assertEquals(new Coordinate(3, 7), player.getCoordinate());
    }

    /**
     * Test to see if player does not have ice boots and slides on ice into the goal
     */
    @Test
    public void testIceBlockSlidingOntoGoal() {
        tiles[3][4] = new Tile(new Coordinate(3, 4), Tile.TileType.ICE);
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        tiles[3][7] = new Tile(new Coordinate(3, 7), Tile.TileType.GOAL);
        assertTrue(maze.movePlayer(Direction.RIGHT));
        assertEquals(new Coordinate(3, 7), player.getCoordinate());
        assertTrue(maze.isGoalReached());
    }

    /**
     * Test to see if player does not have ice boots and slides into a floor tile with a key
     */
    @Test
    public void testIceBlockSlidingOntoKey() {
        tiles[3][4] = new Tile(new Coordinate(3, 4), Tile.TileType.ICE);
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        tiles[3][7] = new Tile(new Coordinate(3, 7), Tile.TileType.FLOOR);
        Key key = new Key(BasicColor.YELLOW);
        tiles[3][7].setEntity(key);
        assertTrue(maze.movePlayer(Direction.RIGHT));
        assertEquals(new Coordinate(3, 7), player.getCoordinate());
        assertTrue(player.isInInventory(key));
        assertTrue(player.getInventory().contains(key));
    }

    /**
     * Test to see if player does not have ice boots and slides into a floor tile with treasure
     */
    @Test
    public void testIceBlockSlidingOntoTreasure() {
        tiles[3][4] = new Tile(new Coordinate(3, 4), Tile.TileType.ICE);
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        tiles[3][7] = new Tile(new Coordinate(3, 7), Tile.TileType.FLOOR);
        Treasure treasure = new Treasure();
        tiles[3][7].setEntity(treasure);
        assertTrue(maze.movePlayer(Direction.RIGHT));
        assertEquals(new Coordinate(3, 7), player.getCoordinate());
        assertTrue(Treasure.allCollected());
    }

    /**
     * Test to see if player does not have ice boots and slides into a floor tile with treasure
     */
    @Test
    public void testIceBlockSlidingIntoLockedTreasureDoor() {
        tiles[3][4] = new Tile(new Coordinate(3, 4), Tile.TileType.ICE);
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        tiles[3][7] = new Tile(new Coordinate(3, 7), Tile.TileType.FLOOR);
        TreasureDoor treasureDoor = new TreasureDoor();
        tiles[3][7].setEntity(treasureDoor);
        Treasure treasure = new Treasure();
        tiles[3][8].setEntity(treasure);
        assertTrue(maze.movePlayer(Direction.RIGHT));
        assertEquals(new Coordinate(3, 6), player.getCoordinate());
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }

    /**
     * Test to see if player does not have ice boots and slides into a floor tile with treasure
     */
    @Test
    public void testIceBlockSlidingIntoLockedKeyDoor() {
        tiles[3][4] = new Tile(new Coordinate(3, 4), Tile.TileType.ICE);
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        tiles[3][7] = new Tile(new Coordinate(3, 7), Tile.TileType.FLOOR);
        KeyDoor keyDoor = new KeyDoor(BasicColor.YELLOW);
        tiles[3][7].setEntity(keyDoor);
        assertTrue(maze.movePlayer(Direction.RIGHT));
        assertEquals(new Coordinate(3, 6), player.getCoordinate());
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }
}
