package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    /**
     * Testing basic player direction
     */
    @Test
    public void testPlayerDirection() {
        Player player = new Player(new Coordinate(0,0));
        // Player starts facing right
        assertEquals(Direction.RIGHT, player.getDirection());
        assertNull(player.getLastDirection());
    }

    /**
     * Testing basic player direction
     */
    @Test
    public void testInventory() {
        Player player = new Player(new Coordinate(0, 0));
        // Test setting inventory
        player.setInventory(new ArrayList<Entity>(Arrays.asList(new Key(BasicColor.BLUE))));

        Key key = new Key(BasicColor.YELLOW);
        assertTrue(player.addToInventory(key));

        assertEquals(key, player.getInventoryAt(1));
        assertTrue(player.removeInventoryAt(0));
        assertEquals(key, player.getInventoryAt(0));
    }

}
