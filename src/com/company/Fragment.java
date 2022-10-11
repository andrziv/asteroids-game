package com.company;

import java.awt.*;

// fragment class
// fragments are what is created from certain special bullet types
public class Fragment extends VectorSprite
{
    public Fragment(double shipX, double shipY, double shipAngle)
    {
        shape = new Polygon();
        shape.addPoint(1, 1);
        shape.addPoint(1, -1);
        shape.addPoint(-1, 1);
        shape.addPoint(-1, -1);

        drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);

        xposition = shipX;
        yposition = shipY;

        angle = shipAngle;


        THRUST = 10;
        xspeed = Math.cos(angle) * THRUST;
        yspeed = Math.sin(angle) * THRUST;

        active = true;
    }
}
