package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Objects;

/**
 * The Coordinate class, purely to store coordinates of objects in a simpler
 * way. Comes with an overridden equals method, as well as getters for the row
 * and col. This should never be reused, as it is purely for storing a row and
 * col, so no setters are necessary.
 *
 * @author Ethan Munn
 *
 */
public class Coordinate implements Saveable {

	private final int row;
	private final int col;

	/**
	 * Sets up a brand new Coordinate.
	 * 
	 * @param row the row
	 * @param col the col
	 */
	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Returns a row.
	 * 
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns a column.
	 * 
	 * @return the column
	 */
	public int getCol() {
		return col;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + row + ", " + col + "]";
	}

	@Override
	public JsonObject toJSON() {
		JsonObject coord = Json.createObjectBuilder().add("row", row).add("col", col).build();

		return coord;
	}

	@Override
	public int hashCode() {
		return Objects.hash(row, col);
	}
}
