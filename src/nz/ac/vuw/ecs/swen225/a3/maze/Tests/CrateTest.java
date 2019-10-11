package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Josh
 *         <p>
 *         Class which contains tests for the Crate
 */
class CrateTest {

    private Tile[][] tiles;
    private Player player;
    private Maze maze;
    private List<Crate> crateList = new ArrayList<>();
    private Crate crate1;
    private Crate crate2;

    @BeforeEach
    void setUp() {
        tiles = new Tile[9][9];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }
        player = new Player(new Coordinate(3, 3));
        crate1 = new Crate(new Coordinate(5, 3));
        crate2 = new Crate(new Coordinate(5, 5));
        crateList = new ArrayList<Crate>(Arrays.asList(crate1, crate2));

        maze = new Maze(tiles, player, crateList,null);
        // Set the player next to the first crate
        player.setCoordinate(new Coordinate(5,2));
    }

    // Player is created at (3,3). Crates are created at (5,3) and (5,5)

    /**
     * Test to see if player pushes a crate onto an empty floor tile
     */
    @Test
    public void testPlayerPushesCrateValid() {
        // Assert that crates can't walk, haha
        assertFalse(crate1.canWalkOn(new Key(BasicColor.YELLOW)));
        assertEquals(new Coordinate(5, 3), crate1.getCoordinate());
        assertTrue(maze.movePlayer(Direction.RIGHT));
        // Player moves crate1. Check Player and crate1 positions
        assertEquals(new Coordinate(5, 3), player.getCoordinate());
        assertEquals(new Coordinate(5, 4), crate1.getCoordinate());
        assertEquals("Crate: [5, 4] moving to [5, 5]", crate1.toString());
    }

    /**
     * Test to see if player can't push a crate into another crate
     */
    @Test
    public void testPlayerPushesCrateIntoAnotherCrate() {
        // Player moves crate1 next to crate2
        assertTrue(maze.movePlayer(Direction.RIGHT));
        // Player tries to move crate1 into crate2
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }

    /**
     * Test to see if player can't push a crate into a wall
     */
    @Test
    public void testPlayerPushesCrateIntoWall() {
        tiles[5][4] = new Tile(new Coordinate(5,4), Tile.TileType.WALL);
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }

    /**
     * Test to see if player can't push a crate into a HintTile
     */
    @Test
    public void testPlayerPushesCrateIntoHintTile() {
        tiles[5][4] = new Tile(new Coordinate(5,4), Tile.TileType.HINT);
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }

    /**
     * Test to see if player can't push a crate into an IceTile
     */
    @Test
    public void testPlayerPushesCrateIntoIceTile() {
        tiles[5][4] = new Tile(new Coordinate(5,4), Tile.TileType.ICE);
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }

    /**
     * Test to see if player can't push a crate into a FireTile
     */
    @Test
    public void testPlayerPushesCrateIntoFireTile() {
        tiles[5][4] = new Tile(new Coordinate(5,4), Tile.TileType.FIRE);
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }

    /**
     * Test to see if player can't push a crate into a Goal Tile
     */
    @Test
    public void testPlayerPushesCrateIntoGoalTile() {
        tiles[5][4] = new Tile(new Coordinate(5,4), Tile.TileType.GOAL);
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }

    /**
     * Test to see if player can't push a crate into an Entity
     */
    @Test
    public void testPlayerPushesCrateIntoEntity() {
        // Test all kinds of entity
        tiles[5][4].setEntity(new FireBoots());
        assertFalse(maze.movePlayer(Direction.RIGHT));
        tiles[5][4].setEntity(new Key(BasicColor.YELLOW));
        assertFalse(maze.movePlayer(Direction.RIGHT));
        tiles[5][4].setEntity(new Treasure());
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }

    /**
     * Test to see if player can't push a crate into a Door
     */
    @Test
    public void testPlayerPushesCrateIntoDoor() {
        // Test all kinds of Door
        tiles[5][4].setEntity(new KeyDoor(BasicColor.YELLOW));
        assertFalse(maze.movePlayer(Direction.RIGHT));
        tiles[5][4].setEntity(new TreasureDoor());
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }
}