package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The Coordinate class, purely to store coordinates of objects in a simpler way.
 * Comes with an overridden equals method, as well as getters for the row and col.
 * This should never be reused, as it is purely for storing a row and col, so no
 * setters are necessary.
 * 
 * @author Ethan Munn
 *
 */
public class Coordinate {
	
	private final int row;
	private final int col;
	
	/**
	 * Sets up a brand new Coordinate
	 * @param row
	 * 		the row
	 * @param col
	 * 		the col
	 */
	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Returns a row
	 * @return
	 * 		the row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns a column
	 * @return
	 * 		the column
	 */
	public int getCol() {
		return col;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinate)) return false;
		Coordinate c = (Coordinate) o;
		return c.getRow() == row && c.getCol() == col;
	}
	
	@Override
	public String toString() {
		return "[" + row + ", " + col + "]";
	}
	
}
