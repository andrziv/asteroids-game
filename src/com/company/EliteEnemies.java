package com.company;

import java.awt.*;

// special class for elite enemies/bosses
public class EliteEnemies extends Asteroid
{
    public EliteEnemies()
    {
        // boss shape
        shape = new Polygon();

        shape.addPoint(-250,0);
        shape.addPoint(-100,50);
        shape.addPoint(-150, 150);
        shape.addPoint(-50, 100);
        shape.addPoint(0, 250); //
        shape.addPoint(50, 100);
        shape.addPoint(150, 150);
        shape.addPoint(100, 50);
        shape.addPoint(250, 0);
        shape.addPoint(100, -50);
        shape.addPoint(150, -150);
        shape.addPoint(50, -100);
        shape.addPoint(0, -250);
        shape.addPoint(-50, -100);
        shape.addPoint(-150, -150);
        shape.addPoint(-100, -50);

        drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints); // creating the boss polygon

        // boss movement and positioning
        double speed, angle;

        speed = Math.random() + 1;
        angle = Math.random() * (2 * Math.PI);

        xspeed = Math.cos(angle) * speed;
        yspeed = Math.sin(angle) * speed;

        double radius;
        radius = Math.random() * 400 + 100;
        angle = Math.random() * (2 * Math.PI);

        xposition = Math.cos(angle) * radius + 450;
        yposition = Math.sin(angle) * radius + 300;

        // speed at which the boss rotates
        ROTATION = Math.random() * 0.25;

        // boss health
        HEALTH = 1000;

        active = true;
    }
}
