package com.company;


import java.awt.*;
import java.util.Random;

// class for all of the spawnable powerups
public class Powerups extends Bullet
{
    Random random = new Random();

    // creates the interactable powerup box
    public Powerups()
    {
        shape = new Polygon();
        shape.addPoint(-5, 15);
        shape.addPoint(5, 15);
        shape.addPoint(15, 5);
        shape.addPoint(15, -5);
        shape.addPoint(5, -15);
        shape.addPoint(-5, -15);
        shape.addPoint(-15, -5);
        shape.addPoint(-15, 5);

        drawShape = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);

        xposition = random.nextInt(801) + 50;
        yposition = random.nextInt(501) + 50;

        active = true;
    }

    // handles the positioning of the bullets
    @Override
    public void updatePosition()
    {
        counter++;

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
        drawShape.translate((int) xposition, (int) yposition);
    }

    // resets the weapon shot to the default, standard one
    public String ResetFire()
    {
        DOES_PIERCE = false;
        SHOT_RATE = 5;
        LIFE_SPAN = 40;

        return "NORMAL";
    }

    // returns a powerup to replace the ship's weapon
    public String PowerupFunction()
    {
        int rng = random.nextInt(7);

        switch (rng) {
            case (0) :
            {
                DOES_PIERCE = false;
                SHOT_RATE = 1;
                LIFE_SPAN = 50;

                return "MINIGUN";
            }
            case (1) :
            {
                DOES_PIERCE = true;
                SHOT_RATE = 80;
                LIFE_SPAN = 40;

                return "RAILGUN";
            }
            case (2) :
            {
                DOES_PIERCE = true;
                SHOT_RATE = 240;
                LIFE_SPAN = 80;

                return "RAZER_CANNON";
            }
            case (3) :
            {
                DOES_PIERCE = false;
                SHOT_RATE = 0;
                LIFE_SPAN = 100;

                return "FLAMETHROWER";
            }
            case (4) :
            {
                DOES_PIERCE = false;
                SHOT_RATE = 150;
                LIFE_SPAN = 100;

                return "BAZOOKA";
            }
            case (5) :
            {
                DOES_PIERCE = false;
                SHOT_RATE = 300;
                LIFE_SPAN = 100;

                return "SUPER_BOMB";
            }
            case (6) :
            {
                DOES_PIERCE = true;
                SHOT_RATE = 0;
                LIFE_SPAN = 30;

                return "HYPER_BEAM";
            }
            default :
                return "NORMAL";
        }
    }
}
