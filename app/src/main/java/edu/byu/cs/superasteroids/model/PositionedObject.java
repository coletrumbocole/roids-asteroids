package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

/**
 * Created by ctrumbo on 7/12/16.
 * Any object that gets drawn in a position.
 */
public abstract class PositionedObject {
    int id = -1;
    PointF position = new PointF(0,0);
    String image = "";
    int width = 0;
    int height = 0;

    PositionedObject(int id, PointF position, String image, int imageWidth, int imageHeight) {
        this.id = id;
        this.position = position;
        this.image = image;
        this.width = imageWidth;
        this.height = imageHeight;
    }


    /**
     * Makes the object's image visible to the player in the correct position.
     */
    public void draw(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
