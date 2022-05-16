package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import framework.*;

public class Player extends GameObject {


    public Player(double xPos, double yPos, double w, double h, GameObjectID objectID) {
        super(xPos, yPos, w, h, objectID);
        setXSpeed(2);
        setYSpeed(2);
    }

    @Override
    public void update(ArrayList<GameObject> gameObject) {
        if (this.id == GameObjectID.PlayerOne) {
            
            if (KeyListener.right) moveX(xSpeed); 
            if (KeyListener.left) moveX(-xSpeed); 
            if (KeyListener.up) moveY(-ySpeed);;
            if (KeyListener.down) moveY(ySpeed);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(255,0,0));
        g2d.fillRect((int) x,(int) y, (int) width, (int) height); // Creates a Rectangle    
    }
    
    private void moveX(double speed) {
        this.x += speed;
    }
    private void moveY(double speed) {
        this.y += speed;
    }

}
