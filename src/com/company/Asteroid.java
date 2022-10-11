package com.company;

import java.awt.*;
import java.util.Random;

// class for the main enemy type, asteroids
public class Asteroid extends VectorSprite {

    int size; // asteroid size determines how large the asteroid will be
    int ID = -1; // id is for the specific asteroid shapes

    // creates the default asteroid
    public Asteroid()
    {
        size = 3;
        initializeAsteroid();
    }
    // creates an asteroid with specified parameters
    // x for the xposition
    // y for the yposition
    // s for the size of the asteroid
    // id { 1 <= id <= 8} for specific asteroid shapes
    public Asteroid(double x, double y, int s, int id)
    {
        size = s;
        ID = id;
        initializeAsteroid();

        xposition = x;
        yposition = y;
    }

    public void initializeAsteroid()
    {
        AsteroidShape(); // generates random asteroid shapes

        // Commented out: an alternate way to generate random asteroids
        // shape = generateRandomShape();
        // drawShape = Polygon(shape.xpoints, shape.ypoints, shape.npoints);

        double speed, angle;

        speed = Math.random() + 1;
        angle = Math.random() * (2 * Math.PI);

        xspeed = Math.cos(angle) * speed / (size);
        yspeed = Math.sin(angle) * speed / (size);

        double radius;
        radius = Math.random() * 400 + 100;
        angle = Math.random() * (2 * Math.PI);

        xposition = Math.cos(angle) * radius + 450;
        yposition = Math.sin(angle) * radius + 300;

        ROTATION = Math.random() * 0.25 / size;

        active = true;
    }

    // returns the point on the asteroid that is closest to the middle of the screen
    public Point getClosestPointToCenter()
    {
        Point center = new Point(450, 300);
        Point closest = new Point(0, 0);

        for (int i = 0; i < drawShape.npoints; i++)
        {
            double distance = center.distance(drawShape.xpoints[i], drawShape.ypoints[i]);

            if (distance < center.distance(closest))
            {
                closest = new Point(drawShape.xpoints[i], drawShape.ypoints[i]);
            }
        }

        return closest;
    }

    // unused
    // creates an asteroid with randomized points
    public Polygon generateRandomShape()
    {
        int NUM_ASTEROIDS = 2;
        int rng = (int) Math.round(Math.random() * NUM_ASTEROIDS);
        Polygon randShape = new Polygon();

        switch (rng)
        {
            case 0:
                randShape.addPoint(30, 3);
                randShape.addPoint(5, 35);
                randShape.addPoint(-25, 10);
                randShape.addPoint(-17, -15);
                randShape.addPoint(20, -35);
                return randShape;
            case 1:
                randShape.addPoint(32, 1);
                randShape.addPoint(7, 38);
                randShape.addPoint(-20, 13);
                randShape.addPoint(-13, -18);
                randShape.addPoint(19, -36);
                return randShape;
        }

        return randShape;
    }

    // creates an asteroid randomly, or based off of a given id
    // id currently must be between 1 and 8 inclusive
    public void AsteroidShape()
    {
        Random random = new Random();
        int r = random.nextInt(80); //80 without random shape asteroid, 85 without large asteroid, 87 with large asteroid (max)

        if (r < 20 || ID == 1)
        {
            shape = new Polygon();

            shape.addPoint(30 / (4 - size), 3 / (4 - size));
            shape.addPoint(5 / (4 - size), 35 / (4 - size));
            shape.addPoint(-25 / (4 - size), 10 / (4 - size));
            shape.addPoint(-17 / (4 - size), -15 / (4 - size));
            shape.addPoint(20 / (4 - size), -45 / (4 - size));

            drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);
            ID = 1;
        }
        if (20 <= r && 30 > r  || ID == 2)
        {
            shape = new Polygon();

            shape.addPoint(-35 / (4 - size), -1 / (4 - size));
            shape.addPoint(10 / (4 - size), 23 / (4 - size));
            shape.addPoint(35 / (4 - size), 30 / (4 - size));
            shape.addPoint(45 / (4 - size), -2 / (4 - size));
            shape.addPoint(-30 / (4 - size), -40 / (4 - size));

            drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);

            ID = 2;
        }
        if (30 <= r && 40 > r  || ID == 3) {
            shape = new Polygon();

            shape.addPoint(40 / (4 - size), 30 / (4 - size));
            shape.addPoint(5 / (4 - size), 15 / (4 - size));
            shape.addPoint(-5 / (4 - size), 19 / (4 - size));
            shape.addPoint(-35 / (4 - size), -4 / (4 - size));
            shape.addPoint(30 / (4 - size), -10 / (4 - size));

            drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);

            ID = 3;
        }
        if (40 <= r && 50 > r  || ID == 4) {
            shape = new Polygon();

            shape.addPoint(10 / (4 - size), 10 / (4 - size));
            shape.addPoint(20 / (4 - size), 30 / (4 - size));
            shape.addPoint(0 / (4 - size), 40 / (4 - size));
            shape.addPoint(-40 / (4 - size), 20 / (4 - size));
            shape.addPoint(-15 / (4 - size), 10 / (4 - size));

            drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);

            ID = 4;
        }
        if (50 <= r && 60 > r  || ID == 5) {
            shape = new Polygon();

            shape.addPoint(0 / (4 - size), 0 / (4 - size));
            shape.addPoint(-20 / (4 - size), 10 / (4 - size));
            shape.addPoint(0 / (4 - size), 20 / (4 - size));
            shape.addPoint(20 / (4 - size), 10 / (4 - size));
            shape.addPoint(20 / (4 - size), -10 / (4 - size));
            shape.addPoint(0 / (4 - size), -20 / (4 - size));
            shape.addPoint(-20 / (4 - size), -10 / (4 - size));

            drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);
            ID = 5;
        }
        if (60 <= r && 70 > r  || ID == 6) {
            shape = new Polygon();

            shape.addPoint(-30 / (4 - size), 0 / (4 - size));
            shape.addPoint(-40 / (4 - size), 30 / (4 - size));
            shape.addPoint(0 / (4 - size), 20 / (4 - size));
            shape.addPoint(30 / (4 - size), 10 / (4 - size));
            shape.addPoint(20 / (4 - size), -30 / (4 - size));
            shape.addPoint(-10 / (4 - size), -23 / (4 - size));
            shape.addPoint(-30 / (4 - size), -40 / (4 - size));

            drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);

            ID = 6;
        }
        if (70 <= r && 80 > r  || ID == 7) {
            shape = new Polygon();

            shape.addPoint(-20 / (4 - size), 0 / (4 - size));
            shape.addPoint(-30 / (4 - size), 60 / (4 - size));
            shape.addPoint(0 / (4 - size), 20 / (4 - size));
            shape.addPoint(30 / (4 - size), 40 / (4 - size));
            shape.addPoint(20 / (4 - size), -30 / (4 - size));
            shape.addPoint(-10 / (4 - size), -20 / (4 - size));
            shape.addPoint(-30 / (4 - size), -40 / (4 - size));

            drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);

            ID = 7;
        }
        if (80 <= r && 85 > r  || ID == 8) {
            shape = new Polygon();

            shape.addPoint(random.nextInt(100), random.nextInt(100));
            shape.addPoint(random.nextInt(100), random.nextInt(100));
            shape.addPoint(random.nextInt(100), random.nextInt(100));
            shape.addPoint(random.nextInt(100), random.nextInt(100));
            shape.addPoint(random.nextInt(100), random.nextInt(100));
            shape.addPoint(random.nextInt(100), random.nextInt(100));

            drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);

            ID = 8;
        }
    }

    public int getScore()
    {
        switch(size) {
            case 3:
                return 100;
            case 2:
                return 200;
            case 1:
                return 300;
            default:
                    return 0;
        }
    }

    // updates position and asteroid orientation
    public void updatePosition() {
        angle += ROTATION;
        super.updatePosition();
    }

    // teleports the asteroid to the other side if it goes off screen
    public void wraparound() {
        int offset = 35;

        if (xposition < 0 - offset) {
            xposition = 900 + offset;
        }
        if (xposition > 900 + offset) {
            xposition = 0 - offset;
        }
        if (yposition < 0 - offset) {
            yposition = 600 + offset;
        }
        if (yposition > 600 + offset) {
            yposition = 0 - offset;
        }
    }

}
