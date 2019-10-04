package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * A class that helps determine where they player should go and where they
 * should be, also helps to tell what direction to render the player.
 * 
 * @author Teagan
 *
 */
public abstract class Moveable {

	private Coordinate coordinate;
	// Initialize direction to avoid null pointers and simulate realistic gameplay
	private Direction direction = Direction.DOWN;
	private Direction lastDirection;

	/**
	 * @param coordinate
	 * 
	 *                   Creates a Movable object
	 */
	public Moveable(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * @param entity
	 * @return validity
	 */
	public abstract boolean canWalkOn(Entity entity);

	/* Getters and Setters */

	/**
	 * @return Returns the direction that the player is facing.
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param dir The direction that you want the player to be facing.
	 */
	public void setDirection(Direction dir) {
		// Update last direction first
		lastDirection = direction;
		direction = dir;
	}

	/**
	 * @return Returns the direction that the player was facing.
	 */
	public Direction getLastDirection() {
		return lastDirection;
	}

	/**
	 *
	 * The next position that player would move to.
	 * 
	 * @return coordinate
	 */
	public Coordinate getNextPos() {

		// This is not needed as the Direction will never be null. I also took this
		// out to increase code coverage
//		if (direction == null)
//			return null;

		int col = coordinate.getCol();
		int row = coordinate.getRow();

		switch (direction) {
		case DOWN:
			return new Coordinate(row + 1, col);
		case UP:
			return new Coordinate(row - 1, col);
		case LEFT:
			return new Coordinate(row, col - 1);
			// RIGHT
			// This default is used to increase code coverage
		default:
			return new Coordinate(row, col + 1);
		}

	}

	/**
	 * The direction that the player was at before it moved.
	 * 
	 * @return coordinate
	 */
	public Coordinate getPrevPos() {
		// This is not needed as the Direction will never be null. I also took this
		// out to increase code coverage
//		if (direction == null)
//			return null;

		int col = coordinate.getCol();
		int row = coordinate.getRow();

		switch (direction.inverse()) {
		case DOWN:
			return new Coordinate(row + 1, col);
		case UP:
			return new Coordinate(row - 1, col);
		case LEFT:
			return new Coordinate(row, col - 1);
			// RIGHT
			// This default is used to increase code coverage
		default:
			return new Coordinate(row, col + 1);
		}

	}

	/**
	 * @return coordinate
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * @param c
	 */
	public void setCoordinate(Coordinate c) {
		this.coordinate = c;
	}

}
