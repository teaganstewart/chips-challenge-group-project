package nz.ac.vuw.ecs.swen225.a3.render.Tests;

import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.application.ui.GamePanel;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.render.*;

/**
 * @author Teagan
 * 
 * Class which contains all tests that test classes in the render directory.
 *
 */
public class RenderTests {
	
	@Test
	void createGridTest() {
		
		Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }

        Player player = new Player(new Coordinate(3, 3));
        Maze maze = new Maze(tiles, player);
        Game game = new Game();
		GamePanel gp = new GamePanel(game);
        Render render = new Render(game, gp, maze);
        JLabel[][] outcome = render.createGrid();
        JLabel[][] expected = new JLabel[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                JLabel floor = new JLabel(new ImageIcon(getClass().getResource("../icons/floor.png")));
                expected[row][col] = floor;
            }
        }
        
       assertEquals(expected[0][0].getIcon().toString(), outcome[0][0].getIcon().toString());
    

  
	}
	
	@Test
	void drawRemovalTest() {
		
		Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < tiles.length; row++){
            for(int col=0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }
        
        Player player = new Player(new Coordinate(3, 3));
        Maze maze = new Maze(tiles, player);
        Game game = new Game();
		GamePanel gp = new GamePanel(game);
        Render render = new Render(game, gp, maze);
		
		render.createGrid();
        GraphicalView gv = new GraphicalView(render, maze);
		gv.drawOnGrid();

	}

}
