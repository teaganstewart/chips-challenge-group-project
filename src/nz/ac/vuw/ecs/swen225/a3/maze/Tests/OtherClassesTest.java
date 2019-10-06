package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Josh
 *         <p>
 *         Class which contains tests for small classes
 */
public class OtherClassesTest {

    /**
     * Testing KeyDoor
     */
    @Test
    public void keyDoor() {
        Tile tile = new Tile(new Coordinate(0,0), Tile.TileType.FLOOR);
        KeyDoor keyDoor = new KeyDoor(BasicColor.YELLOW);
        tile.setEntity(keyDoor);
        assertFalse(keyDoor.onTouch(null));
    }

    /**
     * Testing TreasureDoor
     */
    @Test
    public void treasureDoor() {
        Tile tile = new Tile(new Coordinate(0,0), Tile.TileType.FLOOR);
        TreasureDoor treasureDoor = new TreasureDoor();
        tile.setEntity(treasureDoor);
        assertFalse(treasureDoor.onTouch(null));
    }
}
