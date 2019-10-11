package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.maze.Coordinate;

/**
 *  Class which contains most tests that test the Coordinate class
 *  
 *  @author Josh O'Hagan - 300442801
 */
public class CoordinateTest {
	
	/**
	 * Testing the toString() method
	 */
	@Test
	public void testToString() {
		Coordinate coord = new Coordinate(3, 6);
		assertEquals("[" + "3" + ", " + "6" + "]", coord.toString());
	}
}
