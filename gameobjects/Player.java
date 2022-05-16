package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import framework.*;

public class Player extends GameObject {
    private double movementSpeed;

    public Player(double xPos, double yPos, double w, double h, GameObjectID objectID) {
        super(xPos, yPos, w, h, objectID);
        movementSpeed = 2;
    }

    @Override
    public void update(ArrayList<GameObject> gameObject) {
        if (this.id == GameObjectID.PlayerOne) {
            
            if (KeyListener.right) moveX(movementSpeed); 

            if (KeyListener.left) moveX(-movementSpeed); 
            if (KeyListener.up) moveY(-movementSpeed);
            if (KeyListener.down) moveY(movementSpeed);

            //Collision
        for (int i = 0; i < gameObject.size(); i++) {
            GameObject tempObject = gameObject.get(i);
            switch(tempObject.getID()) {
                case PlayerTwo:
                    if(getHBounds().intersects(tempObject.getHBounds())) {
                        System.out.println("X COLLISION");
                    }
                    if(getVBounds().intersects(tempObject.getVBounds())) {
                        System.out.println("Y COLLISION");
                    }
                    break;
                default:
                    break;
                }
            } 
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
