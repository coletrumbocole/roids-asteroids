package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by ctrumbo on 7/12/16.
 * Holds info about position, speed, direction, and boundaries for detecting collisions.
 */
public abstract class MovingObject extends PositionedObject{
    float direction = 0f;
    int speed = 0;
    RectF boundary = new RectF(1f,1f,1f,1f);

    MovingObject(int id, PointF position, String image, int imageWidth, int imageHeight, float direction, int speed, RectF boundary) {
        super(id, position, image, imageWidth, imageHeight);
        this.direction = direction;
        this.speed = speed;
        this.boundary = boundary;
    }

    /**
     * Calculates a new position based on curent position, speed and angle. Detects collisions.
     * @return: true if everything updated, false otherwise
     */
    public boolean update(){return false;}

    public Float getDirection() {
        return direction;
    }

    public void setDirection(Float direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public RectF getBoundary() {
        return boundary;
    }

    public void setBoundary(RectF boundary) {
        this.boundary = boundary;
    }

    RectF createBoundary(int w, int h) {
        return new RectF();
    }
}
