package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import framework.*;

public class Player extends GameObject {

    public Player(double xPos, double yPos, double w, double h, GameObjectID objectID) {
        super(xPos, yPos, w, h, objectID);
    }

    @Override
    public void update(ArrayList<GameObject> gameObject) {
        x += xSpeed;
        y += ySpeed;

        if (KeyListener.down) ySpeed = 1;
        if (KeyListener.left) xSpeed = -1;
        if (KeyListener.right) xSpeed = 1;
        if (KeyListener.up) ySpeed = -1;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(255,0,0));
        g2d.fillRect((int) x,(int) y, (int) width, (int) height); // Creates a Rectangle    
    }
    
}
