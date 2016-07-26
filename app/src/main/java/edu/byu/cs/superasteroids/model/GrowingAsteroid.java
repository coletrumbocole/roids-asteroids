package edu.byu.cs.superasteroids.model;

/**
 * Created by ctrumbo on 7/12/16.
 * Defines growth behavior and all other GrowingAsteroid data and behavior.
 */
public class GrowingAsteroid extends Asteroid{
    private int grow_speed;

    /**
     * @param asteroidIn: provides the type and all other data for construction.
     */
    public GrowingAsteroid(Asteroid asteroidIn){
        super(asteroidIn.id, asteroidIn.name, asteroidIn.image, asteroidIn.width, asteroidIn.height, asteroidIn.type);
        this.grow_speed = 0;
    }
}
