package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;
import java.util.ArrayList;

import framework.*;

public class Landscape extends GameObject{
    public Landscape(double xPos, double yPos, double w, double h, GameObjectID objectID) {
        super(xPos, yPos, w, h, objectID);
    }


    @Override
    public void update(ArrayList<GameObject> gameObject) {
        
    }


    @Override
    public void draw(Graphics2D g2d) {
        if (this.id == GameObjectID.LandscapeOne) {
            g2d.setColor(new Color(255,255,255));
            g2d.fillRect((int) x,(int) y, (int) width, (int) height);
        } else if (this.id == GameObjectID.LandscapeTwo) {
            g2d.setColor(new Color(0,255,0));
            g2d.fillRect((int) x,(int) y, (int) width, (int) height);
        } else if (this.id == GameObjectID.LandscapeThree) {
            g2d.setColor(new Color(0,0,255));
            g2d.fillRect((int) x,(int) y, (int) width, (int) height);
        }
    }

    public Boolean isOut(GameObject player) {
        return (this.x + this.width <= player.getX() ||
                this.x >= player.getX() + player.getWidth() ||
                this.y + this.height <= player.getY() ||
                this.y >= player.getY() + player.getHeight()); 
    }
    
}
