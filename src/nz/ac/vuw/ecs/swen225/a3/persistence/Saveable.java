package nz.ac.vuw.ecs.swen225.a3.persistence;

/**
 * Saveable class provides methods that have to be
 * implemented on objects that will have to be saved.
 *
 * @author Matt Rothwell
 */
public interface Saveable {

    /**
     * Returns a JSON formatted string for the object this is implemented on.
     * @return JSON representation of an object.
     */
    String toJSON();

}
