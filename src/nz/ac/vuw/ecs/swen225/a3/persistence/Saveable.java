package nz.ac.vuw.ecs.swen225.a3.persistence;

import javax.json.JsonObject;

/**
 * Saveable class provides methods that have to be
 * implemented on objects that will have to be saved as a JSON.
 *
 * @author Matt Rothwell
 */
public interface Saveable {

    /**
     * Returns a JSON formatted string for the object this is implemented on.
     * @return JSON representation of an object.
     */
    JsonObject toJSON();

}
