package com.company;

import java.awt.*;

// class that handles the player's ship
public class Spacecraft extends VectorSprite
{
    public Spacecraft()
    {
        // the ship's shape
        shape = new Polygon();

        shape.addPoint(15, 0);
        shape.addPoint(-10, 10);
        shape.addPoint(-10, -10);

        drawShape = new Polygon();

        drawShape.addPoint(15, 0);
        drawShape.addPoint(-10, 10);
        drawShape.addPoint(-10, -10);

        // spawn position
        xposition = 450;
        yposition = 300;

        // acceleration and turning rate
        THRUST = 0.25;
        ROTATION = 0.1;

        // total life points
        HEALTH = 13;

        // if the player ship is interactable with
        active = true;
    }

    // the effects of when the player ship is hit
    public void hit()
    {
        active = false;
        counter = 0;
        HEALTH--;
    }

    // resetting the ship to some state
    public void reset()
    {
        active = true;
        xspeed = 0;
        yspeed = 0;
        angle = 0;
        xposition = 450;
        yposition = 300;
    }

    // accelerating forward
    public void accelerate()
    {
        xspeed += Math.cos(angle) * THRUST;
        yspeed += Math.sin(angle) * THRUST;
    }

    // slowing down
    public void decelerate()
    {
        xspeed -= Math.cos(angle) * THRUST;
        yspeed -= Math.sin(angle) * THRUST;
    }

    // handles turning
    public void rotateRight()
    {
        angle += ROTATION;
    }
    public void rotateLeft()
    {
        angle -= ROTATION;
    }

}
