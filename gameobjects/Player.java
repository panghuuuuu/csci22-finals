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
            xSpeed = 0;
            ySpeed = 0;
            
            if (KeyListener.right) {
                setXSpeed(movementSpeed);
                moveX(xSpeed);
            }
            if (KeyListener.left) {
                setXSpeed(-movementSpeed); 
                moveX(xSpeed);
            }
            if (KeyListener.up) {
                setYSpeed(-movementSpeed);
                moveY(ySpeed);
            }
            if (KeyListener.down) {
                setYSpeed(movementSpeed);
                moveY(ySpeed);
            }

        //Collision
        for (int i = 0; i < gameObject.size(); i++) {
            GameObject tempObject = gameObject.get(i);
            switch(tempObject.getID()) {
                case PlayerTwo:
                    if(getHBounds().intersects(tempObject.getHBounds())) { 
                        if(tempObject.getXSpeed() < 0) {
                            if(x < tempObject.getX() + tempObject.getWidth()/2) x = tempObject.getX() - tempObject.getWidth();
                        } else if (tempObject.getXSpeed() > 0) {
                            if(x > tempObject.getX() + tempObject.getWidth()/2) x = tempObject.getX() + tempObject.getWidth();
                        }
                       //if(tempObject.getXSpeed() > 0) {
                       //    x = tempObject.getX() + tempObject.getWidth();
                       //} else if (tempObject.getXSpeed() < 0) {
                       //    x = tempObject.getX() - tempObject.getWidth();
                       //}
                        //if(xSpeed > 0) {
                        //    x = tempObject.getX() - tempObject.getWidth();
                        //} else if (xSpeed < 0) {
                        //    x = tempObject.getX() + tempObject.getWidth();
                        //}
                    }

                    if(getVBounds().intersects(tempObject.getVBounds())) {
                        if(tempObject.getYSpeed() < 0) {
                            if(y < tempObject.getY() + tempObject.getHeight()/2) y = tempObject.getY() - tempObject.getHeight();
                        } else if (tempObject.getYSpeed() > 0) {
                            if(y > tempObject.getY() + tempObject.getHeight()/2) y = tempObject.getY() + tempObject.getHeight();
                        }
                       //if(ySpeed > 0) {
                       //    ySpeed = 0;
                       //    y = tempObject.getY() - tempObject.getHeight();
                       //} else if (ySpeed < 0) {
                       //    ySpeed = 0;
                       //    y = tempObject.getY() + tempObject.getHeight();
                       //}
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

        g2d.setColor(new Color(255,255,255));
        g2d.fill(getHBounds());
        
        g2d.setColor(new Color(0,255,0));
        g2d.fill(getVBounds());

        g2d.setColor(new Color(255,0,0));
        g2d.fillRect((int) x,(int) y, (int) width, (int) height); // Creates a Rectangle
    }
}
