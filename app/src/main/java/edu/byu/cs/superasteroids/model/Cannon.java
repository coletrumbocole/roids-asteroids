package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by ctrumbo on 7/12/16.
 *
 * The ship's cannon - determines the laser, and handles the attacking.
 * Constructed during ship builder.
 */
public class Cannon extends MovingObject {
    private Laser laser;
    private PointF attachPoint;
    private PointF emitPoint;
    private String attackImage;
    private int attackImageWidth;
    private int attackImageHeight;
    private String attackSound;
    private int damage;

    public Cannon(int id, int attachX, int attachY, int emitX, int emitY,
                  String image, int imageWidth, int imageHeight,
                  String attackImage, int attackImageWidth, int attackImageHeight,
                  String attackSound, int damage) {
        this(id, attachX, attachY, emitX, emitY, attackImage, attackImageWidth, attackImageHeight, attackSound, damage, 0f, 0, new RectF(0f,0f,0f,0f), new PointF(0,0), image, imageWidth, imageHeight);
    }


    public Cannon(int id, int attachX, int attachY, int emitX, int emitY,
                  String attackImage, int attackImageWidth, int attackImageHeight,
                  String attackSound, int damage,
                  float direction, int speed, RectF boundary,
                  PointF position, String image, int imageWidth, int imageHeight) {
        super(id, position, image, imageWidth, imageHeight, direction, speed, boundary);
        this.laser = null;
        this.attachPoint = new PointF(attachX, attachY);
        this.emitPoint = new PointF(emitX, emitY);
        this.attackImage = attackImage;
        this.attackImageWidth = attackImageWidth;
        this.attackImageHeight = attackImageHeight;
        this.attackSound = attackSound;
        this.damage = damage;
    }


    public void generateLaser() {

    }


    /**
     * Created by ctrumbo on 7/12/16.
     * Lasers that get shot from Cannons.
     */
    public class Laser extends MovingObject {

        public Laser(int id, PointF position, String image, int imageWidth, int imageHeight, float direction, int speed, RectF boundary) {
            super(id, position, image, imageWidth, imageHeight, direction, speed, boundary);
        }
        /**
         * moves outward from cannon along angle.
         */
        public void shoot(){}

    }


}