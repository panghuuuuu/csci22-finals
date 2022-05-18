package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;
import java.util.ArrayList;

import framework.*;

public class Wall extends GameObject {

    public Wall(double xPos, double yPos, double w, double h, GameObjectID objectID) {
        super(xPos, yPos, w, h, objectID);
    }

    @Override
    public void update(ArrayList<GameObject> gameObject) {

    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(1f,0f,0f,0f ));
        g2d.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    public Boolean isOut(GameObject player) {
        return (this.x + this.width <= player.getX() ||
                this.x >= player.getX() + player.getWidth() ||
                this.y + this.height <= player.getY() ||
                this.y >= player.getY() + player.getHeight());
    }

}