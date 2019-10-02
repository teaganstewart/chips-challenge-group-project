package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

	private Game game;
	private Render render;

	public GamePanel(Game game) {
		this.game = game;
		this.setLayout(new GridLayout(9, 9, 0, 0));
		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 20));
		game.getMaze().setTiles(game.getMaze().getTiles());
		game.getMaze().setGame(game);	
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
