package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The HintTile class, which allows the player to view a hint message. This class may be viewed
 * as extraneous as it only holds a string, but in my opinion it helps keep the classes clean.
 * 
 * @author Ethan Munn
 *
 */
public class HintTile extends Tile {

	private final String message;
	
	/**
	 * 		The tile the hint is on
	 * @param message
	 * 		The message to display upon this hint being touched.
	 */
	public HintTile(Coordinate coord, String message) {
		super(coord, TileType.HINT);
		this.message = message;
	}

	/**
	 * Gets the helpful hint so the player knows what to do.
	 * @return
	 * 		The message
	 */
	public String getMessage() {
		return message;
	}
	
}