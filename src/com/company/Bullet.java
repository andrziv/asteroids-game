package com.company;

import java.awt.*;

// bullet class for the bullets that can be shot
public class Bullet extends VectorSprite
{
    int SHOT_RATE;
    int LIFE_SPAN = 40;
    boolean DO_RANDOM_ANGLE;
    boolean DOES_PIERCE;

    public Bullet() {}

    // creates a bullet object
    // shipx, shipy and shipangle are the ships coordinates and angle
    // type is the type of bullet that should be created
    public Bullet(double shipX, double shipY, double shipAngle, String type)
    {
        double Speedmod;
        double Shotspread;

        if (type.equals("NORMAL"))
        {
            int[] x = {1, 1, -1, -1};
            int[] y = {1, -1, 1, -1};

            Speedmod = 1;
            DO_RANDOM_ANGLE = true;
            Shotspread = 0.05;

            BulletForm(DO_RANDOM_ANGLE, Shotspread, Speedmod, x, y, shipX, shipY, shipAngle);
        }
        if (type.equals("RAILGUN"))
        {
            int[] x = {-3, 3, 3, -3};
            int[] y = {9, 9, -9, -9};

            Speedmod = 6;
            DO_RANDOM_ANGLE = false;
            Shotspread = 0;

            BulletForm(DO_RANDOM_ANGLE, Shotspread, Speedmod, x, y, shipX, shipY, shipAngle);
        }
        if (type.equals("MINIGUN"))
        {
            int[] x = {1, 1, -1, -1};
            int[] y = {1, -1, 1, -1};

            Speedmod = 0.8;
            DO_RANDOM_ANGLE = false;
            Shotspread = 0.1;

            BulletForm(DO_RANDOM_ANGLE, Shotspread, Speedmod, x, y, shipX, shipY, shipAngle);
        }
        if (type.equals("RAZER_CANNON"))
        {
            int[] x = {0, 0, 10, 6, 0, 0, -10, -6};
            int[] y = {10, 6, 0, 0, -10, -6, 0, 0};

            Speedmod = 0.5;
            DO_RANDOM_ANGLE = false;
            Shotspread = 0;

            ROTATION = Math.random() * 0.1;

            BulletForm(DO_RANDOM_ANGLE, Shotspread, Speedmod, x, y, shipX, shipY, shipAngle);
        }
        if (type.equals("FLAMETHROWER"))
        {
            int[] x = {1, 1, -1, -1};
            int[] y = {1, -1, 1, -1};

            Speedmod = 0.1;

            DO_RANDOM_ANGLE = true;
            Shotspread = 0.5;

            BulletForm(DO_RANDOM_ANGLE, Shotspread, Speedmod, x, y, shipX, shipY, shipAngle);
        }
        if (type.equals("BAZOOKA"))
        {
            int[] x = {0, 3, 4, 4, 3, 2, -2, -3, -4, -4, -3};
            int[] y = {12, 9, 7, -12, -12, -8, -8, -12, -12, 7, 9};

            Speedmod = 0.9;

            DO_RANDOM_ANGLE = true;
            Shotspread = 0.5;

            ROTATION = 0.1;

            BulletForm(DO_RANDOM_ANGLE, Shotspread, Speedmod, x, y, shipX, shipY, shipAngle);
        }
        if (type.equals("SUPER_BOMB"))
        {
            int[] x = {0, 5, 0, -5};
            int[] y = {5, 0, -5, 0};

            Speedmod = 0.4;

            DO_RANDOM_ANGLE = false;
            Shotspread = 0;

            BulletForm(DO_RANDOM_ANGLE, Shotspread, Speedmod, x, y, shipX, shipY, shipAngle);
        }
        if (type.equals("HYPER_BEAM"))
        {
            int[] x = {40, 40, -40, -40};
            int[] y = {40, -40, -40, 40};

            Speedmod = 4;

            DO_RANDOM_ANGLE = false;
            Shotspread = 0;

            BulletForm(DO_RANDOM_ANGLE, Shotspread, Speedmod, x, y, shipX, shipY, shipAngle);
        }

        active = true;
    }

    // sets the bullets shape, and general parameters:
    // ranAng: true creates an inaccurate bullet based off of spread
    //          false creates a straight firing bullet
    // spread: determines how inaccurate the bullet will be if ranAng is true
    // x and y: bullet shape
    // shipX and shipY: the player ship's coordinates
    // shipAngle: the ship's angle
    public void BulletForm (boolean ranAng, double spread, double spdmod, int[] x, int[] y, double shipX, double shipY, double shipAngle)
    {
        BulletShape(x, y);

        xposition = shipX;
        yposition = shipY;

        double rngOffset = Math.random() * spread - spread/2;

        if (ranAng)
        {
            angle = shipAngle + rngOffset;
        } else {
            angle = shipAngle;
        }

        THRUST = 10 * spdmod;
        xspeed = Math.cos(angle) * THRUST;
        yspeed = Math.sin(angle) * THRUST;
    }

    // creates the bullet shape
    public void BulletShape(int[] x, int[] y)
    {
        shape = new Polygon();

        for (int i = 0; i < x.length; i++)
        {
            shape.addPoint(x[i], y[i]);
        }

        drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);
    }

    // updates the bullet position and angle
    public void updatePosition() {
        angle += ROTATION;
        super.updatePosition();
    }
}
