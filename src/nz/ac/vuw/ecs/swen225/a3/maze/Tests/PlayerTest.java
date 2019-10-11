package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for testing how the player moves and their inventory.
 * 
 * @author Josh O'Hagan - 300442801
 */
class PlayerTest {

	Player player;

	/*
	* Create a player before each test
	* */
	@BeforeEach
	public void setUp() {
		player = new Player(new Coordinate(0, 0));
	}

	/**
	 * Testing basic player direction
	 */
	@Test
	public void testPlayerDirection() {
		assertEquals(new Coordinate(0,0), player.getStartCoordinate());
		// Player starts facing down
		assertEquals(Direction.DOWN, player.getDirection());
		assertNull(player.getLastDirection());
	}

	/**
	 * Testing addToInventory() method
	 */
	@Test
	public void testAddToInventory() {
		Key key = new Key(BasicColor.YELLOW);
		Key blueKey = new Key(BasicColor.BLUE);
		assertTrue(player.addToInventory(key));
		assertTrue(player.addToInventory(blueKey));
	}

	/**
	 * Testing getInventoryAt() method
	 */
	@Test
	public void testGetInventoryAt() {
		Key key = new Key(BasicColor.YELLOW);
		Key blueKey = new Key(BasicColor.BLUE);
		assertTrue(player.addToInventory(key));
		assertTrue(player.addToInventory(blueKey));

		// Test getting
		assertEquals(key, player.getInventoryAt(0));
		assertEquals(blueKey, player.getInventoryAt(1));
	}

	/**
	 * Testing removeInventoryAt() method
	 */
	@Test
	public void testRemoveInventoryAt() {
		Key key = new Key(BasicColor.YELLOW);
		Key blueKey = new Key(BasicColor.BLUE);
		assertTrue(player.addToInventory(key));
		assertTrue(player.addToInventory(blueKey));

		// Test removing index
		assertEquals(blueKey, player.getInventoryAt(1));
		assertTrue(player.removeInventoryAt(0));
		assertEquals(blueKey, player.getInventoryAt(0));
	}

	/**
	 * Testing setInventory() method
	 */
	@Test
	public void testSetInventory() {
		// Test setting inventory
		Key key = new Key(BasicColor.YELLOW);
		Key blueKey = new Key(BasicColor.BLUE);
		player.setInventory(new ArrayList<Entity>(Arrays.asList(key, blueKey)));
		assertEquals(key, player.getInventoryAt(0));
		assertEquals(blueKey, player.getInventoryAt(1));
	}

	/**
	 * Testing not being able to add any more items when a player's inventory is full
	 */
	@Test
	public void testAddInventoryWhenFull() {
		Key yellowKey = new Key(BasicColor.YELLOW);
		Key blueKey = new Key(BasicColor.BLUE);
		assertTrue(player.addToInventory(blueKey));
		assertTrue(player.addToInventory(yellowKey));
		assertTrue(player.addToInventory(blueKey));
		assertTrue(player.addToInventory(yellowKey));
		assertTrue(player.addToInventory(blueKey));
		assertTrue(player.addToInventory(blueKey));
		assertTrue(player.addToInventory(blueKey));
		assertTrue(player.addToInventory(blueKey));
		// 8 items, inventory is full
		assertFalse(player.addToInventory(blueKey));

		// Take one off
		assertTrue(player.removeInventoryAt(0));
		assertTrue(player.addToInventory(blueKey));
		// Inventory should be full again
		assertFalse(player.addToInventory(blueKey));
	}

}
