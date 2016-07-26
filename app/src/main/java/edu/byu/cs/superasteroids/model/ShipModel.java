package edu.byu.cs.superasteroids.model;

/**
 * Created by ctrumbo on 7/12/16.
 *
 * Holds info about Cannon, Engine, MainBody, ExtraPart, and PowerCore and makes it possible for all of them to function together porperly during game play.
 */
public class ShipModel extends MovingObject{
    private Cannon cannon;
    private ExtraPart extraPart;
    private Engine engine;
    private PowerCore powerCore;
    private MainBody mainBody;

}
