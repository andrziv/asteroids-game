package com.company;

import java.awt.*;

// parent vectorsprite shape class
public class VectorSprite
{
    Polygon shape, drawShape;

    int HEALTH;

    double xposition;
    double yposition;
    double angle;
    double xspeed;
    double yspeed;

    double THRUST; // acceleration of the ship
    double ROTATION; //amount to rotate by

    boolean active;

    int counter = 0;

    // empty constructor for inheritance
    public VectorSprite() {}

    // default movement and direction function
    public void updatePosition()
    {
        counter++;

        xposition += xspeed;
        yposition += yspeed;

        wraparound();

        int x, y;

        // updating all of the shape's points to simulate movement
        for (int i = 0; i < shape.npoints; i++)
        {
            x = (int) Math.round(shape.xpoints[i] * Math.cos(angle) - shape.ypoints[i] * Math.sin(angle));
            y = (int) Math.round(shape.xpoints[i] * Math.sin(angle) + shape.ypoints[i] * Math.cos(angle));

            drawShape.xpoints[i] = x;
            drawShape.ypoints[i] = y;
        }

        drawShape.invalidate();
        drawShape.translate((int) xposition, (int) yposition); // moves the object to this location
    }

    // if an object's center goes beyond the screen boundary, teleport it to the opposite side
    public void wraparound()
    {
        if (xposition < 0)
        {
            xposition = 900;
        }
        if (xposition > 900)
        {
            xposition = 0;
        }
        if (yposition < 0)
        {
            yposition = 600;
        }
        if (yposition > 600)
        {
            yposition = 0;
        }
    }

    // draws the object for the player to see
    public void paint(Graphics g)
    {
        g.drawPolygon(drawShape);
    }
}
