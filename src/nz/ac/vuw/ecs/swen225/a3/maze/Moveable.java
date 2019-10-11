package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * A class that helps determine where they player should go and where they
 * should be, also helps to tell what direction to render the player.
 * 
 * @author Teagan Stewart
 *
 */
public abstract class Moveable {

	private Coordinate coordinate;
	// Initialize direction to avoid null pointers and simulate realistic gameplay.
	private Direction direction = Direction.DOWN;
	private Direction lastDirection;

	/**
	 * Creates a Movable object.
	 * 
	 * @param coordinate The starting coordinate of a moveable object.
	 */
	public Moveable(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * Returns whether or not the player can walk on this entity.
	 * 
	 * @param entity the entity checked against
	 * @return whether it can or can't overwrite the entity
	 */
	public abstract boolean canWalkOn(Entity entity);

	/* Getters and Setters */

	/**
	 * The direction the player is facing.
	 * 
	 * @return Returns the direction that the player is facing.
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Sets the direction the player is facing.
	 * 
	 * @param dir The direction that you want the player to be facing.
	 */
	public void setDirection(Direction dir) {
		// Update last direction first
		lastDirection = direction;
		direction = dir;
	}

	/**
	 * The direction the player was facing before the last move.
	 * 
	 * @return Returns the direction that the player was facing.
	 */
	public Direction getLastDirection() {
		return lastDirection;
	}

	/**
	 * The next position that player would move to, in direction player 
	 * is facing.
	 * 
	 * @return coordinate Coordinate of nextPostion
	 */
	public Coordinate getNextPos() {

		if (direction == null)
			return null;

		int col = coordinate.getCol();
		int row = coordinate.getRow();

		switch (direction) {
		case DOWN:
			return new Coordinate(row + 1, col);
		case UP:
			return new Coordinate(row - 1, col);
		case LEFT:
			return new Coordinate(row, col - 1);
		default:
			return new Coordinate(row, col + 1);
		}

	}

	/**
	 * The coordinate that the player was at before it moved.
	 * 
	 * @return coordinate Returns the last coordinate.
	 */
	public Coordinate getPrevPos() {
		if (direction == null)
			return null;

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
		default:
			return new Coordinate(row, col + 1);
		}

	}

	/**
	 * The coordinate that they player is on in the board.
	 * 
	 * @return coordinate Returns the coordinate.
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * Set the coordinate where the player is on the board.
	 * 
	 * @param c The new coordinate of the player.
	 */
	public void setCoordinate(Coordinate c) {
		this.coordinate = c;
	}

}
