package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Moveable {

	private Coordinate coordinate;
	private Direction direction;
	private Direction lastDirection;

	public Moveable(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public abstract boolean canWalkOn(Entity entity);

	/*Getters and Setters*/

	/**
	 * @return
	 * 		Returns the direction that the player is facing.
	 */
	public Direction getDirection(){
		return direction;
	}

	/**
	 * @param dir
	 * 		The direction that you want the player to be facing.
	 */
	public void setDirection(Direction dir){
		direction = dir;
	}

	/**
	 * @return
	 * 		Returns the direction that the player was facing.
	 */
	public Direction getLastDirection(){
		return lastDirection;
	}

	/**
	 * @param dir
	 * 		The direction that you want the player to have been facing.
	 */
	public void setLastDirection(Direction dir){
		lastDirection = dir;
	}

	/**
	 *
	 * 		The next position that player would move to.
	 */
	public Coordinate getNextPos() {

		int col = coordinate.getCol();
		int row = coordinate.getRow();

		switch(direction) {
			case DOWN:
				return new Coordinate(row + 1, col);
			case UP:
				return new Coordinate(row - 1, col);
			case LEFT:
				return new Coordinate(row, col - 1);
			case RIGHT:
				return new Coordinate(row, col + 1);
			default:
				return null;
		}

	}

	/**
	 *
	 * 		The direction that the player was at before it moved.
	 */
	public Coordinate getPrevPos() {
		int col = coordinate.getCol();
		int row = coordinate.getRow();

		switch(lastDirection.inverse()) {
			case DOWN:
				return new Coordinate(row - 1, col);
			case UP:
				return new Coordinate(row + 1, col);
			case LEFT:
				return new Coordinate(row, col + 1);
			case RIGHT:
				return new Coordinate(row, col - 1);
			default:
				return null;
		}

	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	@Deprecated
	public int getRow() {
		return coordinate.getRow();
	}

	@Deprecated
	public void setRow(int row) {
		this.coordinate = new Coordinate(row , this.coordinate.getCol());
	}

	@Deprecated
	public int getCol() {
		return coordinate.getCol();
	}

	@Deprecated
	public void setCol(int col) {
		this.coordinate = new Coordinate(this.coordinate.getRow(), col);
	}
}

