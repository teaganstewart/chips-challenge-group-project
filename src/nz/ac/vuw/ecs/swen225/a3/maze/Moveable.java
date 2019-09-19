package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Moveable extends Entity {

	int row, col;
	private Direction direction;
	private Direction lastDirection;
	
	public Moveable(int row, int col, Tile tile) {
		super(row, col, tile);
	}
	
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
	 * @param dir
	 * 		The next position that player would move to.
	 */
	public Coordinate getNextPos() {
		switch(direction) {
		case DOWN:
			return new Coordinate(row, col+1);
		case UP:
			return new Coordinate(row, col-1);
		case LEFT:
			return new Coordinate(row-1, col);
		case RIGHT:
			return new Coordinate(row+1, col);
		default:
			return null;
		}

	}
	
	/**
	 * @param dir
	 * 		The direction that the player was at before it moved.
	 */
	public Coordinate getPrevPos() {
		switch(lastDirection.inverse()) {
		case DOWN:
			return new Coordinate(row, col+1);
		case UP:
			return new Coordinate(row, col-1);
		case LEFT:
			return new Coordinate(row-1, col);
		case RIGHT:
			return new Coordinate(row+1, col);
		default:
			return null;
		}

	}

}
