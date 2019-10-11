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
 * Class which contains tests for the Ice Tile
 *
 * @authors Joshua O'Hagan - 300442801, Ethan Munn - 300367257
 *
 */
public class IceTest {

	private GUI gui;
	private Level level;
    private Maze maze;
    private Tile[][] tiles;
    private Player player;
    private List<Crate> crateList = new ArrayList<>();
    private List<Skeleton> enemiesList = new ArrayList<>();

    /**
     *  Creates a new maze before each test.
     */
    @BeforeEach
    public void setUp() {
    	gui = new GUI();

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
        
        enemiesList = new ArrayList<Skeleton>(Arrays.asList(
        		new Skeleton(new Coordinate(4,3), Direction.UP),
        		new Skeleton(new Coordinate(5,6), Direction.LEFT),
        		new Skeleton(new Coordinate(1,2), Direction.RIGHT)
        ));

        maze = new Maze(tiles, player, crateList,enemiesList);
        level = new Level(-1, maze, System.currentTimeMillis(), 0, 1000);
        gui.getGame().loadSave(level);
        Treasure.reset();

    }

    
	/**
	 * Reset the Treasure class after each test as it contains
	 * static methods that will change it's state.
	 */
	@AfterEach
    public void tearDown() {
        Treasure.reset();
        GUI.stopTimer();
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
        // runs the game.update method to actually update the game and check for ice sliding
        for (int i = 0; i < 3; i++) gui.getGame().update(true, true);
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
        // runs the game.update method to actually update the game and check for ice sliding
        for (int i = 0; i < 3; i++) gui.getGame().update(true, true);
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
        // runs the game.update method to actually update the game and check for ice sliding
        for (int i = 0; i < 2; i++) gui.getGame().update(true, false);
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
        tiles[3][7] = new HintTile(new Coordinate(3, 7), "Test Hint! :)");
        assertTrue(maze.movePlayer(Direction.RIGHT));
        // runs the game.update method to actually update the game and check for ice sliding
        for (int i = 0; i < 3; i++) gui.getGame().update(true, false);
        // Player should now be standing on hint tile
        HintTile hintTile = (HintTile) tiles[3][7];
        assertEquals("Test Hint! :)", hintTile.getMessage());
        assertEquals("Test Hint! :)", maze.getHintMessage());
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
        // runs the game.update method to actually update the game and check for ice sliding
        for (int i = 0; i < 3; i++) gui.getGame().update(true, false);
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
        // runs the game.update method to actually update the game and check for ice sliding
        for (int i = 0; i < 3; i++) gui.getGame().update(true, false);
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
        // runs the game.update method to actually update the game and check for ice sliding
        for (int i = 0; i < 3; i++) gui.getGame().update(true, false);
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
        // runs the game.update method to actually update the game and check for ice sliding
        for (int i = 0; i < 3; i++) gui.getGame().update(true, false);
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
        // runs the game.update method to actually update the game and check for ice sliding
        for (int i = 0; i < 3; i++) gui.getGame().update(true, false);
        assertEquals(new Coordinate(3, 6), player.getCoordinate());
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }

    /**
     * Test to see if player has ice boots!
     */
    @Test
    public void testIceBlockWithBoots() {
    	IceBoots ib = new IceBoots();
        tiles[3][4].setEntity(ib);
        tiles[3][5] = new Tile(new Coordinate(3, 5), Tile.TileType.ICE);
        tiles[3][6] = new Tile(new Coordinate(3, 6), Tile.TileType.ICE);
        tiles[3][7] = new Tile(new Coordinate(3, 7), Tile.TileType.FLOOR);
        assertTrue(maze.movePlayer(Direction.RIGHT));
        // checks the ice boots are in the inventory
        assertTrue(player.isInInventory(ib));
        assertTrue(player.getInventory().contains(ib));
        assertTrue(maze.movePlayer(Direction.RIGHT));
        // runs the game.update method to actually update the game and check for ice sliding
        for (int i = 0; i < 3; i++) gui.getGame().update(true, false);
        // since they have ice boots, should have no effect
        assertEquals(new Coordinate(3, 5), player.getCoordinate());
    }
    
    /**
     * Checks what skeletons can walk on.
     */
    @Test
    public void testSkeletonEntityMovement() {
    	
    	// what the skeleton can walk on
    	assertTrue(enemiesList.get(1).canWalkOn(null));
    	assertTrue(enemiesList.get(1).canWalkOn(new IceBoots()));
    	assertTrue(enemiesList.get(1).canWalkOn(new FireBoots()));
    	assertTrue(enemiesList.get(1).canWalkOn(new Key(BasicColor.GREEN)));
    	assertTrue(enemiesList.get(1).canWalkOn(new Treasure()));
    	
    	// what the skeleton can't walk on
    	assertFalse(enemiesList.get(1).canWalkOn(new TreasureDoor()));
    	assertFalse(enemiesList.get(1).canWalkOn(new KeyDoor(BasicColor.GREEN)));
    }
    
    /**
     * Testing skeleton movement.
     */
    @Test
    public void testWall() {
        tiles[3][4] = new Tile(new Coordinate(3, 4), Tile.TileType.FLOOR);
        enemiesList.get(0).setCoordinate(new Coordinate(2,3));
        tiles[1][3] = new Tile(new Coordinate(1, 3), Tile.TileType.WALL);
        // runs the game.update method to actually update the game and check for ice sliding
        assertEquals(enemiesList.get(0).getDirection(), Direction.UP);
        gui.getGame().update(false, true);
        // Player slides to the next non-ice block
        assertEquals(enemiesList.get(0).getDirection(), Direction.DOWN);
    }
}