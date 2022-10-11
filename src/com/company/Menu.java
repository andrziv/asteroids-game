package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;

// supposed to create a menu but does not do anything as of now
public class Menu extends JFrame implements MouseListener
{
    public Window panel;

    Image offscreen; // an image to be loaded offscreen
    Graphics offg; // graphics objects for the offscreen image

    public void initMenu()
    {/*
        this.setVisible(true);
        this.setSize(900, 600);
        this.setTitle("Asteroids");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMouseListener(this);
        add(this.panel = new Window(this), BorderLayout.CENTER); // add the JPanel window as a component of our Game window

        offscreen = createImage(this.getWidth(), this.getHeight()); // initialize the image as the same dimension as the JFrame
        offg = offscreen.getGraphics(); */
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.getPoint().getX() > 200 && e.getPoint().getX() < 500 && e.getPoint().getY() > 200 && e.getPoint().getY() < 500)
        {
            Game asteroids = new Game();
            /*
            try {
                asteroids.init();

            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            } */
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
