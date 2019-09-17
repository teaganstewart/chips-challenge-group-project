package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 *  Directions for the four ways you can move in Cluedo.
 * 
 * @author 
 */
public enum Direction {
	UP, RIGHT, DOWN, LEFT;

	/**
	 * Inverse direction used for movements.
	 *
	 * @return
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

}