package com.company;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JFrame implements KeyListener, ActionListener
{
    public Window panel;
    Menu menu = new Menu();

    Image offscreen; // an image to be loaded offscreen
    Graphics offg; // graphics objects for the offscreen image

    Random random = new Random();

    Spacecraft ship;
    ArrayList<Asteroid> asteroidList; // list of all asteroids objects
    ArrayList<EliteEnemies> bossList; // list of all hard enemies
    ArrayList<Bullet> bulletList; // list of all bullet objects
    ArrayList<Bullet> specialBulletList; // list of all special bullet objects
    ArrayList<Fragment> fragmentsList; // list of all bullet fragments
    ArrayList<Debris> debrisList; // list of all debris objects
    ArrayList<Powerups> powerupList; // list of all powerup objects
    Powerups powerupFunction;

    int SCORE = 0;

    boolean powerupActivated = false;
    String powerupType;
    String powerupTypeActive = "NORMAL";

    Timer timer;
    int powerupCounter = 0;
    int powerupDeactCounter = 0;
    int SECounter = 0;

    int POWER_UP_DEACTIVATION_TIME = 500;
    int POWERUP_RESPAWN_TIME = 1000;

    double powerUpTimeLeftPercent = 100;

    int worldTimer;

    int RESPAWN_ASTEROID_TIME = 1000;
    boolean BOSS_MODE_ACTIVATED = false;

    double bossHealthPercent = 100;

    double bulletLastPosX;
    double bulletLastPosY;

    AudioUtil au; // tool for using audio functionality

    AudioClip laser;
    AudioClip explosion;
    AudioClip thruster;

    BufferedImage testImage;

    boolean rightKey, leftKey, upKey, downKey, spaceKey;

    int SHIP_SPAWN_DELAY = 50;

    // Initialization function of the window for the game
    public void init() throws MalformedURLException {
        this.setVisible(true);
        this.setSize(900, 600);
        this.setTitle("Asteroids");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        add(this.panel = new Window(this), BorderLayout.CENTER); // add the JPanel window as a component of the Game window

        offscreen = createImage(this.getWidth(), this.getHeight()); // initialize the image as the same dimension as the JFrame
        offg = offscreen.getGraphics();

        ship = new Spacecraft();

        timer = new Timer(20, this);

        asteroidList = new ArrayList(); //new asteroids
        bossList = new ArrayList(); // new hard enemies

        bulletList = new ArrayList(); // new Bullets
        specialBulletList = new ArrayList();

        fragmentsList = new ArrayList(); // new Bullets

        debrisList = new ArrayList(); // new Debris

        powerupList = new ArrayList(); // new Powerups
        powerupFunction = new Powerups();
        powerupType = new String();

        for (int i = 0; i < 6; i++)
        {
            asteroidList.add(new Asteroid());
        }

        rightKey = false;
        leftKey = false;
        upKey = false;
        downKey = false;
        spaceKey = false;

        au = AudioUtil.getInstance();

        laser = Applet.newAudioClip(au.transform(new File("./src/sounds/laser79.wav")));
        explosion = Applet.newAudioClip(au.transform(new File("./src/sounds/explode0.wav")));

        pack(); // Size the window so it fits into our game
        timer.start(); // start the game update timer
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // player controls
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            rightKey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            leftKey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            upKey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            downKey = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            spaceKey = true;
        }
    }

    // stopping acceleration when key released
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            rightKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            leftKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            upKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            downKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            spaceKey = false;
        }
    }

    private void keyCheck()
    {
        if (rightKey)
        {
            ship.rotateRight();
        }
        if (leftKey)
        {
            ship.rotateLeft();
        }
        if (upKey)
        {
            ship.accelerate();
        }
        if (downKey)
        {
            ship.decelerate();
        }
        if (spaceKey & ship.active == true)
        {
            fireBullet();
        }
    }

    // spawns the normal asteroids
    public void spawnAsteroid()
    {
        asteroidList.add(new Asteroid());
    }

    // spawns the boss
    public void spawnBoss()
    {
        bossList.add(new EliteEnemies());
    }

    // checks collision between two sprites
    // return true if an object is partly within another
    // return false if the two objects arent overlapping
    public boolean collision(VectorSprite v1, VectorSprite v2)
    {
        boolean isInside;

        for (int i = 0; i < v2.drawShape.npoints; i++)
        {
            isInside = v1.drawShape.contains(v2.drawShape.xpoints[i], v2.drawShape.ypoints[i]);

            if (isInside)
            {
                return true;
            }
        }
        for (int i = 0; i < v1.drawShape.npoints; i++)
        {
            isInside = v2.drawShape.contains(v1.drawShape.xpoints[i], v1.drawShape.ypoints[i]);

            if (isInside)
            {
                return true;
            }
        }

        return false;
    }

    // computes all cases of collisions between the player, bullets/fragments and other objects
    // if a bullet hits a normal asteroid, destroy the asteroid and spawn smaller asteroids and debris
    // if a bullet hits a boss, deplete health until boss health is 0, at which point boss despawns
    // if an asteroid hits the player, destroy the player and remove a life point
    // if the player hits a powerup box, give them a powerup
    // else dont do anything
    public void checkCollisions()
    {
        int DEBRIS_COUNT = 20;

        for (int i = 0; i < asteroidList.size(); i++) // checks collisions between all the asteroids and other objects...
        {
            if (collision(ship, asteroidList.get(i)) && ship.active) // for collision between asteroid and player
            {
                ship.hit();
                SCORE-=500;

                for (int j = 0; j < 20 + random.nextInt(DEBRIS_COUNT); j++)
                {
                    debrisList.add(new Debris(ship.xposition, ship.yposition));
                }
                explosion.stop();
                explosion.play();
            }

            for (int k = 0; k < bulletList.size(); k++) // for collision between asteroid and bullets
            {
                if (collision(bulletList.get(k), asteroidList.get(i)))
                {
                    asteroidList.get(i).active = false;
                    if (powerupFunction.DOES_PIERCE == false)
                    {
                        bulletList.get(k).active = false;
                    }
                    SCORE += asteroidList.get(i).getScore();

                    for (int j = 0; j < 20 + random.nextInt(DEBRIS_COUNT); j++)
                    {
                        debrisList.add(new Debris(asteroidList.get(i).xposition, asteroidList.get(i).yposition));
                    }
                    explosion.stop();
                    explosion.play();
                }
            }
            for (int k = 0; k < specialBulletList.size(); k++) // for collision between asteroid and special bullets/powerups
            {
                if (collision(specialBulletList.get(k), asteroidList.get(i)))
                {
                    asteroidList.get(i).active = false;
                    if (powerupFunction.DOES_PIERCE == false)
                    {
                        specialBulletList.get(k).active = false;
                    }
                    SCORE += asteroidList.get(i).getScore();

                    for (int j = 0; j < 20 + random.nextInt(DEBRIS_COUNT); j++)
                    {
                        debrisList.add(new Debris(asteroidList.get(i).xposition, asteroidList.get(i).yposition));
                    }
                    explosion.stop();
                    explosion.play();
                }
            }
            for (int k = 0; k < fragmentsList.size(); k++) // for collision between fragments and asteroids
            {
                if (collision(fragmentsList.get(k), asteroidList.get(i)))
                {
                    asteroidList.get(i).active = false;

                    fragmentsList.get(k).active = false;

                    SCORE += asteroidList.get(i).getScore();

                    for (int j = 0; j < 20 + random.nextInt(DEBRIS_COUNT); j++)
                    {
                        debrisList.add(new Debris(asteroidList.get(i).xposition, asteroidList.get(i).yposition));
                    }
                    explosion.stop();
                    explosion.play();
                }
            }
        }
        for (int i = 0; i < powerupList.size(); i++) // for collision between player and powerup box
        {
            if (collision(ship, powerupList.get(i)) && ship.active)
            {
                powerupActivated = true;
                powerupList.remove(i);
                powerupType = powerupFunction.PowerupFunction();
                if (powerupType != null)
                {
                    powerupTypeActive = powerupType;
                }
            }
        }
        for (int i = 0; i< bossList.size(); i++) // for collision between boss and player
        {
            if (collision(ship, bossList.get(i)) && ship.active)
            {
                ship.hit();
                SCORE-=500;

                for (int j = 0; j < 20 + random.nextInt(DEBRIS_COUNT); j++)
                {
                    debrisList.add(new Debris(ship.xposition, ship.yposition));
                }
                explosion.stop();
                explosion.play();
            }

            for (int k = 0; k < bulletList.size(); k++) // for collision between boss and bullets
            {
                if (collision(bulletList.get(k), bossList.get(i)))
                {
                    bossList.get(i).HEALTH -= 1;
                    bossHealthPercent = ((double) bossList.get(i).HEALTH/1000);
                    if (powerupFunction.DOES_PIERCE == false)
                    {
                        bulletList.get(k).active = false;
                    }
                }
            }
            for (int k = 0; k < specialBulletList.size(); k++) // for collision between boss and special bullets
            {
                if (collision(specialBulletList.get(k), bossList.get(i)))
                {
                    bossList.get(i).HEALTH -= 30;
                    bossHealthPercent = ((double) bossList.get(i).HEALTH/1000);
                    if (powerupFunction.DOES_PIERCE == false)
                    {
                        specialBulletList.get(k).active = false;
                    }
                }
            }
            for (int k = 0; k < fragmentsList.size(); k++) // for collision between boss and fragment bullets
            {
                if (collision(fragmentsList.get(k), bossList.get(i)))
                {
                    bossList.get(i).HEALTH -= 1;
                    bossHealthPercent = ((double) bossList.get(i).HEALTH/1000);

                    fragmentsList.get(k).active = false;
                }
            }
        }
    }

    // respawns the ship after a certain delay and the condition that the ship is dead
    public void respawnShip()
    {
        if (!ship.active && ship.counter > SHIP_SPAWN_DELAY && isRespawnSafe() && ship.HEALTH != 0)
        {
            ship.reset();
        }
    }

    // checks if it is possible to respawn the shape without being spawn-killed
    // does not work well with the boss asteroid
    public boolean isRespawnSafe()
    {
        int RESPAWN_RADIUS = 50;
        int x, y, h;


        for (int i = 0; i < asteroidList.size(); i++)
        {
            x = (int) (450 - asteroidList.get(i).getClosestPointToCenter().x);
            y = (int) (300 - asteroidList.get(i).getClosestPointToCenter().y);
            h = (int) Math.sqrt (x*x + y*y);
            if (h < RESPAWN_RADIUS)
            {
                return false;
            }
        }
        return true;
    }

    // creates a powerup on the playing field after some time, and if there are no powerups active/already on the board
    public void spawnPowerup()
    {
        if (powerupCounter%POWERUP_RESPAWN_TIME == 0 && powerupCounter != 0)
        {
            powerupList.add(new Powerups());
        }
    }

    // fires the ship's main weapon
    public void fireBullet()
    {
         int SHOT_RATE; // fire rate of the weapon

         if (powerupActivated == false) // normal bullets
         {
             SHOT_RATE = 5;
             random.nextInt(2);

             if (ship.counter > SHOT_RATE)
             {
                 bulletList.add(new Bullet(ship.drawShape.xpoints[0], ship.drawShape.ypoints[0], ship.angle, "NORMAL"));
                 ship.counter = 0;

                 laser.stop();
                 laser.play();
             }
         }

         if (powerupActivated == true) // bullets if the player has an active powerup
         {
             if (ship.counter > powerupFunction.SHOT_RATE)
             {
                 if (powerupType.equals("BAZOOKA") || powerupType.equals("SUPER_BOMB"))
                 {
                     specialBulletList.add(new Bullet(ship.drawShape.xpoints[0], ship.drawShape.ypoints[0], ship.angle, powerupType));
                 }
                 else
                 {
                    bulletList.add(new Bullet(ship.drawShape.xpoints[0], ship.drawShape.ypoints[0], ship.angle, powerupType));
                 }
                 ship.counter = 0;

                 laser.stop();
                 laser.play();
             }
         }
    }

    // some weapons leave fragments or do special things, this method handles these effects
    public void SpecialEffects(String type)
    {
        if (type.equals("BAZOOKA") && SECounter < 1 && bulletLastPosX != 0) {
            SECounter++;
            for (int i = 0; i < 10; i++)
            {
                fragmentsList.add(new Fragment(bulletLastPosX, bulletLastPosY, Math.random() * (2 * Math.PI)));
            }

            if (SECounter == 1){
                bulletLastPosX = 0;
                SECounter = 0;
            }
        }
        if (type.equals("SUPER_BOMB") && SECounter < 100 && bulletLastPosX != 0) {
            SECounter++;
            for (int i = 0; i < 10; i++)
            {
                fragmentsList.add(new Fragment(bulletLastPosX, bulletLastPosY, Math.random() * (2 * Math.PI)));
                System.out.println("SECOUNTER: " + SECounter);
            }

            if (SECounter == 100){
                bulletLastPosX = 0;
                SECounter = 0;
            }
        }
    }

    // creates smaller asteroids when a larger asteroid gets destroyed
     public void checkAsteroidDestruction()
     {
         for(int i = 0; i < asteroidList.size(); i++)
         {
             if (!asteroidList.get(i).active)
             {
                 if (asteroidList.get(i).size > 1) {
                     asteroidList.add(new Asteroid(asteroidList.get(i).xposition, asteroidList.get(i).yposition, asteroidList.get(i).size - 1, asteroidList.get(i).ID));
                     asteroidList.add(new Asteroid(asteroidList.get(i).xposition, asteroidList.get(i).yposition, asteroidList.get(i).size - 1, asteroidList.get(i).ID));
                 }
                 asteroidList.remove(i);
             }
         }
     }

     // deals with what to do when a harder enemy/boss is destroyed
    public void checkEliteEnemyDestruction()
    {
        for(int i = 0; i < bossList.size(); i++)
        {
            if(bossList.get(i).HEALTH < 1)
            {
                bossList.remove(i);
                BOSS_MODE_ACTIVATED = false;
            }
        }
    }

    // --------------- MAIN GAME LOOP -----------------
    @Override
    public void actionPerformed(ActionEvent e)
    {
        worldTimer++;

        if (powerupActivated) {
            powerupCounter = 0;
            powerupDeactCounter++;

            powerUpTimeLeftPercent = ((double) powerupDeactCounter )/ POWER_UP_DEACTIVATION_TIME;
        }
        else
        {
            powerupDeactCounter = 0;
            powerupCounter++;
        }

        if (powerupDeactCounter == POWER_UP_DEACTIVATION_TIME)
        {
            powerupTypeActive = "NORMAL";
            powerupType = "NORMAL";
            powerupActivated = false;
            bulletList.clear();
            powerupFunction.ResetFire();
        }

        //deal with key inputs
        keyCheck();

        respawnShip();
        spawnPowerup();

        //update the powerups
        ship.updatePosition();

        for (int i = 0; i < powerupList.size(); i++)
        {
            powerupList.get(i).updatePosition();

            if (powerupList.get(i).counter == 400)
            {
                powerupList.remove(i);
                powerupCounter = 0;
            }
        }

        // updates the position (movement) of all the asteroids
        for (int i = 0; i < asteroidList.size(); i++)
        {
            asteroidList.get(i).updatePosition();
        }

        // updates the position (movement) of all the elite enemies
        for (int i = 0; i < bossList.size(); i++)
        {
            bossList.get(i).updatePosition();
        }

        // updates the position (movement) of all the debris and removes after a certain amount of time
        for (int i = 0; i < debrisList.size(); i++)
        {
            debrisList.get(i).updatePosition();

            if (debrisList.get(i).counter == 20) // distance traveled = pixelsToTravel/ bulletSpeed (10)
            {
                debrisList.remove(i);
            }
        }

        // updates the position (movement) of all the bullets and removes them after a certain amount of time or if they hit something
        for (int i = 0; i < bulletList.size(); i++)
        {
            bulletList.get(i).updatePosition();

            if (powerupActivated){
                if (bulletList.get(i).counter == powerupFunction.LIFE_SPAN || !bulletList.get(i).active) // distance traveled = pixelsToTravel/ bulletSpeed (10)
                {
                    bulletList.remove(i);
                }
            }
            else{
                if (bulletList.get(i).counter == 40 || !bulletList.get(i).active) // distance traveled = pixelsToTravel/ bulletSpeed (10)
                {
                    bulletList.remove(i);
                }
            }
        }

        // updates the position (movement) of all the bullets and removes them after a certain amount of time or if they hit something
        for (int i = 0; i < specialBulletList.size(); i++)
        {
            specialBulletList.get(i).updatePosition();

            if (specialBulletList.get(i).counter == powerupFunction.LIFE_SPAN || !specialBulletList.get(i).active) // distance traveled = pixelsToTravel/ bulletSpeed (10)
            {
                bulletLastPosX = specialBulletList.get(i).xposition;
                bulletLastPosY = specialBulletList.get(i).yposition;

                specialBulletList.remove(i);
            }
        }

        // removes fragments after some time
        for (int i = 0; i < fragmentsList.size(); i++)
        {
            fragmentsList.get(i).updatePosition();

            if (fragmentsList.get(i).counter == 20 || !fragmentsList.get(i).active) // distance traveled = pixelsToTravel/ bulletSpeed (10)
            {
                fragmentsList.remove(i);
            }
        }

        // spawns asteroids at some interval rate, if the boss is not active yet
        if ((worldTimer%RESPAWN_ASTEROID_TIME == 0) && (worldTimer != 0) && !BOSS_MODE_ACTIVATED)
        {
            spawnAsteroid();

            if (RESPAWN_ASTEROID_TIME > 300) {
                RESPAWN_ASTEROID_TIME -= 50;
            }
            worldTimer = 0;
        }

        // spawns when the score reaches certain values
        // if there is already a boss, don't spawn one
        if ((SCORE > 8000 && SCORE < 10000) && BOSS_MODE_ACTIVATED == false)
        {
            spawnBoss();
            BOSS_MODE_ACTIVATED = true;
        }

        checkCollisions(); // checks if there were any collisions
        checkAsteroidDestruction(); // checks if an asteroid was destroyed
        checkEliteEnemyDestruction(); // checks if the hard enemies were destroyed

        // activates some powerups special effects
        SpecialEffects(powerupType);
    }
}
