package com.company;

import java.awt.*;
import java.util.Random;

// class that handles the debris particles when an asteroid gets shot
public class Debris extends VectorSprite
{
    public Debris(double posX, double posY)
    {
        // debris shape
        shape = new Polygon();
        shape.addPoint(1, 1);
        shape.addPoint(1, -1);
        shape.addPoint(-1, 1);
        shape.addPoint(-1, -1);

        drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints); // creating the debris polygon

        // debris movement and positioning
        int RADIUS = 8;
        xposition = posX + Math.cos(angle) * RADIUS;
        yposition = posY + Math.cos(angle) * RADIUS;

        angle = Math.random() * (2 * Math.PI);

        THRUST = 10;
        xspeed = Math.cos(angle) * THRUST;
        yspeed = Math.sin(angle) * THRUST;

        // lets the debris be shown and interactable
        active = true;
    }
}
