package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.application.ui.GUI;

/**
 * Runs the game.
 * @author Meng Veng Taing - 300434816
 *
 */
public class Main {
	
	/**
     * Main method that starts up the entire code.
     * 
     * @param args Needed to be runnable.
     */
    @SuppressWarnings("deprecation")
	public static void main(String args[]) {
        GUI gui = new GUI();    
        gui.enable();
    }
}
