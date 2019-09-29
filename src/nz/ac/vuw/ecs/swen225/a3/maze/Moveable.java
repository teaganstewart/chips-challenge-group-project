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
  private Direction direction;
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
    case RIGHT:
      return new Coordinate(row, col + 1);
    default:
      return null;
    }

  }

  /**
   * The direction that the player was at before it moved.
   * 
   * @return coordinate
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
    case RIGHT:
      return new Coordinate(row, col + 1);
    default:
      return null;

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

  @Deprecated
  public int getRow() {
    return coordinate.getRow();
  }

  @Deprecated
  public void setRow(int row) {
    this.coordinate = new Coordinate(row, this.coordinate.getCol());
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
