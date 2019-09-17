package nz.ac.vuw.ecs.swen225.a3.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.maze.*;


public class ChipsChallengeTests {

	//-----------------------------//
	//-------GENERAL TESTS --------//
	//-----------------------------//
	
	@Test
	void extensionsTest() { 
		Player p = new Player(1,2);
		Player p2 = new Player(2,1);
		assertTrue(p.addToInventory(p2));
		
	}
}
