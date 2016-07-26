package edu.byu.cs.superasteroids.model;

import java.util.ArrayList;

/**
 * Created by ctrumbo on 7/12/16.
 *
 * Holds info about the area the ship can travel in, the asteroids, the hint, the objects.
 * Defines behavior for completing a level.
 */
public class Level {
    private int id;
    private int number;
    private String title;
    private String hint;
    private int width;
    private int height;
    private String music;
    private ArrayList<LevelObject> objects;
    private ArrayList<Asteroid> asteroids;

    public Level(int id, int number, String title, String hint,
                 int width, int height, String music,
                 ArrayList<Asteroid> asteroids, ArrayList<LevelObject> objects) {
        this.id = id;
        this.number = number;
        this.title = title;
        this.hint = hint;
        this.width = width;
        this.height = height;
        this.music = music;
        this.asteroids = asteroids;
        this.objects = objects;
    }


    /**
     * calcualtes whether the level has been cleared, which is when all the asteroids are gone.
     * @return true if beaten.
     */
    private boolean isBeaten() {
        return false;
    }

}
