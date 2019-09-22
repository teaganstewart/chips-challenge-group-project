package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Moveable {

	private int col, row;
	// Initialize direction to avoid null pointers and simulate realistic gameplay
	private Direction direction = Direction.RIGHT;
	private Direction lastDirection;
	
	public Moveable(int row, int col) {
		this.row = row;
		this.col = col;
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
		// Update last direction first
		lastDirection = direction;
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
	 * 		The next position that player would move to.
	 */
	public Coordinate getNextPos() {
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
	 * @param dir
	 * 		The direction that the player was at before it moved.
	 */
	public Coordinate getPrevPos() {
		switch(direction.inverse()) {
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

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
