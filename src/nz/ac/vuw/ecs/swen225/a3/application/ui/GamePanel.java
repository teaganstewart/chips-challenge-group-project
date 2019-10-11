package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

import javax.swing.*;
import java.awt.*;

/**
 * The JPanel that displays the board, uses a grid layout and JPanels
 * that are selected in Render.
 * 
 * @author Meng Veng Taing - 300434816, Teagan Stewart - 300407769
 *
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Render render;

	/**
	 * The constructor for the GamePanel
	 * @param game
	 */
	public GamePanel(Game game) {
		this.setLayout(new GridLayout(9, 9, 0, 0));
		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 20));
		game.getMaze().setTiles(game.getMaze().getTiles());	
		render = new Render(game,game.getMaze());
		game.setRender(render);
		drawBoard();

		setVisible(true);
	}

	/**
	 * Clears the game panel so it can be redrawn.
	 */
	public void clearBoard() {
		for (int y = 0; y < 9; y++) {
			for (int x1 = 0; x1 < 9; x1++) {
				this.remove(0);
			}
		}
	}

	/**
	 * Draws the tiles that can be seen and creates the rest of the board.
	 */
	public void drawBoard() {
		render.createGrid();
		JLabel[][] visibleBoard = render.getVisibleBoard();

		for (int y = 0; y < 9; y++) {
			for (int x1 = 0; x1 < 9; x1++) {

				this.add(visibleBoard[y][x1]);

			}
		}
	}
	
	
}
