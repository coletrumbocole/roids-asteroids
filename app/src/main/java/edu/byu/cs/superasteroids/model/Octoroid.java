package edu.byu.cs.superasteroids.model;

/**
 * Created by ctrumbo on 7/12/16.
 * An asteroid that breaks into smaller asteroids instead of disappearing.
 */
public class Octoroid extends Asteroid {
    private int children;

    /**
     * Creates an Ocotoid from an Asteroid.
     * @param asteroidIn
     */
    public Octoroid(Asteroid asteroidIn){
        super(asteroidIn.id, asteroidIn.name, asteroidIn.image, asteroidIn.width, asteroidIn.height, asteroidIn.type);
        this.children = 0;
    }

    /**
     * creates new NormalAsteroids, as many as this has children.
     */
    private void breakIntoChildren() {}
}
