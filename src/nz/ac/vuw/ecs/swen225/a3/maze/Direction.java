package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * Directions for the four ways you can move in Cluedo.
 * 
 * @author
 */
public enum Direction {
	/**
	 * Up
	 */
	UP, 
	/**
	 * Right
	 */
	RIGHT, 
	/**
	 * Down
	 */
	DOWN, 
	/**
	 * Left
	 */
	LEFT;

	/**
	 * Inverse direction used for movements.
	 *
	 * @return inverse
	 */
	public Direction inverse() {

		switch (this) {
		case UP:
			return DOWN;

		case RIGHT:
			return LEFT;

		case DOWN:
			return UP;

		default:
			return RIGHT;
		}
	}
	
	public static Direction getFacing(Coordinate from, Coordinate to) {
		int fromRow = from.getRow();
		int fromCol = from.getCol();
		
		if (to.getRow() == fromRow && to.getCol() != fromCol) {
			return to.getCol() < fromCol ? LEFT : RIGHT;
		} else if (to.getRow() != fromRow && to.getCol() == fromCol) {
			return to.getRow() < fromRow ? UP : DOWN;
		}
		
		return null;
	}

}