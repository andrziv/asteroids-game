package com.company;

import javax.swing.*;
import java.awt.*;

// this class handles the ui and what the player sees
public class Window extends JPanel
{
    private Game game;

    // list of numbers for the health/timer bars CHANGE TO FITTING NAMES LATER
    int num = 1;
    int num2 = 1;

    public Window(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(900, 600));
        setBackground(Color.BLACK);
    }

    // ----------------- ADD ALL GRAPHICS HERE! --------------
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // paints the backgrounds and sets the general shape colour
        game.offg.setColor(Color.BLACK);
        game.offg.fillRect(0, 0, 900, 600);
        game.offg.setColor(Color.GREEN);

        // keeps painting the ship while it is "alive"
        if (game.ship.active == true)
        {
            game.ship.paint(game.offg);
        }

        // paints asteroids
        for (int i = 0; i < game.asteroidList.size(); i++)
        {
            game.offg.setColor(Color.GREEN);

            // sets the color for the asteroids based on their size, fix later so it's cleaner
            if (game.asteroidList.get(i).size == 2)
            {
                game.offg.setColor(Color.YELLOW);
            }
            if (game.asteroidList.get(i).size == 1)
            {
                game.offg.setColor(Color.RED);
            }

            game.asteroidList.get(i).paint(game.offg); // paints the asteroid shape
        }

        // paints the elite enemies
        for (int i = 0; i < game.bossList.size(); i++)
        {
            game.offg.setColor(Color.GREEN);
            game.bossList.get(i).paint(game.offg); // paints the elite enemy shape

            if (game.bossList.size() == 1)
            {
                game.offg.setColor(Color.GREEN);
                num = (int) (500*game.bossHealthPercent);
                game.offg.drawRect(200, 50, 500, 25);
                game.offg.drawRect(200, 50, num, 25);
            }
        }

        //paint the bullet
        for (int i = 0; i < game.bulletList.size(); i++)
        {
            game.offg.setColor(Color.GREEN);
            game.bulletList.get(i).paint(game.offg);
        }
        for (int i = 0; i < game.specialBulletList.size(); i++)
        {
            game.offg.setColor(Color.GREEN);
            game.specialBulletList.get(i).paint(game.offg);
        }

        //paint the fragments
        for (int i = 0; i < game.fragmentsList.size(); i++)
        {
            game.offg.setColor(Color.GREEN);
            game.fragmentsList.get(i).paint(game.offg);
        }

        // paints the debris
        for (int i = 0; i < game.debrisList.size(); i++)
        {
            game.offg.setColor(Color.ORANGE);
            game.debrisList.get(i).paint(game.offg);
        }

        // paints the powerups
        for (int i = 0; i < game.powerupList.size(); i++)
        {
            game.offg.setColor(Color.MAGENTA);
            game.powerupList.get(i).paint(game.offg);
        }

        // paints the powerup indicator
        game.offg.setColor(Color.GREEN);
        game.offg.setFont(new Font(null, Font.BOLD, 16));
        game.offg.drawString("POWERUP: " + game.powerupTypeActive, 650, 580);

        if (game.powerupActivated == true)
        {
            game.offg.setColor(Color.GREEN);
            num = (int) (100*game.powerUpTimeLeftPercent);
            game.offg.drawRect(650, 540, 100, 20);
            game.offg.drawRect(650, 540, num, 20);
        }

        /* draw the game over winning screen
        if (game.asteroidList.isEmpty())
        {
            game.offg.setColor(Color.GREEN);
            game.offg.setFont(new Font(null, Font.BOLD, 25));
            game.offg.drawString("Game Over - You Win", 350, 300);
        } */

        // draw the game over losing screen
        if (game.ship.HEALTH == 0)
        {
            game.offg.setColor(Color.GREEN);
            game.offg.setFont(new Font(null, Font.BOLD, 25));
            game.offg.drawString("Game Over - You Lose", 350, 300);
        }

        // draw the life counter
        game.offg.setColor(Color.GREEN);
        game.offg.setFont(new Font(null, 0, 16));
        game.offg.drawString("LIVES: " + game.ship.HEALTH, 20, 580);

        // draw the score counter
        game.offg.setColor(Color.GREEN);
        game.offg.setFont(new Font(null, 0, 16));
        game.offg.drawString("SCORE: " + game.SCORE, 400, 580);

        g.drawImage(game.offscreen, 0, 0, this);
        repaint();
    }

}
